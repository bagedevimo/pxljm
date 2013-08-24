package goldeneagle.state;

import goldeneagle.scene.ChunkEntity;
import goldeneagle.scene.Scene;
import goldeneagle.scene.SceneManager;

public class MainGameState extends GameState {
	private Scene scene;
	
	@Override
	protected void init() {
		this.scene = new Scene(getClock());
		scene.AddEntity(new ChunkEntity(scene.getRoot(), 0, 0));
	}

	@Override
	protected void update() {

	}

	@Override
	protected void draw() {
		// hello, world!
		SceneManager.doFrame(scene, null);
	}
}
