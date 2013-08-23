package goldeneagle;

import goldeneagle.clock.*;

public abstract class GameState {
	protected boolean isDone = false;
	protected GameState nextState = null;
	
	private final ManualClock clock = new ManualClock(PxlGame.sysclock, 0);
	
	public Clock getClock() {
		return clock;
	}
	
	public GameState() {
		this.doInit();
	}
	
	public GameState getNextState() {
		GameState retState = this.nextState;
		if(retState != null)
			this.nextState = null;
		
		return retState;
	}
	
	public final boolean isDone() {
		return this.isDone;
	}
	
	private final void doInit() {
		clock.set(0);
		this.init();
	}
	
	public final void doUpdate() {
		clock.update();
		this.update();
	}
	
	public final void doDraw() {
		this.draw();
	}
	
	public void doPause() {
		onPause();
	}
	
	public void doResume() {
		clock.reset();
		onResume();
	}
	
	protected abstract void init();
	protected abstract void update();
	protected abstract void draw();
	
	protected void onPause() {
		// state paused because another is pushed onto the stack
	}
	
	protected void onResume() {
		// state resumed because it has become the stack top again
	}
}
