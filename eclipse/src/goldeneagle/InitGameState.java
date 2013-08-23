package goldeneagle;

import goldeneagle.scene.SceneManager;

// This GameState should load any assets required to display the intros
// and the menus which occur before we enter the game.

public class InitGameState extends GameState {

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
