package goldeneagle;

import goldeneagle.scene.SceneManager;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.nio.IntBuffer;

import org.lwjgl.openal.AL10;
import org.newdawn.slick.openal.WaveData;

public class BackgroundMusic extends AudioTrack {
	public BackgroundMusic(String path) throws Exception {
		super();
		
		FileInputStream fis = new FileInputStream(path);
		WaveData waveFile = WaveData.create(new BufferedInputStream(fis));
		
		int err = AL10.alGetError();
		if(err != AL10.AL_NO_ERROR)
			throw new Exception("Something error: " + err);
		
		AL10.alGenBuffers(buffer);
		AL10.alBufferData(buffer.get(0), waveFile.format, waveFile.data, waveFile.samplerate);
		waveFile.dispose();
		
		err = AL10.alGetError();
		if(err != AL10.AL_NO_ERROR)
			throw new Exception("Something error: " + err);
	}
}
