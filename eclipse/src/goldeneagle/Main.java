package goldeneagle;

import java.io.File;

import org.lwjgl.LWJGLUtil;

public class Main {

	static {
		File libpath = new File("./lib/native/" + LWJGLUtil.getPlatformName());
		System.setProperty("org.lwjgl.librarypath", libpath.getAbsolutePath());
	}
	
	public static void main(String[] args) {
		
		PxlGame game = new PxlGame();
		
		boolean fullscreen = false;
		
		for(int i = 0; i < args.length; i++)
			if(args[i] == "--fullscreen")
				fullscreen = true;
			
		game.start(fullscreen);
	}
}
