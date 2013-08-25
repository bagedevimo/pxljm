package goldeneagle.items;

import goldeneagle.entities.FireEntity;
import goldeneagle.entities.PlayerEntity;
import goldeneagle.scene.Scene;

public class Wood extends Item{

	public Wood() {
		texturePath = "./assets/entities/sticks.png";
	}
	
	@Override
	public void use(PlayerEntity player, Scene scene) {
		scene.AddEntity(new FireEntity(scene.getRoot(), scene));
	}

}
