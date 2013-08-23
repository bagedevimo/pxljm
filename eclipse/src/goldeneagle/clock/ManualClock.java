package goldeneagle.clock;

public class ManualClock extends DerivedClock {

	private double now;

	@Override
	protected double baseTime() {
		return now;
	}

	public ManualClock(Clock clock_, double offset_) {
		super(clock_, offset_);
	}

	/**
	 * Update to the current time of the source clock
	 */
	public void update() {
		now = super.baseTime();
	}

	/**
	 * Update to the current time of the source clock and adjust the offset so the time on this clock does not change.
	 */
	public void reset() {
		double now_now = super.baseTime();
		offset -= now_now - now;
		now = now_now;
	}

}
