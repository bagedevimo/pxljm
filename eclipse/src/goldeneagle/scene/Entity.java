package goldeneagle.scene;

import goldeneagle.*;

public abstract class Entity extends MovingFrame {

	public Entity(Frame parent_) {
		super(parent_);
	}
	
	public abstract void Update();
	public abstract void Draw();
}
