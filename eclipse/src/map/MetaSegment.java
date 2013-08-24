package map;

import goldeneagle.BoundingBox;
import goldeneagle.scene.TreeEnitity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import map.islandbase.Biome;
import map.islandbase.Triangle;

public class MetaSegment {

	private static final int TreeFill = (int)(Segment.size * Segment.size / 10);
	private static final int PlantFill = (int)(Segment.size * Segment.size / 7);
	
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
		Biome[][] biomes = new Biome[Segment.size][Segment.size];
		
		int xs = Segment.size * xPos;
		int ys = Segment.size * yPos;
		
		for(int x = 0; x<Segment.size; x++){
			for(int y = 0; y<Segment.size; y++){
				for(Triangle t : triList)
					if(t.contains(x+xs, y+ys)){
						tiles[x][y] = TileType.getType(t.biome);
						biomes[x][y] = t.biome;
					}
			}
		}
		
		Segment seg = new Segment(xPos, yPos, tiles);
		//addFoliage(seg, biomes);
		
		return seg;
	}
	
	private void addFoliage(Segment seg, Biome[][] biomes){
		Random rand = new Random(seg.id);
		
		for(int i=0; i<TreeFill; i++){
			double x = rand.nextDouble() * Segment.size;
			double y = rand.nextDouble() * Segment.size;
			if (treeChance(biomes[(int)x][(int)y]) > rand.nextDouble()){
				seg.addEntity(new TreeEnitity(null,x, y, 0));
			}
		}
		
		for(int i=0; i<PlantFill; i++){
			double x = rand.nextDouble() * Segment.size;
			double y = rand.nextDouble() * Segment.size;
			if (plantChance(biomes[(int)x][(int)y]) > rand.nextDouble()){
				seg.addEntity(new TreeEnitity(null,x, y, 0));
			}
		}
	}
	
	private double treeChance(Biome b){
		switch(b) {
		case SNOW: return 0.1;
		case TUNDRA: return 0.06;
		case BARE: return 0.001;
		case SCORCHED: return 0.01;
		case TAIGA: return 0.05;
		case SHRUBLAND: return 0.3;
		case TEMPERATE_DESERT: return 0.01;
		case TEMPERATE_RAIN_FOREST: return 1.0;
		case TEMPERATE_DECIDUOUS_FOREST: return 0.7;
		case TROPICAL_RAIN_FOREST: return 1.0;
		case TROPICAL_SEASONAL_FOREST: return 0.8;
		case SUBTROPICAL_DESERT: return 0.02;
		case GRASSLAND: return 0.05;
		}
		return 0;
	}
	
	private double plantChance(Biome b){
		switch(b) {
		case SNOW: return 0.1;
		case TUNDRA: return 0.1;
		case BARE: return 0.01;
		case SCORCHED: return 0.01;
		case TAIGA: return 0.05;
		case SHRUBLAND: return 0.7;
		case TEMPERATE_DESERT: return 0.1;
		case TEMPERATE_RAIN_FOREST: return 0.5;
		case TEMPERATE_DECIDUOUS_FOREST: return 0.7;
		case TROPICAL_RAIN_FOREST: return 0.5;
		case TROPICAL_SEASONAL_FOREST: return 0.8;
		case SUBTROPICAL_DESERT: return 0.02;
		case GRASSLAND: return 0.05;
		}
		return 0;
	}
	
	public BoundingBox getBound() {
		return bound;
	}
}
