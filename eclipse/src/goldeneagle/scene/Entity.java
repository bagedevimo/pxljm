package goldeneagle.scene;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import goldeneagle.*;

public abstract class Entity extends MovingFrame {

	private BoundingSphere bound = null;
	private double height = 0;
	private List<ShadowCaster> scasters = new ArrayList<ShadowCaster>();
	
	public Entity(Frame parent_) {
		super(parent_);
	}
	
	protected void addShadowCaster(ShadowCaster sc) {
		scasters.add(sc);
	}
	
	protected boolean removeShadowCaster(ShadowCaster sc) {
		return scasters.remove(sc);
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
		SceneManager.multMatrix(getTransformToRoot());
		GL11.glTranslated(0, 0, getHeight() + 0.01);
		draw();
		GL11.glPopMatrix();
	}
	
	public final void doDrawShadowVolume(Vec4 lightpos) {
		GL11.glPushMatrix();
		SceneManager.multMatrix(getTransformToRoot());
		for (ShadowCaster sc : scasters) {
			sc.drawShadowVolume(lightpos, getHeight());
		}
		GL11.glPopMatrix();
	}
	
	protected abstract boolean update(double deltaTime);
	protected abstract void draw();
}
