package goldeneagle;

import goldeneagle.scene.Frame;

public class MovingFrame extends Frame {

	private double t0 = 0;
	private Vec3 p0 = Vec3.zero;
	private Vec3 v0 = Vec3.zero;
	private double ap0 = 0;
	private double av0 = 0;
	
	public MovingFrame(Frame parent_) {
		super(parent_);
	}
	
	@Override
	public Vec3 getPosition() {
		return p0.add(v0.mul(getClock().get() - t0));
	}

	@Override
	public double getRotation() {
		return ap0 + av0 * (getClock().get() - t0);
	}
	
	/**
	 * Set linear motion at current time, and extrapolate angular position to match
	 * @param p0
	 * @param v0
	 */
	public void setLinear(Vec3 p0, Vec3 v0) {
		this.p0 = p0;
		this.v0 = v0;
		double t1 = getClock().get();
		// extrapolate angular position to current time
		ap0 = ap0 + av0 * (t1 - t0);
		t0 = t1;
	}
	
	/**
	 * Set angular motion at current time, and extrapolate linear position to match
	 * @param ap0
	 * @param av0
	 */
	public void setAngular(double ap0, double av0) {
		this.ap0 = ap0;
		this.av0 = av0;
		double t1 = getClock().get();
		// extrapolate position to current time
		p0 = p0.add(v0.mul(t1 - t0));
		t0 = t1;
	}
	
	/**
	 * Set all motion parameters
	 * @param t0
	 * @param p0
	 * @param v0
	 * @param ap0
	 * @param av0
	 */
	public void setMotion(double t0, Vec3 p0, Vec3 v0, double ap0, double av0) {
		this.t0 = t0;
		this.p0 = p0;
		this.v0 = v0;
		this.ap0 = ap0;
		this.av0 = av0;
	}
	
	/**
	 * Set linear and angular motion at current time
	 * @param p0
	 * @param v0
	 * @param ap0
	 * @param av0
	 */
	public void setMotion(Vec3 p0, Vec3 v0, double ap0, double av0) {
		setMotion(getClock().get(), p0, v0, ap0, av0);
	}

}
