package goldeneagle.scene;

import goldeneagle.*;

public class Camera {

	private Frame frame = null;
	private double radius = 1;
	
	public Camera() {
		
	}
	
	public void bindFrame(Frame f) {
		frame = f;
	}
	
	public void setRadius(double r) {
		radius = r;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public Mat4 getViewXform() {
		return frame.getRootTransform().inv();
	}
	
}
