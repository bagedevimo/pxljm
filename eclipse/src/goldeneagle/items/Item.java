package goldeneagle.items;

import goldeneagle.entities.PlayerEntity;

public abstract class Item {
	
	protected String texturePath; 
	
	public abstract void use(PlayerEntity player);
	
	public String getTexturePath(){
		return texturePath;
	}
}
