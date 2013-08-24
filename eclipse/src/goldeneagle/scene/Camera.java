package goldeneagle.scene;

import goldeneagle.*;

public class Camera extends Frame {

	private double radius = 1;
	
	public Camera(Frame parent_) {
		super(parent_);
	}
	
	public void bindFrame(Frame f) {
		setParent(f);
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

	@Override
	public Vec3 getPosition() {
		return getParent().getPosition();
	}

	@Override
	public double getRotation() {
		return getParent().getRotation();
	}
	
}
