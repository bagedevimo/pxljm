package goldeneagle;

import java.util.Stack;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class PxlGame {
	protected Stack<GameState> gameStates = new Stack<GameState>();
	
	public void start(boolean isFullscreen) {
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create();
		} catch(LWJGLException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	
		gameStates.push(new InitGameState());
		
		
		while(!Display.isCloseRequested()) {
			GameState currentState = this.getCurrentState();
			
			currentState.doUpdate();
			currentState.doDraw();
			Display.update();
		}
		
		Display.destroy();
	}
	
	private GameState getCurrentState() {
		if(gameStates.isEmpty()) {
			System.out.println("no gamestate on stack");
			System.exit(1);
		}
				
		GameState currentState = gameStates.peek();
		GameState nextState = null;
		do {
			nextState = currentState.getNextState();
			if(nextState != null)
				gameStates.push(nextState);
			
		} while(nextState != null);
		currentState = gameStates.peek();
		
		if(currentState.checkIsDone()) {
			do {
				gameStates.pop();
				
				if(!gameStates.isEmpty())
					currentState = gameStates.peek();
			} while(!gameStates.isEmpty() && currentState.checkIsDone());
		}
		
		if(currentState == null) {
			System.out.println("not state resolved");
			System.exit(1);
		}
		
		return currentState;
	}
}
