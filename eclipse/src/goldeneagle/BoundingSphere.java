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
//		double[] result = new double[3];
//		
//		double[] sph_cen = { this.position.x, this.position.y, this.position.z};
//		double[] box_min = { _b.getNegExtreme().x, _b.getNegExtreme().y, _b.getNegExtreme().z};
//		double[] box_max = { _b.getPosExtreme().x, _b.getPosExtreme().y, _b.getPosExtreme().z};
//		
//		//for each plane record the 1-d vector from the 
//		//edges to the sphere on that plane.
//		for(int i=0; i<3; i++){
//			if(sph_cen[i] < box_min[i]){
//				result[i] =  box_min[i] - sph_cen[i]; //TODO not sure if right way round
//			}else if(sph_cen[i] > box_max[i]){
//				result[i] = box_max[i] - sph_cen[i];
//			}
//		}
//		//compile the vector together and check the distance
//		Vec3 collisionNorm = new Vec3(result[0], result[1], result[2]);
//		if(this.radius > collisionNorm.mag()){
//			return collisionNorm.unit();
//		}
		return null;
	}

	@Override
	public Vec3 intersects(BoundingSphere b) {
		Vec3 collisionNorm = b.getPosition().sub(parent.getPosition());
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
		Vec3 position = parent.getPosition();
		double distance = Math.hypot((x-position.x), (y-position.y));
		return distance < radius;
	}

	@Override
	public Vec3 getPosition() {
		return parent.getPosition();
	}
}
