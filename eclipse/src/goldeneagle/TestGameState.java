package goldeneagle;

import goldeneagle.scene.Scene;
import goldeneagle.scene.SceneManager;

public class TestGameState extends GameState {
	private Scene scene = new Scene(getClock());
	
	@Override
	protected void init() {
		
	}

	@Override
	protected void update() {

	}

	@Override
	protected void draw() {
		// hello, world!
		SceneManager.doFrame(null, null);
	}
}
