package goldeneagle.clock;

public class DerivedClock extends Clock {

	private final Clock clock;
	
	public DerivedClock(Clock clock_, double offset_) {
		super(offset_);
		clock = clock_;
	}
	
	@Override
	protected double baseTime() {
		return clock.get();
	}

}
