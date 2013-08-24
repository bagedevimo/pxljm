package goldeneagle;

public class BoundingSphere extends Bound {
	private final Frame parent;
	private final double radius;

	public BoundingSphere(Frame _parent, double _radius) {
		parent = _parent;
		radius = _radius;
	}

	@Override
	public Vec3 intersects(BoundingBox _b){
		return _b.intersects(this).neg();
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
}
