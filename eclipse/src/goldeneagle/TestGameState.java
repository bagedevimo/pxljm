package goldeneagle;

public class TestGameState extends GameState {
	private Scene scene = new Scene();
	
	@Override
	protected void Init() {
		
	}

	@Override
	protected void Update(double deltaTime) {
	}

	@Override
	protected void Draw(double deltaTime) {
		// Something like this?
		// SceneManager.DrawScene(scene);
	}
}
