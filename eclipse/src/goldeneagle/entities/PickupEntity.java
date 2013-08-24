package goldeneagle.entities;

import goldeneagle.Bound;
import goldeneagle.items.Item;
import goldeneagle.scene.Frame;
import goldeneagle.scene.Entity;
import goldeneagle.scene.Scene;

public class PickupEntity extends Entity {

	private static final int size = 32;
	private final Item item;
	private Bound bound; 
	
	public PickupEntity(Frame parent_, double xPos_, double yPos_, Item item_) {
		super(parent_);
		item = item_;
	}
	
	protected boolean update(double deltaTime, Scene scene) {
		return false;
	}

	@Override
	protected void draw() {

	}

}
