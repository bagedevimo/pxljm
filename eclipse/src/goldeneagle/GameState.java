package goldeneagle;

public abstract class GameState {
	private double initTime = -1;
	protected boolean isDone = false;
	protected GameState nextState = null;
	
	public GameState() {
		this.doInit();
	}
	
	public GameState getNextState() {
		GameState retState = this.nextState;
		if(retState != null)
			this.nextState = null;
		
		return retState;
	}
	
	public boolean checkIsDone() {
		return this.isDone;
	}
	
	private void doInit() {
		this.initTime = (System.nanoTime() / 1e9d);
		
		this.Init();
		
	}
	
	public void doUpdate() {
		double deltaTime = (System.nanoTime() / 1e9d) - initTime;
		this.Update(deltaTime);
	}
	
	public void doDraw() {
		double deltaTime = (System.nanoTime() / 1e9d) - initTime;
		this.Draw(deltaTime);
	}
	
	protected abstract void Init();
	protected abstract void Update(double deltaTime);
	protected abstract void Draw(double detlaTime);
}
