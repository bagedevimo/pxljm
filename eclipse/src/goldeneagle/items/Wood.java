package goldeneagle.items;

import goldeneagle.MovingFrame;
import goldeneagle.Vec3;
import goldeneagle.entities.FireEntity;
import goldeneagle.entities.PlayerEntity;
import goldeneagle.scene.Scene;

public class Wood extends Item{

	public Wood() {
		texturePath = "./assets/entities/sticks.png";
	}
	
	@Override
	public void use(PlayerEntity player, Scene scene) {
		MovingFrame uiOffset = new MovingFrame(scene.getRoot());
		uiOffset.setLinear(new Vec3(player.getPosition().x, player.getPosition().y, 0), Vec3.zero);
		FireEntity f = new FireEntity(uiOffset, scene);
		scene.AddEntity(f);
	}

}
