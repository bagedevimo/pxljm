package goldeneagle.entities;

import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glNormal3d;
import static org.lwjgl.opengl.GL11.glVertex3d;
import goldeneagle.scene.Entity;
import goldeneagle.scene.Frame;
import goldeneagle.scene.Scene;

public class TestEntity extends Entity {

	public TestEntity(Frame parent_) {
		super(parent_);
	}

	@Override
	public void draw() {
		glBegin(GL_POLYGON);
		glColor3d(1, 0, 0);
		glNormal3d(0, 0, 1);
		glVertex3d(0, -1, -1);
		glVertex3d(1, 0, -1);
		glVertex3d(0, 1, -1);
		glVertex3d(-1, 0, -1);
		glEnd();
	}

	@Override
	public boolean update(double deltaTime, Scene scene) {
		return true;
	}
	
}
