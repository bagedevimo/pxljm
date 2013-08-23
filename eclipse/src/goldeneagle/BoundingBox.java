package goldeneagle;

public class BoundingBox extends Bound{

	public final double minX;
	public final double minY;
	public final double maxX;
	public final double maxY;
	
	public BoundingBox(double minX, double minY, double maxX, double maxY){
		super();
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}

	@Override
	public Vec3 intersects(BoundingBox b) {
		if (b == null) return null;
		if (this.contains(b.minX, b.minY) || 
			this.contains(b.minX, b.maxY) || 
			this.contains(b.maxX, b.minY) || 
			this.contains(b.maxX, b.maxY)) {
			return new Vec3(0,0,0);
		}
		return null;
	}
	
	@Override
	public Vec3 intersects(BoundingSphere b) {
//		double[] result = new double[3];
//		
//		double[] sph_cen =  {b.getPosition().x, b.getPosition().y, b.getPosition().z};
//		double[] box_min =  {neg_ext.x, neg_ext.y, neg_ext.z};
//		double[] box_max =  {pos_ext.x, pos_ext.y, pos_ext.z};
//		
//		//for each plane record the 1-d vector from the 
//		//edges to the sphere on that plane.
//		for(int i=0; i<3; i++){
//			if(sph_cen[i] < box_min[i]){
//				result[i] =  sph_cen[i] - box_min[i];
//			}else if(sph_cen[i] > box_max[i]){
//				result[i] = sph_cen[i] - box_max[i];
//			}
//		}
//		//compile the vector together and check the distance
//		Vec3 collisionNorm = new Vec3(result[0], result[1], result[2]);
//		if(b.getRadius() > collisionNorm.mag()){
//			return collisionNorm.unit();
//		}
		return null;
	}
	
	@Override
	public boolean contains(double x, double y){
		if (this.minX <= x &&
			this.minY <= y &&
			this.maxX > x &&
			this.maxY > y){
			return true;
		}
		return false;
	}
	
	@Override
	public Vec3 getPosition() {
		return new Vec3(minX, minY, 0);
	}
	
	@Override
	public String toString(){
		return String.format("%.3f,%.3f,%.3f,%.3f",minX, minY, maxX, maxY);
	}
}
