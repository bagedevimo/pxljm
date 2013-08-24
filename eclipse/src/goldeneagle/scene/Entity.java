package goldeneagle.scene;

import goldeneagle.*;

public abstract class Entity extends MovingFrame {

	public Entity(Frame parent_) {
		super(parent_);
	}
	
	public abstract void Update(double deltaTime);
	public abstract void Draw();
}
