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
		int width = 800;
		int height = 600;
		
		for(int i = 0; i < args.length; i++) {
			System.out.printf("args[%d]=%s\n", i, args[i]);
			if(args[i].equals("--fullscreen")) {
				fullscreen = true;
				System.out.println("fullscreen!");
				width = Integer.parseInt(args[++i]);
				height = Integer.parseInt(args[++i]);
			}
		}
			
		game.start(fullscreen, width, height);
	}
}
