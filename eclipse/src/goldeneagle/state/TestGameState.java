package goldeneagle.state;

import goldeneagle.Frame;
import goldeneagle.scene.Scene;
import goldeneagle.scene.SceneManager;
import goldeneagle.scene.TestEntity;

public class TestGameState extends GameState {
	private Scene scene;
	
	@Override
	protected void init() {
		this.scene = new Scene(getClock());
		Frame root = scene.getRoot();
		scene.AddEntity(new TestEntity(root));
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
