package goldeneagle.scene;

import goldeneagle.Vec3;

public class ProxyFrame extends Frame {

	public ProxyFrame(Frame parent_) {
		super(parent_);
	}

	public void bindFrame(Frame f) {
		setParent(f);
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
