package goldeneagle.scene;

import goldeneagle.*;

public abstract class Entity extends MovingFrame {

	private BoundingSphere bound = null;
	
	public Entity(Frame parent_) {
		super(parent_);
	}
	
	public BoundingSphere getBound() {
		return bound;
	}
	
	protected void setBound(BoundingSphere bs) {
		bound = bs;
	}
	
	public abstract boolean Update(double deltaTime);
	public abstract void Draw();
}
