package goldeneagle.state;

import goldeneagle.ResourceCache;
import goldeneagle.ResourceLoader;

// This GameState should load any assets required to display the intros
// and the menus which occur before we enter the game.

public class InitGameState extends GameState {
	ResourceLoader rl;
	
	@Override
	protected void init() {
		rl = new ResourceLoader();
		rl.Add("./assets/tiles/atlas.jpg");
		rl.Add("./assets/entities/tree.png");
		rl.Add("./assets/entities/bush.png");
		rl.Add("./assets/sprites/character_walk.png");
		rl.Start();
		
		try {
			ResourceCache.AddShaderProgram("test");
			System.out.printf("ShaderProgram: %d\n", ResourceCache.GetProgram("test")); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
