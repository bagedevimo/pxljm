package goldeneagle.scene;

import org.lwjgl.opengl.GL11;

import goldeneagle.*;

public abstract class Entity extends MovingFrame {

	private BoundingSphere bound = null;
	private double height = 0;
	
	public Entity(Frame parent_) {
		super(parent_);
	}
	
	public BoundingSphere getBound() {
		return bound;
	}
	
	protected void setBound(BoundingSphere bs) {
		bound = bs;
	}
	
	protected double getHeight() {
		return height;
	}
	
	protected void setHeight(double h) {
		height = h;
	}
	
	public final void doUpdate(double deltaTime) {
		update(deltaTime);
	}
	
	public final void doDraw() {
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, getHeight());
		draw();
		GL11.glPopMatrix();
	}
	
	protected abstract void update(double deltaTime);
	protected abstract void draw();
}
