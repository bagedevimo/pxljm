package goldeneagle.clock;

public abstract class Clock {

	protected double offset;
	protected double paused_at;
	protected boolean paused;
	
	protected abstract double baseTime();
	
	protected Clock(double offset_) {
		offset = offset_;
	}
	
	/**
	 * @return Current time
	 */
	public double get() {
		return (paused ? paused_at : baseTime()) + offset;
	}
	
	/**
	 * Set current time
	 * @param now
	 */
	public void set(double now) {
		offset = now - baseTime();
	}
	
	/**
	 * Increment current time
	 * @param d
	 */
	public void inc(double d) {
		offset += d;
	}
	
	/**
	 * Decrement current time
	 * @param d
	 */
	public void dec(double d) {
		offset -= d;
	}
	
	/**
	 * Halt the clock's progression, except for changing its offset
	 */
	public void pause() {
		if (!paused) {
			paused_at = baseTime();
			paused = true;
		}
	}
	
	/**
	 * Restart the clock's progression at the paused time plus any changes to its offset while paused
	 */
	public void play() {
		if (paused) {
			offset -= baseTime() - paused_at;
			paused = false;
		}
	}
	
}
