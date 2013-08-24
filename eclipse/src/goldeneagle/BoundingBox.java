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
		double resultX = 0, resultY = 0;
		
		//for each plane record the 1-d vector from the 
		//edges to the sphere on that plane.

		if(b.getPosition().x < minX)
			resultX =  b.getPosition().x - minX;
		else if(b.getPosition().x > maxX)
			resultX = b.getPosition().x - maxX;
		
		if(b.getPosition().y < minY)
			resultY =  b.getPosition().y - minY;
		else if(b.getPosition().y > maxY)
			resultY = b.getPosition().y - maxY;
		
		//compile the vector together and check the distance
		Vec3 collisionNorm = new Vec3(resultX, resultY);
		if(b.getRadius() > collisionNorm.mag()){
			return collisionNorm.unit();
		}
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
