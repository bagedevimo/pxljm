package goldeneagle.state;

import goldeneagle.MovingFrame;
import goldeneagle.Vec3;
import goldeneagle.scene.Camera;
import goldeneagle.scene.ChunkEntity;
import goldeneagle.scene.PlayerEntity;
import goldeneagle.scene.Scene;
import goldeneagle.scene.SceneManager;

public class MainGameState extends GameState {
	private Scene scene;
	private Camera cam;
	
	@Override
	protected void init() {
		this.scene = new Scene(getClock());
		this.cam = new Camera(scene.getRoot());
		PlayerEntity player = new PlayerEntity(scene.getRoot());
		MovingFrame offset = new MovingFrame(player);
		offset.setLinear(new Vec3(0, 4, 0), Vec3.zero);
		cam.bindFrame(offset);
		cam.setRadius(10);
		scene.AddEntity(new ChunkEntity(scene.getRoot(), 0, 0));
		scene.AddEntity(player);
	}

	@Override
	protected void update() {
		scene.Update();
	}

	@Override
	protected void draw() {
		// hello, world!
		SceneManager.doFrame(scene, cam);
	}
}
