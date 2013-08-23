package goldeneagle;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL12;

public class ResourceCache {
	public static void AddTexture(ByteBuffer texture, int width, int height) {
		System.err.println("TODO: Implement texture cache storing textures");
	}
	
	private static int bindGLTexture(ByteBuffer texture, int width, int height) {
		int textureID = glGenTextures(); //Generate texture ID
		glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, texture);
		
		return textureID;
	}
}
