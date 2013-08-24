package map;

import java.util.ArrayList;
import java.util.List;

import goldeneagle.Vec3;
import goldeneagle.scene.Entity;

public class Segment {
	public static final int size = 32;

	public final int xPos;
	public final int yPos;
	public final long id;
	private final TileType[][] tiles;
	
	private List<Vec3> trees;
	private List<Vec3> plants;
	
	private final List<Entity> entities;

	public static long getID(int posx, int posz) {
		return (long) ((long) posx << 32) | (((long) (posz)) & 0xFFFFFFFFL);
	}

	public Segment(int x, int y, TileType[][] t) {
		xPos = x;
		yPos = y;
		id = getID(xPos, yPos);
		if(t!=null)
			tiles = t;
		else {
			tiles = new TileType[size][size];
			for(int xs = x; xs < size; xs++)
				for(int ys = y; ys < size; ys++)
					tiles[xs][ys] = TileType.UNKNOWN;
		}
		entities = new ArrayList<Entity>();
	}
	
	public TileType[][] getTiles(){
		return tiles;
	}

	public boolean contains(double x, double y) {
		return x >= xPos * size
				&& x < (xPos + 1) * size
				&& y >= yPos * size
				&& y < (yPos + 1) * size;
	}

	// values go from 0 to size (inclusive)
	public TileType tileAt(int x, int y) {
		return tiles[(int) x + 1][(int) y + 1];
	}
	
	public void addTrees(List<Vec3> trees){
		this.trees = trees;
	}
	
	public void addPlants(List<Vec3> plants){
		this.plants = plants;
	}
	
	public List<Vec3> getTrees(){
		return trees;
	}
	
	public List<Vec3> getPlants(){
		return plants;
	}
	
	public List<Entity> getEntities(Entity e){
		return new ArrayList<Entity>(entities); 
	}

//	// values go from 0 to size (inclusive)
//	private Vec3 normalAt(int x, int z) {
//		float h = heightAt(x, z);
//
//		Vec3 d0 = new Vec3(-2, heightAt(x - 1, z) - h, 0).unit();
//		Vec3 d1 = new Vec3(0, heightAt(x, z + 1) - h, 2).unit();
//		Vec3 d2 = new Vec3(2, heightAt(x + 1, z) - h, 0).unit();
//		Vec3 d3 = new Vec3(0, heightAt(x, z - 1) - h, -2).unit();
//
//		return (d0.cross(d1).add(d1.cross(d2)).add(d2.cross(d3)).add(d3
//				.cross(d0))).unit();
//	}
//
//	// values go from 0 to size (inclusive)
//	private Vec3 vectorAt(int x, int z) {
//		return new Vec3(x, heightAt(x, z), z);
//	}
//
//	/**
//	 * Translates the given relative value x, to a global position based on the
//	 * segment position given. Assumes x is a value between 0 (inclusive) and
//	 * Segment.size (exclusive).
//	 * 
//	 * @param x
//	 *            the segment relative value
//	 * @param segPos
//	 *            one of Segment.xPos or Segment.zPos
//	 * @return global position of that value
//	 */
//	private double globalPos(double x, int segPos) {
//		return (x + (segPos * size));
//	}
//
//	/**
//	 * Translates the global position given into relative position between 0
//	 * (inclusive) and Segment.size (exclusive).
//	 * 
//	 * @param x
//	 *            The global position
//	 * @return The relative position to the segment
//	 */
//	private static double relativePos(double x) {
//		return x % size;
//	}
}
