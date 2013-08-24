package goldeneagle.state;

import goldeneagle.ResourceLoader;

// This GameState should load any assets required to display the intros
// and the menus which occur before we enter the game.

public class InitGameState extends GameState {
	ResourceLoader rl;
	
	@Override
	protected void init() {
		rl = new ResourceLoader();
		rl.Add("./assets/tiles/atlas.jpg");
		rl.Start();
	}

	@Override
	protected void update(double deltaTime) {
		if(rl.isComplete())
			this.nextState = new MainGameState();
	}

	@Override
	protected void draw() {
		
	}

}
