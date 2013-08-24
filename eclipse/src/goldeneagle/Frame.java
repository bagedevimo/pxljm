package goldeneagle;

import goldeneagle.clock.Clock;

public abstract class Frame {

	/**
	 * Represents the root of a Scene. Only root frames should have a null
	 * parent.
	 */
	public static class Root extends Frame {

		public Root(Clock clock_) {
			super(clock_);
		}

		@Override
		public Vec3 getPosition() {
			return Vec3.zero;
		}

		@Override
		public double getRotation() {
			return 0;
		}

	}

	// TODO allow changing parent (change rel positions so no global change)

	private Frame parent;
	private final Clock clock;
	private Mat4 cached_root_xform = null;
	private Mat4 cached_rootinv_xform = null;
	private double cache_time;

	/**
	 * Only for construction of root frames
	 * 
	 * @param clock_
	 */
	private Frame(Clock clock_) {
		if (clock_ == null)
			throw new NullPointerException();
		parent = null;
		clock = clock_;
	}

	public Frame(Frame parent_) {
		if (parent_ == null)
			throw new NullPointerException();
		parent = parent_;
		clock = parent.getClock();
	}

	public final Frame getParent() {
		return parent;
	}

	public final Clock getClock() {
		return clock;
	}

	public abstract Vec3 getPosition();

	public abstract double getRotation();

	/**
	 * @return Matrix to transform to the root
	 */
	public Mat4 getTransformToRoot() {
		if (parent == null)
			return new Mat4();
		return parent.getTransformToRoot().mul(Mat4.translate(getPosition()))
				.mul(Mat4.rotateZ(getRotation()));
	}

	/**
	 * @param f
	 * @return Matrix to transform to a frame
	 */
	public Mat4 getTransformTo(Frame f) {
		// this can be done better...
		return f.getTransformToRoot().inv().mul(getTransformToRoot());
	}

	/**
	 * Transform a vector to the root
	 * @param p
	 * @return
	 */
	public Vec3 transformToRoot(Vec3 p) {
		if (cached_root_xform == null || clock.get() > cache_time) {
			cached_root_xform = getTransformToRoot();
		}
		return cached_root_xform.mul(p);
	}
	
	/**
	 * Transform a vector from the root to this frame
	 * @param p
	 * @return
	 */
	public Vec3 transformFromRoot(Vec3 p) {
		if (cached_rootinv_xform == null || clock.get() > cache_time) {
			cached_rootinv_xform = getTransformToRoot().inv();
		}
		return cached_rootinv_xform.mul(p);
	}
	
	/**
	 * Transform a vector to a frame from this one
	 * @param f
	 * @param p
	 * @return
	 */
	public Vec3 transformTo(Frame f, Vec3 p) {
		return f.transformFromRoot(transformToRoot(p));
	}
	
	/**
	 * Transform a vector from a frame to this one
	 * @param f
	 * @param p
	 * @return
	 */
	public Vec3 transformFrom(Frame f, Vec3 p) {
		return transformFromRoot(f.transformToRoot(p));
	}
	
	/**
	 * @return The position of this frame relative to the root
	 */
	public Vec3 getGlobalPosition() {
		return transformToRoot(Vec3.zero);
	}
}
