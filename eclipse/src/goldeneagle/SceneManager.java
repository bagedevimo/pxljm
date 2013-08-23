package goldeneagle;

public class SceneManager {

	private static boolean is_inited = false;
	
	public static void init() {
		if (is_inited) return;
		is_inited = true;
		
	}
	
	public static void doFrame(Scene s, Camera c) {
		init();
		
	}
	
}
