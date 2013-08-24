package goldeneagle.entities;

import goldeneagle.scene.Frame;
import goldeneagle.scene.Entity;

public class PickupEntity extends Entity {

//	private final Item item;
//	private Bound bound;
//	
	public PickupEntity(Frame parent_) {
		super(parent_);
	}
	
	protected boolean update(double deltaTime) {
		return false;
	}

	@Override
	protected void draw() {
	}

}
