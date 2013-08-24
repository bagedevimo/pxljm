package map;

import goldeneagle.BoundingBox;

import java.util.ArrayList;
import java.util.List;

import map.islandbase.Triangle;

public class MetaSegment {

	private List<Triangle> triList = new ArrayList<Triangle>();
	private final BoundingBox bound;
	private final int xPos;
	private final int yPos;
	
	
	public MetaSegment(int x, int y){
		xPos = x;
		yPos = y;
		bound = new BoundingBox(x, y, x+Segment.size, y+Segment.size);
	}
	
	public void addTriangle(Triangle t){
		triList.add(t);
	}
	
	public Segment generateSegment(){
		TileType[][] tiles = new TileType[Segment.size][Segment.size];
		
		int xs = Segment.size * xPos;
		int ys = Segment.size * yPos;
		
		for(int x = 0; x<Segment.size; x++){
			for(int y = 0; y<Segment.size; y++){
				for(Triangle t : triList)
					if(t.contains(x+xs, y+ys))
						tiles[x][y] = TileType.getType(t.biome);
			}
		}
		
		Segment seg = new Segment(xPos, yPos, tiles); 
		
		//TODO Generate entities here
		
		
		return seg;
	}
	
	public BoundingBox getBound() {
		return bound;
	}
}
