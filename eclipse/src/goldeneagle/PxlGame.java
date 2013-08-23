package goldeneagle;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class PxlGame {
	public void start(boolean isFullscreen) {
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create();
		} catch(LWJGLException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
		
		//
		
		while(!Display.isCloseRequested()) {
			Display.update();
		}
		
		Display.destroy();
	}
}
