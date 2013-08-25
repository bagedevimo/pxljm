package goldeneagle.entities;

import goldeneagle.clock.DerivedClock;
import goldeneagle.scene.Frame;

public class Animation {
	private String name;
	private Integer nFrames;
	private double animationStart;
	private DerivedClock animationClock;
	
	private double frameTime = 1/20f;
	
	public Animation(Frame parent, String n, int f) {
		name = n;
		this.nFrames = f;
		this.animationClock = new DerivedClock(parent.getClock(), 0);
		this.animationStart = this.animationClock.get();
	}
	
	public int getFrame() {
		double dT = (this.animationClock.get() - this.animationStart);
		int frame = (int)(dT / this.frameTime);
		if(frame >= nFrames) {
			this.animationStart = this.animationClock.get();
			dT = (this.animationClock.get() - this.animationStart);
			frame = (int)(dT / this.frameTime);
		}
		return frame;
	}
	
	public String getFrameTexture() {
		return String.format("assets/sprites/%s/%s%04d.png", this.name, this.name, this.getFrame()+1);
	}
	
	public void pause() {
		this.animationClock.pause();
	}
	
	public void play() {
		this.animationClock.play();
	}
}
