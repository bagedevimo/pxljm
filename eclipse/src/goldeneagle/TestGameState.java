package goldeneagle;

import goldeneagle.scene.Scene;

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
		// Something like this?
		// SceneManager.DrawScene(scene);
	}
}
