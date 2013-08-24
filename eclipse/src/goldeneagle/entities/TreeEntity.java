package goldeneagle.entities;

import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glNormal3d;
import static org.lwjgl.opengl.GL11.glVertex3d;
import goldeneagle.Bound;
import goldeneagle.BoundingSphere;
import goldeneagle.Vec3;
import goldeneagle.scene.Entity;
import goldeneagle.scene.Frame;
import goldeneagle.scene.SceneManager;

public class TreeEntity extends Entity{
	private final Bound bound;
	
	public TreeEntity(Frame parent_, double xPos_, double yPos_, double radius) {
		super(parent_);
		this.setLinear(new Vec3(xPos_, yPos_, 0), Vec3.zero);
		bound = new BoundingSphere(parent_, radius);
	}

	@Override
	public void Draw() {
 		glBegin(GL_POLYGON);
	
		glBindTexture(GL_TEXTURE_2D, 0);
		glColor3d(0, 1, 0);
		glNormal3d(0, 0, 1);
		
		glVertex3d(-0.5, -0.5, SceneManager.Z_OBJECT);
		glVertex3d(-0.5, -0.5, SceneManager.Z_OBJECT);
		glVertex3d(-0.5, -0.5, SceneManager.Z_OBJECT);
		glVertex3d(-0.5, -0.5, SceneManager.Z_OBJECT);
		
		glEnd();
	}

	@Override
	public void Update(double deltaTime) {
		// TODO Auto-generated method stub
		
	}
}
