package goldeneagle;

import goldeneagle.clock.Clock;

public abstract class Frame {
	
	/**
	 * Represents the root of a Scene. Only root frames should have a null parent.
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
	
	/**
	 * Only for construction of root frames
	 * @param clock_
	 */
	private Frame(Clock clock_) {
		if (clock_ == null) throw new NullPointerException();
		parent = null;
		clock = clock_;
	}
	
	public Frame(Frame parent_) {
		if (parent_ == null) throw new NullPointerException();
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
	public Mat4 getRootTransform() {
		// TODO frame root transform
		return null;
	}
}
