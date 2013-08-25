package goldeneagle.items;

import goldeneagle.Vec3;
import goldeneagle.entities.FireEntity;
import goldeneagle.entities.PlayerEntity;
import goldeneagle.scene.Scene;

public class Fire extends Item{

	@Override
	public void use(PlayerEntity player, Scene scene) {
		scene.AddEntity(new FireEntity(scene.getRoot(), scene));
	}

}
