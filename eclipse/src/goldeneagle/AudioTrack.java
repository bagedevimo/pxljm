package goldeneagle;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public abstract class AudioTrack {
	IntBuffer buffer, source;
	
	public AudioTrack() {
		buffer = BufferUtils.createIntBuffer(10);
		source = BufferUtils.createIntBuffer(10);
	}
	
}
