package goldeneagle;

import goldeneagle.scene.SceneManager;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.newdawn.slick.openal.*;
import org.newdawn.slick.util.ResourceLoader;

public class AudioEngine {
	private static boolean isInitalised = false;
		
	private static Queue<Audio> backgroundTracks;
	private static Map<String, Audio> fxTracks;
	
	public static void Initalise() throws Exception {
		if(isInitalised)
			return;
		isInitalised = true;
		
		backgroundTracks = new LinkedList<Audio>();
		fxTracks = new HashMap<String, Audio>();
		
//		AddBackgroundMusic(AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("test.wav")));
		AddEffect("step", AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("assets/audio/fx/step.wav")));
	}
	
	public static void AddBackgroundMusic(Audio a) {		
		backgroundTracks.add(a);
	}
	
	public static void AddEffect(String name, Audio a) {
		fxTracks.put(name, a);
	}
	
	public static void PlayEffect(String name, Vec3 point) {
		if(fxTracks.containsKey(name))
			fxTracks.get(name).playAsSoundEffect(1f, 1f, false, (float)point.x, (float)point.y, 0f);
	}
	
	public static void Update() {
		if(!isInitalised)
			try {
				Initalise();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		if(backgroundTracks.size() > 0 && !backgroundTracks.peek().isPlaying()) {
			Audio next = backgroundTracks.remove();
			next.playAsMusic(0.2f, 1, false);
			backgroundTracks.add(next);
		}
	}

	public static void destroy() {
		AL.destroy();
	}
}
