package goldeneagle.items;

import goldeneagle.entities.PlayerEntity;
import goldeneagle.scene.Scene;

public class Wood extends Item{

	public Wood() {
		texturePath = "./assets/entities/sticks.png";
	}
	
	@Override
	public void use(PlayerEntity player, Scene scene) {
		player.addItem(new Fire());
	}

}
