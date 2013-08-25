package map;

import goldeneagle.BoundingBox;
import goldeneagle.Vec3;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import map.islandbase.Biome;
import map.islandbase.Triangle;

public class MetaSegment {

	private static final int TreeFill = (int)(Segment.size * Segment.size / 4);
	private static final double TreeRadius = 5;
	private static final int PlantFill = (int)(Segment.size * Segment.size / 4);
	private static final double PlantRadius = 1;
	
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
		
		
		for(int x = 0; x<Segment.size; x++){
			for(int y = 0; y<Segment.size; y++){
				boolean set = false;
				for(Triangle t : triList)
					if(t.contains(x/(double)Segment.size+xPos, y/(double)Segment.size+yPos)){
						tiles[x][y] = TileType.getType(t.biome);
						biomes[x][y] = t.biome;
						set = true;
					}
				if(!set){ //default to ocean
					tiles[x][y] = TileType.UNKNOWN;
				}
			}
		}
		
//		//assuming that there is only one change per segment
		//trying here to change the lines to more jaggeded
//		Point topRight = null, bottomLeft = null;
//		
//		for (int i = 0; i < Segment.size-1 && (topRight == null || bottomLeft == null); i++) {
//			if (topRight != null && tiles[i][0] != tiles[i+1][0]) {
//				topRight = new Point(i,0);
//			} if (bottomLeft != null && tiles[0][i] != tiles[0][i+1]) {
//				bottomLeft = new Point(0,i);
//			}
//		}
//		
//		for (int i = 0; i < Segment.size-1 && (topRight == null || bottomLeft == null); i++) {
//			if (topRight != null && tiles[i][Segment.size-1] != tiles[i+1][Segment.size-1]) {
//				topRight = new Point(i,Segment.size-1);
//			} if (bottomLeft != null && tiles[Segment.size-1][i] != tiles[Segment.size-1][i+1]) {
//				bottomLeft = new Point(Segment.size-1,i);
//			}
//		}
//		
//		if (topRight != null && bottomLeft != null) {
//			Point middle = new Point((topRight.x + bottomLeft.x)/2,(topRight.y + bottomLeft.y)/2);
//		}
//		
		
		
		
		Segment seg = new Segment(xPos, yPos, tiles);
		addFoliage(seg, biomes);
		
		return seg;
	}
//	
//	private void asd(Point left, Point right, TileType[][] tiles, Random rand) {
//		Point middle = new Point((left.x + right.x)/2,(left.y + right.y)/2);
//		if (!middle.equals(right) && !middle.equals(left)) {
//			int deltaX = (int) ((rand.nextDouble() - 0.5) * middle.x);
//			int deltaY = (int) ((rand.nextDouble() - 0.5) * middle.y);
//			Point newMiddle = new Point(middle.x+deltaX, middle.y+deltaY);
//			
//			//TODO
//		}
//	}
	
	private void addFoliage(Segment seg, Biome[][] biomes){
		Random rand = new Random(seg.id);
		
		List<Foliage> foliage = new ArrayList<Foliage>();
		
		//HACKYHACKYHACKY
		Foliage center = new Foliage(Segment.size, Segment.size, 7);
		
		//Trees
		for(int i=0; i<TreeFill; i++){
			double x = rand.nextDouble() * Segment.size;
			double y = rand.nextDouble() * Segment.size;
			if (treeChance(biomes[(int)x][(int)y]) > rand.nextDouble()){
				Foliage newF = new Foliage(x, y, TreeRadius + ((TreeRadius / 3) * rand.nextGaussian()));
				boolean collision = false;
				for(int j=0; j<foliage.size(); j++){
					if(foliage.get(j).collision(newF)){
						collision = true;
						break;
					}
				}
				if(!collision)
					foliage.add(newF);
			}
		}
		
		List<Vec3> trees = new ArrayList<Vec3>();
		for(int i=0; i<foliage.size(); i++){
			Foliage f = foliage.get(i);
			if((seg.xPos!=127 && seg.yPos != 127) || !f.collision(center))
			trees.add(new Vec3(f.xPos, f.yPos, f.radius));
		}
		
		foliage.clear();
		
		//Plants
		for(int i=0; i<PlantFill; i++){
			double x = rand.nextDouble() * Segment.size;
			double y = rand.nextDouble() * Segment.size;
			if (plantChance(biomes[(int)x][(int)y]) > rand.nextDouble()){
				Foliage newF = new Foliage(x, y, PlantRadius + ((PlantRadius / 3) * rand.nextGaussian()));
				boolean collision = false;
				for(int j=0; j<foliage.size(); j++){
					if(foliage.get(j).collision(newF)){
						collision = true;
						break;
					}
				}
				if(!collision){
					foliage.add(newF);
					i = PlantFill - placePlantGroup(foliage, rand, x, y, PlantFill-i);
				}
			}
		}
		
		List<Vec3> plants = new ArrayList<Vec3>();
		for(int i=0; i<foliage.size(); i++){
			Foliage f = foliage.get(i);
			if((seg.xPos!=127 && seg.yPos != 127) || !f.collision(center))
			plants.add(new Vec3(f.xPos, f.yPos, f.radius));
		}
		
		List<Vec3> wood = getWood(trees);
		List<Vec3> berries = getBerries(plants);
		
		seg.addPlants(plants);
		seg.addTrees(trees);
		seg.addWood(wood);
		seg.addBerries(berries);
	}
	
	//returns the number  of bushes  remained t b planted
	private int placePlantGroup(List<Foliage> foliage, Random rand, double x, double y, int num){
		int numB = (int) (num * rand.nextDouble());
		for(int i =0; i<numB; i++){
			foliage.add(new Foliage(x+rand.nextGaussian()*PlantRadius*2, y+rand.nextGaussian()*PlantRadius*2, PlantRadius + ((PlantRadius / 3) * rand.nextGaussian())));
		}
		return num - numB;
	}
	
	private List<Vec3> getBerries(List<Vec3> plants) {
		// TODO Auto-generated method stub
		ArrayList<Vec3> berries = new ArrayList<Vec3>();
		berries.add(new Vec3(10, 10));
		return berries;
	}

	private List<Vec3> getWood(List<Vec3> trees) {
		// TODO Auto-generated method stub
		ArrayList<Vec3> wood = new ArrayList<Vec3>();
		wood.add(new Vec3(10, 20));
		return wood;
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
		default: return 0;
		}
	}
	
	private double plantChance(Biome b){
		switch(b) {
		case BEACH: return 0;
		case ICE: return 0;
		case LAKE: return 0;
		case MARSH: return 0;
		case OCEAN: return 0;
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
	
	private class Foliage{
		public final double xPos;
		public final double yPos;
		public final double radius;
		Foliage(double xPos_, double yPos_, double radius_){
			xPos = xPos_;
			yPos = yPos_;
			radius = radius_;
		}
		
		public boolean collision(Foliage f){
			if(Math.hypot((xPos - f.xPos), (yPos - f.yPos)) < (radius + f.radius))
				return true;
			return false;
		}
	}
	
	public BoundingBox getBound() {
		return bound;
	}
}
