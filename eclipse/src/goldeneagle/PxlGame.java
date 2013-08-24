package goldeneagle;

import goldeneagle.clock.SystemClock;
import goldeneagle.scene.SceneManager;
import goldeneagle.state.GameState;
import goldeneagle.state.InitGameState;

import java.util.Stack;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


public class PxlGame {

	public static final SystemClock sysclock = new SystemClock();

	protected Stack<GameState> gameStates = new Stack<GameState>();

	public void start(boolean isFullscreen) {
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create(SceneManager.PIXEL_FORMAT, SceneManager.CONTEXT_ATTRIBS);
		} catch (LWJGLException ex) {
			ex.printStackTrace();
			System.exit(1);
		}

		gameStates.push(new InitGameState());

		while (!Display.isCloseRequested()) {
			GameState currentState = getCurrentState();
			currentState.doUpdate();
			currentState.doDraw();
			Display.update();
			try {
				// for sanity - i get gpu whine at insane frame rates
				Thread.sleep(1);
			} catch (InterruptedException e) {
				
			}
		}

		Display.destroy();
	}

	private GameState getCurrentState() {
		if (gameStates.isEmpty()) {
			System.out.println("no gamestate on stack");
			System.exit(1);
		}

		GameState nextState = null;
		do {
			nextState = gameStates.peek().getNextState();
			if (nextState != null) {
				gameStates.peek().doPause();
				gameStates.push(nextState);
			}

		} while (nextState != null);

		while (!gameStates.isEmpty() && gameStates.peek().isDone()) {
			gameStates.pop();
			if (!gameStates.isEmpty()) {
				gameStates.peek().doResume();
			}
		}

		if (gameStates.isEmpty()) {
			System.out.println("last state done, exiting");
			System.exit(0);
		}

		return gameStates.peek();
	}
}
