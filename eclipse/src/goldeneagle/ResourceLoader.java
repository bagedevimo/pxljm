package goldeneagle;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import org.newdawn.slick.opengl.TextureLoader;

enum ResourceType {
	PNG,
	JPG
}

public class ResourceLoader extends Thread {
	private Queue<ResourceLoadUnit> loadQueue = new LinkedList<ResourceLoadUnit>();
	private Thread threadHandle = null;
	
	public void Add(String path) {
		String[] pathParts = path.split(".");
		String ext = pathParts[pathParts.length-1].toUpperCase();
		if(ext == "JPG" || ext == "JPEG")
			this.loadQueue.add(new ResourceLoadUnit(ResourceType.JPG, path));
		else if(ext == "PNG")
			this.loadQueue.add(new ResourceLoadUnit(ResourceType.PNG, path));
		else
			System.err.printf("Unknown extension: %s", ext);
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
					case JPG:
					case PNG:
							ResourceCache.AddTexture(TextureLoader.getTexture(rlu.Type.toString(), org.newdawn.slick.util.ResourceLoader.getResourceAsStream(rlu.Path)));
					break;
				}
				
			} catch(IOException e) {
				System.err.printf("Unable to load resource: %s", rlu.Path);
			}
		}
	}
}
