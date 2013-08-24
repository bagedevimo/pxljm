package goldeneagle;

import java.nio.ByteBuffer;

public class ResourceInfo {
	private String Path;
	private ByteBuffer Buffer;
	private int Width;
	private int Height;
	private int TextureID;
	
	public ResourceInfo(String path, ByteBuffer data, int width, int height) {
		this.Path = path;
		this.Buffer = data;
		this.Width = width;
		this.Height = height;
		this.TextureID = -1;
	}
	
	public boolean isGLReady() {
		return this.TextureID != -1;
	}
	
	public String getPath() {
		return this.Path;
	}
	
	public int getWidth() {
		return this.Width;
	}
	
	public int getHeight() {
		return this.Height;
	}
	
	public ByteBuffer getBuffer() {
		return this.Buffer;
	}

	public void setTextureID(int bindGLTexture) {
		this.TextureID = bindGLTexture;
	}
	
	public int getTextureID() throws Exception {
		if(this.TextureID < 0)
			throw new Exception("Texture not bound");
		
		return this.TextureID;
	}
}
