package map;

import goldeneagle.BoundingBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import map.islandbase.MapGenerator;
import map.islandbase.Triangle;

public class SegmentGenerator {
	private final long seed;
	private final MetaSegment[][] metaSegments;
	private static final int regionSize = 256; // genereated map resolution
	public static final int numSegments = 256;
	
	private Map<Long, Segment> segmentCache = new HashMap<Long, Segment>();
	

	private static SegmentGenerator inst = new SegmentGenerator(System.currentTimeMillis());
	
	public static SegmentGenerator getInst(){
		return inst;
	}
	
	public void setSeed(long seed){
		inst = new SegmentGenerator(seed);
	}
	
	private SegmentGenerator(long seed) {
		this.seed = seed;
		metaSegments = new MetaSegment[regionSize][regionSize];
		createRegion();
	}

	private void createRegion() {
		MapGenerator map = new MapGenerator(seed, regionSize);
		map.run();
		// map.look();
		List<Triangle> tList = map.getTriangles();

		//initialise the metaSegments
		for (int y = 0; y < regionSize; y++) {
			for (int x = 0; x < regionSize; x++) {
				metaSegments[x][y] = new MetaSegment(x, y);
			}
		}

		double scale = regionSize / numSegments;
		
		for (Triangle t : tList) {
			t.scale(scale);
			BoundingBox b = t.getBound();
			for (int y = (int)b.minY-1; y>=0 && y<=b.maxY && y < numSegments; y++) {
				for (int x = (int)b.minX; x>=0 && x<=b.maxX && x < numSegments; x++) {
					if (b.intersects(metaSegments[x][y].getBound()) != null) {
						metaSegments[x][y].addTriangle(t);
					}
				}
			}
		}
	}

	//Gets the segment by the segment number
	public Segment getSegment(final int posx, final int posy) {

		// location id by long
		long id = Segment.getID(posx, posy);
		// return if in the cache
		if (segmentCache.containsKey(id)) {
			return segmentCache.get(id);
		}

		Segment s;
		if (posx >= 0 && posx < numSegments && posy >= 0 && posy < numSegments) {
			s = metaSegments[posx][posy].generateSegment();
		} else {
			s = new Segment(posx, posy, null);
		}
		
		segmentCache.put(id, s);
		return s;
	}

	//returns the segment that contains the given global co-oridinate
	public Segment segmentAt(double x, double y) {
		int segX = (int) x / Segment.size;
		int segY = (int) y / Segment.size;
		return getSegment(segX, segY);
	}

	public static int segCoordFromWorldCoord(double x) {
		return (int) x / Segment.size;
	}

	public static void main(String[] args) {
		//SegmentGenerator sg = new SegmentGenerator(32);
		System.out.println("Made it here");
		for (int y = 64; y < 80; y++) {
			for (int x = 64; x < 80; x++) {
				Segment s = SegmentGenerator.getInst().segmentAt(x*32, y*32);
				System.out.printf(s.getTiles()[0][0] + " ");
			}
			System.out.println();
		}
	}
}
