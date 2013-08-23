package goldeneagle;

public class BoundingBox extends Bound{

	private final Vec3 position;
	private final Vec3 pos_ext;// positive extreme
	private final Vec3 neg_ext; //lowest co-ordinates of bounding box, negative extreme
	private final Vec3 size; //always positive values
	private final double volume;
	
	public BoundingBox(Vec3 _position, Vec3 _radius){
		super();
		position = _position;
		neg_ext = Vec3.negativeExtremes(_position.sub(_radius), _position.add(_radius));
		pos_ext = Vec3.positiveExtremes(_position.sub(_radius), _position.add(_radius));
		size = pos_ext.sub(this.neg_ext);
		volume = size.x * size.y * size.z;
	}

	//TODO
	@Override
	public Vec3 intersects(BoundingBox b){
		if (b == null) return null;
		b = getIntersection(b);
		if (b!=null && b.volume>0){
			return null;
		}
		return null;
	}
	
	@Override
	public Vec3 intersects(BoundingSphere b) {
		double[] result = new double[3];
		
		double[] sph_cen =  {b.getPosition().x, b.getPosition().y, b.getPosition().z};
		double[] box_min =  {neg_ext.x, neg_ext.y, neg_ext.z};
		double[] box_max =  {pos_ext.x, pos_ext.y, pos_ext.z};
		
		//for each plane record the 1-d vector from the 
		//edges to the sphere on that plane.
		for(int i=0; i<3; i++){
			if(sph_cen[i] < box_min[i]){
				result[i] =  sph_cen[i] - box_min[i];
			}else if(sph_cen[i] > box_max[i]){
				result[i] = sph_cen[i] - box_max[i];
			}
		}
		//compile the vector together and check the distance
		Vec3 collisionNorm = new Vec3(result[0], result[1], result[2]);
		if(b.getRadius() > collisionNorm.mag()){
			return collisionNorm.unit();
		}
		return null;
	}
	
	@Override
	public boolean contains(Vec3 v){
		if(v == null) return false;
		if (this.neg_ext.x <= v.x &&
			this.neg_ext.y <= v.y &&
			this.neg_ext.z <= v.z &&
			this.neg_ext.add(this.size).x > v.add(this.size).x &&
			this.neg_ext.add(this.size).y > v.add(this.size).y &&
			this.neg_ext.add(this.size).z > v.add(this.size).z){
			return true;
		}
		return false;
	}
	
	@Override
	public Vec3 getPosition() {
		return position;
	}


	@Override
	public Bound setPosition(Vec3 _pos) {
		return new BoundingBox(_pos, size.mul(0.5));
	}

	public boolean contains(BoundingBox b){
		if(b == null) return false;
		if (this.neg_ext.x <= b.neg_ext.x &&
			this.neg_ext.y <= b.neg_ext.y &&
			this.neg_ext.z <= b.neg_ext.z &&
			this.neg_ext.add(this.size).x >= b.neg_ext.add(b.size).x &&
			this.neg_ext.add(this.size).y >= b.neg_ext.add(b.size).y &&
			this.neg_ext.add(this.size).z >= b.neg_ext.add(b.size).z){
			return true;
		}
		return false;
	}
	
	public BoundingBox getIntersection(BoundingBox b){

		Vec3 int_neg_ext = Vec3.positiveExtremes(this.neg_ext, b.neg_ext);
		Vec3 int_pos_ext = Vec3.negativeExtremes(this.pos_ext, b.pos_ext);
		
		if (int_pos_ext.x>=int_neg_ext.x &&
			int_pos_ext.y>=int_neg_ext.y &&
			int_pos_ext.z>=int_neg_ext.z){
			return new BoundingBox(int_neg_ext, int_pos_ext.sub(int_neg_ext));
		}
		return null;

	}
	
	public Vec3 getNegExtreme(){
		return neg_ext;
	}
	
	public Vec3 getPosExtreme(){
		return pos_ext;
	}
	
	
	@Override
	public String toString(){
		return String.format("%.3f,%.3f,%.3f",neg_ext.x, neg_ext.y, neg_ext.z);
	}
}
