package goldeneagle.clock;

public class SystemClock extends Clock {

	private static double time_impl() {
		return System.nanoTime() * 1e-9d;
	}
	
	public SystemClock() {
		super(-time_impl());
	}
	
	@Override
	protected double baseTime() {
		return time_impl();
	}

}
