package goldeneagle;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL12;

public class ResourceCache {
	private static final Map<String, ResourceInfo> textures = new HashMap<String, ResourceInfo>();
	
	public static void AddTexture(String path, ByteBuffer texture, int width, int height) {
		if(textures.containsKey(path))
			return;
		
		textures.put(path, new ResourceInfo(path, texture, width, height));
	}
	
	public static int GetGLTexture(String path) throws Exception {
		if(!textures.containsKey(path))
			throw new Exception("Asset not found");
		
		ResourceInfo ri = textures.get(path);
		if(!ri.isGLReady())
		{
			ri.setTextureID(bindGLTexture(ri));
		}
		
		return ri.getTextureID();
			
	}
	
	private static int bindGLTexture(ResourceInfo res) {
		int textureID = glGenTextures(); //Generate texture ID
		glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, res.getWidth(), res.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, res.getBuffer());
		
		return textureID;
	}
}
