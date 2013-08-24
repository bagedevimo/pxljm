package goldeneagle.scene;

import goldeneagle.*;

public class Camera extends BoundFrame {

	private double radius = 1;
	
	public Camera(Frame parent_) {
		super(parent_);
	}
	
	public void setRadius(double r) {
		radius = r;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public Mat4 getViewTransform() {
		return getTransformToRoot().inv();
	}
	
}
