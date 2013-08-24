package goldeneagle.state;

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
		this.cam = new Camera();
		cam.bindFrame(scene.getRoot());
		cam.setRadius(10);
		scene.AddEntity(new ChunkEntity(scene.getRoot(), 0, 0));
		scene.AddEntity(new PlayerEntity(scene.getRoot()));
	}

	@Override
	protected void update() {

	}

	@Override
	protected void draw() {
		// hello, world!
		SceneManager.doFrame(scene, cam);
	}
}
