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
		double xMin = Math.min(b.minX, minX);
		double xMax = Math.max(b.maxX, maxX);
		double yMin = Math.min(b.minY, minY);
		double yMax = Math.max(b.maxY, maxY);
		
		if(xMax-xMin < ((b.maxX-b.minX)+(maxX-minX)) && 
				yMax-yMin < ((b.maxY-b.minY)+(maxY-minY)))
			return new Vec3(0,0,0);
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

	public Vec3 center() {
		return new Vec3((this.maxX + this.minX) * 0.5,(this.maxY + this.minY) * 0.5, 0); 
	}

	public boolean contains(Bound a) {
		System.out.println("SIP!");
		System.exit(1);
		return false;
	}
	
	public Vec3 min() {
		return new Vec3(this.minX, this.minY);
	}
	
	public Vec3 max() {
		return new Vec3(this.maxX, this.maxY);
	}

	public static BoundingBox fromExtremes(Vec3 center, Vec3 add) {
		Vec3 max = Vec3.positiveExtremes(center, add);
		Vec3 min = Vec3.negativeExtremes(center, add);
		return new BoundingBox(min.x, min.y, max.x, max.y);
	}
}
