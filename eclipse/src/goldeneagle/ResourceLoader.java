package goldeneagle;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.newdawn.slick.opengl.TextureLoader;

enum ResourceType {
	PNG,
	JPG
}

public class ResourceLoader extends Thread {
	private static final int BYTES_PER_PIXEL = 4;//3 for RGB, 4 for RGBA
	private Queue<ResourceLoadUnit> loadQueue = new LinkedList<ResourceLoadUnit>();
	private Thread threadHandle = null;
	private int nResources = 0;
	
	public void Add(String path) {
		System.out.printf("Loading: %s\n", path);
		String[] pathParts = path.split("\\.");
		System.out.printf("#parts: %d\n", pathParts.length);
		String ext = pathParts[pathParts.length-1].toUpperCase();
		if(ext.equals("JPG") || ext.equals("JPEG"))
			this.loadQueue.add(new ResourceLoadUnit(ResourceType.JPG, path));
		else if(ext.equals("PNG"))
			this.loadQueue.add(new ResourceLoadUnit(ResourceType.PNG, path));
		else
			System.err.printf("Unknown extension: '%s'\n", ext);
	}
	
	public void Start() {
		this.nResources = this.loadQueue.size();
		this.threadHandle = new Thread(this);
		this.threadHandle.start();
	}
	
	public boolean isComplete() {
		return this.loadQueue.size() == 0;
	}
	
	public void run() {
		while(!this.loadQueue.isEmpty())
		{
			ResourceLoadUnit rlu = this.loadQueue.peek();
			try {
				switch(rlu.Type) {
					case JPG:
					case PNG:
							BufferedImage image = this.loadImage(rlu.Path);
							ResourceCache.AddTexture(rlu.Path, this.loadTexture(image), image.getWidth(), image.getHeight());
					break;
				}
				
			} catch(IOException e) {
				System.err.printf("Unable to load resource: %s", rlu.Path);
			}
			this.loadQueue.remove();
		}
	}

    
       public ByteBuffer loadTexture(BufferedImage image){

          int[] pixels = new int[image.getWidth() * image.getHeight()];
            image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

            ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL); //4 for RGBA, 3 for RGB

            for(int y = 0; y < image.getHeight(); y++){
                for(int x = 0; x < image.getWidth(); x++){
                    int pixel = pixels[y * image.getWidth() + x];
                    buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                    buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                    buffer.put((byte) (pixel & 0xFF));               // Blue component
                    buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
                }
            }

            buffer.flip(); 
            return buffer;
       }

       public BufferedImage loadImage(String path) throws IOException
       {
           return ImageIO.read(new File(path));
       }
}
