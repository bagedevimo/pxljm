package goldeneagle;

import goldeneagle.scene.SceneManager;

// This GameState should load any assets required to display the intros
// and the menus which occur before we enter the game.

public class InitGameState extends GameState {
	ResourceLoader rl;
	
	@Override
	protected void init() {
		rl = new ResourceLoader();
		rl.Add("./assets/images/backdrop.jpg");
		rl.Start();
	}

	@Override
	protected void update() {
		if(rl.isComplete())
			this.nextState = new TestGameState();
		System.out.printf("not here\n");
	}

	@Override
	protected void draw() {
		
	}

}
