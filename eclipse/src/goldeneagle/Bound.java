package goldeneagle;

public abstract class Bound {
	
	public final Vec3 getIntersectionNorm(Bound b){
		if(b==null){
			return null;
		}
		if(b instanceof BoundingBox){
			return intersects((BoundingBox)b);
		}else if(b instanceof BoundingSphere){
			return intersects((BoundingSphere)b);
		}
		throw new UnsupportedOperationException("Cannot collide these 2 implementations of bound");
	}
	
	public final boolean intersects(Bound b){
		if(b==null){
			return false;
		}
		if(b instanceof BoundingBox){
			return intersects((BoundingBox)b) != null;
		}else if(b instanceof BoundingSphere){
			return intersects((BoundingSphere)b) != null;
		}
		throw new UnsupportedOperationException("Cannot collide these 2 implementations of bound");
	}
	
	public abstract boolean contains(double x, double y);
	
	public abstract Vec3 intersects(BoundingBox b);
	public abstract Vec3 intersects(BoundingSphere b);
	
	public abstract Vec3 getPosition();
}
