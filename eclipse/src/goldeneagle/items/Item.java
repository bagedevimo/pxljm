package goldeneagle.items;

import goldeneagle.entities.PlayerEntity;
import goldeneagle.scene.Scene;

public abstract class Item {
	
	protected String texturePath; 
	
	public abstract void use(PlayerEntity player, Scene scene);
	
	public String getTexturePath(){
		return texturePath;
	}
	
	public void drawOnScreen(){
		
	}
}
