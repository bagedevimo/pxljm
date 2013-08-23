package goldeneagle;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import org.newdawn.slick.opengl.TextureLoader;

public class ResourceLoader extends Thread {
	private Queue<ResourceLoadUnit> loadQueue = new LinkedList<ResourceLoadUnit>();
	private Thread threadHandle = null;
	
	public void Add(String path) {
		String[] pathParts = path.split(".");
		String ext = pathParts[pathParts.length-1];
		switch(ext.toLowerCase()) {
			case "jpg":
			case "jpeg":
				this.loadQueue.add(new ResourceLoadUnit("JPG", path));
			break;
			
			case "png":
				this.loadQueue.add(new ResourceLoadUnit("PNG", path));
			break;
				
			default:
				System.err.printf("Unknown extension: %s", ext);
				break;
		}
	}
	
	public void Start() {
		this.threadHandle = new Thread(this);
		this.threadHandle.start();
	}
	
	public void run() {
		while(!this.loadQueue.isEmpty())
		{
			ResourceLoadUnit rlu = this.loadQueue.remove();
			try {
				switch(rlu.Type) {
					case "JPG":
					case "PNG":
							ResourceCache.AddTexture(TextureLoader.getTexture(rlu.Type, org.newdawn.slick.util.ResourceLoader.getResourceAsStream(rlu.Path)));
	
					break;
				}
				
			} catch(IOException e) {
				System.err.printf("Unable to load resource: %s", rlu.Path);
			}
		}
	}
}
