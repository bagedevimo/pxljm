package goldeneagle.state;

import goldeneagle.ResourceLoader;

public class InitGameState extends GameState {
	ResourceLoader rl;
	
	@Override
	protected void init() {
		rl = new ResourceLoader();
		rl.Add("assets/menu/bg.jpg");
		rl.Add("assets/menu/start.png");
		rl.Add("assets/menu/start_hover.png");
		rl.Add("assets/menu/exit.png");
		rl.Add("assets/menu/exit_hover.png");
		rl.Add("assets/menu/once.png");
	}

	@Override
	protected void update(double deltaTime) {
		if(!rl.isStarted())
			rl.Start();
		
		if(rl.isComplete())
			this.nextState = new MenuState();
	}

	@Override
	protected void draw() {
		// TODO Auto-generated method stub
	}

}
