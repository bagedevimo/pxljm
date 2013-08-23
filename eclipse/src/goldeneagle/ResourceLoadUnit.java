package goldeneagle;

public class ResourceLoadUnit {
	public final ResourceType Type;
	public final String Path;
	
	public ResourceLoadUnit(ResourceType type, String path) {
		this.Type = type;
		this.Path = path;
	}
}
