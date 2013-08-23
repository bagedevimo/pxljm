package map;

import goldeneagle.Vec3;

public class Segment {
	public static final int size = 16;
	public static final float vertScale = 8f;
	public static final float horzScale = 2f;

	private float[][] heightmap = null;

	public final int xPos;
	public final int zPos;
	public final long id;

	public static long getID(int posx, int posz) {
		return (long) ((long) posx << 32) | (((long) (posz)) & 0xFFFFFFFFL);
	}

	public Segment(int _xPos, int _zPos, float[][] heightmap) {
		xPos = _xPos;
		zPos = _zPos;
		id = getID(_xPos, _zPos);
		this.heightmap = heightmap;
	}

	public float[][] getData() {
		return this.heightmap;
	}

	public boolean contains(double x, double z) {
		return x >= xPos * size * horzScale
				&& x < (xPos + 1) * size * horzScale
				&& z >= zPos * size * horzScale
				&& z < (zPos + 1) * size * horzScale;
	}

	// values go from 0 to size (inclusive)
	private float heightAt(int x, int z) {
		return heightmap[(int) x + 1][(int) z + 1] * vertScale;
	}

	// values go from 0 to size (inclusive)
	private Vec3 normalAt(int x, int z) {
		float h = heightAt(x, z);

		Vec3 d0 = new Vec3(-2, heightAt(x - 1, z) - h, 0).unit();
		Vec3 d1 = new Vec3(0, heightAt(x, z + 1) - h, 2).unit();
		Vec3 d2 = new Vec3(2, heightAt(x + 1, z) - h, 0).unit();
		Vec3 d3 = new Vec3(0, heightAt(x, z - 1) - h, -2).unit();

		return (d0.cross(d1).add(d1.cross(d2)).add(d2.cross(d3)).add(d3
				.cross(d0))).unit();
	}

	// values go from 0 to size (inclusive)
	private Vec3 vectorAt(int x, int z) {
		return new Vec3(x, heightAt(x, z), z);
	}

	/**
	 * Translates the given relative value x, to a global position based on the
	 * segment position given. Assumes x is a value between 0 (inclusive) and
	 * Segment.size (exclusive).
	 * 
	 * @param x
	 *            the segment relative value
	 * @param segPos
	 *            one of Segment.xPos or Segment.zPos
	 * @return global position of that value
	 */
	private double globalPos(double x, int segPos) {
		return (x + (segPos * size)) * horzScale;
	}

	/**
	 * Translates the global position given into relative position between 0
	 * (inclusive) and Segment.size (exclusive).
	 * 
	 * @param x
	 *            The global position
	 * @return The relative position to the segment
	 */
	private static double relativePos(double x) {
		return (x / horzScale) % size;
	}

	/**
	 * Returns the height at the given global positions inside the Segment.
	 * Returns 0 if the position is not contained inside the segment.
	 * 
	 * @param x
	 *            global x position
	 * @param z
	 *            global z position
	 * @return the height of the segment at the given global position
	 */
	public double getHeight(double x, double z) {
		if (contains(x, z)) {

			// relative positions
			x = relativePos(x);
			z = relativePos(z);

			// relative positions rounded down
			int relX = (int) x;
			int relZ = (int) z;

			double topLeft = heightAt(relX, relZ);
			double topRight = heightAt(relX + 1, relZ);

			double botLeft = heightAt(relX, relZ + 1);
			double botRight = heightAt(relX + 1, relZ + 1);

			double left = (botLeft - topLeft) * (z % 1) + topLeft;
			double right = (botRight - topRight) * (z % 1) + topRight;

			return (right - left) * (x % 1) + left;

		}
		return 0;
	}

	/**
	 * Returns the flat normal at the given global positions inside the Segment.
	 * The flat normal is the normal calculated by taking the surrounding points
	 * at the highest LOD and working out the normal of the plane they create.
	 * Returns 0 if the position is not contained inside the segment
	 * 
	 * @param x
	 *            global x position
	 * @param z
	 *            global z position
	 * @return the flat normal of the segment at the given global position
	 */
	public Vec3 getFlatNormal(double x, double z) {
		if (contains(x, z)) {

			// relative positions
			x = relativePos(x);
			z = relativePos(z);

			// relative positions rounded down
			int relX = (int) x;
			int relZ = (int) z;

			Vec3 p1, p2, p3;

			// if an even cumulative index make the triangle cut across
			// the square go from top left to bottom right
			if ((relX + relZ) % 2 == 0) {

				// if point is on the top right triangle
				if ((x % 1) > (z % 1)) {
					p1 = vectorAt(relX + 1, relZ);
					p2 = vectorAt(relX, relZ);
					p3 = vectorAt(relX + 1, relZ + 1);
					// else point is in bottom left triangle
				} else {
					p1 = vectorAt(relX, relZ);
					p2 = vectorAt(relX, relZ + 1);
					p3 = vectorAt(relX + 1, relZ + 1);
				}

				// else from top right to bottom left
			} else {

				// if point is on the top left triangle
				if ((x % 1) + (z % 1) < 1) {
					p1 = vectorAt(relX + 1, relZ);
					p2 = vectorAt(relX, relZ);
					p3 = vectorAt(relX, relZ + 1);
					// else point is in bottom right triangle
				} else {
					p1 = vectorAt(relX, relZ + 1);
					p2 = vectorAt(relX + 1, relZ + 1);
					p3 = vectorAt(relX + 1, relZ);
				}
			}

			return Vec3.planeNorm(p1, p2, p3);
		}
		return new Vec3(0, 1, 0);
	}

	/**
	 * Returns the normal at the given global positions inside the Segment.
	 * returns a normal that points straight up if position is not contained
	 * inside the segment.
	 * 
	 * @param x
	 *            global x position
	 * @param z
	 *            global z position
	 * @return the normal of the segment at the given global position
	 */
	public Vec3 getNormal(double x, double z) {
		if (contains(x, z)) {

			// relative positions
			x = relativePos(x);
			z = relativePos(z);

			// relative positions rounded down
			int relX = (int) x;
			int relZ = (int) z;

			Vec3 topLeft = normalAt(relX, relZ);
			Vec3 topRight = normalAt(relX + 1, relZ);

			Vec3 botLeft = normalAt(relX, relZ + 1);
			Vec3 botRight = normalAt(relX + 1, relZ + 1);

			Vec3 left = (botLeft.sub(topLeft)).mul(z % 1).add(topLeft);
			Vec3 right = (botRight.sub(topRight)).mul(z % 1).add(topRight);

			return (right.sub(left)).mul((z % 1)).add(left);

		}
		return new Vec3(0, 1, 0);
	}
}
