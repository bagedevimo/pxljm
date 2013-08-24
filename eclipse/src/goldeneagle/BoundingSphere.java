package goldeneagle;
import map.islandbase.Point;
import goldeneagle.scene.Frame;

public class BoundingSphere extends Bound {
	private final Frame parent;
	private final double radius;

	public BoundingSphere(Frame _parent, double _radius) {
		parent = _parent;
		radius = _radius;
	}

	@Override
	public Vec3 intersects(BoundingBox _b){
		Vec3 v = _b.intersects(this);
		if (v != null) return v.neg();
		return null;
	}

	@Override
	public Vec3 intersects(BoundingSphere b) {
		Vec3 collisionNorm = b.getPosition().sub(parent.getGlobalPosition());
		if(radius + b.radius > collisionNorm.mag()){
			return collisionNorm.unit();
		}
		return null;
	}

	public double getRadius() {
		return radius;
	}
	
	@Override
	public boolean contains(double x, double y) {
		Vec3 position = parent.getGlobalPosition();
		double distance = Math.hypot((x-position.x), (y-position.y));
		return distance < radius;
	}

	@Override
	public Vec3 getPosition() {
		return parent.getGlobalPosition();
	}
	
	public BoundingBox getInnerBoundingBox(){
		Vec3 p = getPosition();
		double length = Math.sqrt(Math.pow(radius, 2) / 4);
		return new BoundingBox(p.x-length, p.y-length, p.x+length, p.y+length);
	}
	
	public BoundingBox getOuterBoundingBox(){
		Vec3 p = getPosition();
		return new BoundingBox(p.x-radius, p.y-radius, p.x+radius, p.y+radius);
	}

	@Override
	public Vec3 center() {
		return getPosition();
	}

	@Override
	public Vec3 min() {
		return center().sub(radius, radius, 0);
	}

	@Override
	public Vec3 max() {
		return center().add(radius, radius, 0);
	}

	@Override
	public boolean contains(Bound b) throws InvalidBoundException {
		BoundingSphere a = this;
		if(b instanceof BoundingBox) {
			BoundingBox bb = (BoundingBox)b;
		    Point p_rect_1 = new Point(bb.minX, bb.minY);
		    Point p_rect_2 = new Point(bb.maxX, bb.minY);
		    Point p_rect_3 = new Point(bb.maxX, bb.maxY);
		    Point p_rect_4 = new Point(bb.minX, bb.maxY);
		    Point[] points = new Point[] { p_rect_1, p_rect_2, p_rect_3, p_rect_4 };

		    for(Point p : points) {
		        if ((Math.pow((a.center().x - p.x), 2) + Math.pow((a.center().y - p.y), 2)) > Math.pow(a.getRadius(), 2)) {
		            return false;
		        }
		    }
		    return true;
		} else if(b instanceof BoundingSphere) {
			BoundingSphere bb = (BoundingSphere)b;
			double distance = a.center().dist(bb.center());
			return !(distance > (a.getRadius() + bb.getRadius())) && distance <= Math.abs(a.getRadius() - bb.getRadius());
		} else
			throw new InvalidBoundException();
	}
}
