package goldeneagle.items;

import goldeneagle.entities.PlayerEntity;

public abstract class Item {
	
	private String texturePath; 
	
	public Item() {}
	
	public abstract void use(PlayerEntity player);
}
