package map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import map.islandbase.MapGenerator;
import map.islandbase.Triangle;

public class SegmentGenerator {
	private final long seed;
	private final MetaSegment[][] metaSegments;
	private static final int regionSize = 256; // segments per region

	private Map<Long, Segment> segmentCache = new HashMap<Long, Segment>();

	public SegmentGenerator(long seed) {
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

		for (Triangle t : tList) {
			for (int z = (int) t.getMinZ(); z <= (int) (t.getMaxZ() + 0.5); z++) {
				for (int x = (int) t.getMinX(); x <= (int) (t.getMaxX() + 0.5); x++) {
					if (x < regionSize && x >= 0 && z < regionSize && z >= 0 && t.contains(x, z)) {
						metaSegments[x][z].addTriangle(t);
					}
				}
			}
		}
	}

	public Segment getSegment(final int posx, final int posy) {

		// location id by long
		long id = Segment.getID(posx, posy);
		// return if in the cache
		if (segmentCache.containsKey(id)) {
			return segmentCache.get(id);
		}

		Segment s;
		if (!(posx < 0) && !(posx >= regionSize - 1) && !(posy < 0) && !(posy >= regionSize - 1)) {
			s = metaSegments[posx][posy].generateSegment();
		} else {
			s = new Segment(posx, posy, null);
		}
		
		segmentCache.put(id, s);
		return s;
	}

	// Takes world coordinates and gives back segment at that vlue
	public Segment segmentAt(double x, double y) {
		int segX = (int) x / Segment.size;
		int segY = (int) y / Segment.size;
		return getSegment(segX, segY);
	}

	public static int segCoordFromWorldCoord(double x) {
		return (int) x / Segment.size;
	}

	public static void main(String[] args) {
		SegmentGenerator sg = new SegmentGenerator(32);
		for (int i = 0; i < 10; i++) {
			Segment s = sg.getSegment(0, i);
			System.out.println(s.xPos + " :: " + s.yPos);
		}
	}
}
