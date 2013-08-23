package goldeneagle;

public class Main {

	public static void main(String[] args) {
		PxlGame game = new PxlGame();
		
		boolean fullscreen = false;
		
		for(int i = 0; i < args.length; i++)
			if(args[i] == "--fullscreen")
				fullscreen = true;
			
		game.start(fullscreen);
	}
}
