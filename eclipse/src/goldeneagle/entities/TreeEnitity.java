package goldeneagle.entities;

import goldeneagle.Bound;
import goldeneagle.BoundingSphere;
import goldeneagle.Frame;
import goldeneagle.scene.Entity;

public class TreeEnitity extends Entity{

	private final double xPos;
	private final double yPos;
	private final Bound bound;
	
	public TreeEnitity(Frame parent_, double xPos_, double yPos_, double radius) {
		super(parent_);
		xPos = xPos_;
		yPos = yPos_;
		bound = new BoundingSphere(parent_, radius);
	}

	@Override
	public void Draw() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Update(double deltaTime) {
		// TODO Auto-generated method stub
		
	}
}
