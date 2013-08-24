package goldeneagle.entities;

import static org.lwjgl.opengl.GL11.*;
import goldeneagle.Bound;
import goldeneagle.BoundingSphere;
import goldeneagle.Frame;
import goldeneagle.ResourceCache;
import goldeneagle.Vec3;
import goldeneagle.scene.Entity;
import goldeneagle.scene.SceneManager;

public class TreeEntity extends Entity{
	private final Bound bound;
	private final double radius;
	
	public TreeEntity(Frame parent_, double xPos_, double yPos_, double radius) {
		super(parent_);
		this.setLinear(new Vec3(xPos_, yPos_, 0), Vec3.zero);
		this.radius = radius;
		bound = new BoundingSphere(parent_, radius);
	}

	@Override
	public void Draw() {
		int texID = -1;
		try {
			texID = ResourceCache.GetGLTexture("./assets/entities/tree.png");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
 		glBindTexture(GL_TEXTURE_2D, texID);
		glEnable(GL_TEXTURE_2D);
		
		glEnable(GL_ALPHA_TEST);
		glAlphaFunc(GL_GREATER, 0.5f);
		
		glBegin(GL_POLYGON);
		
		glColor3d(1, 1, 1);
		glNormal3d(0, 0, 1);
		
		glTexCoord2d(0, 0);
		glVertex3d(-this.radius, -this.radius, SceneManager.Z_ROOF);
		glTexCoord2d(1, 0);
		glVertex3d(this.radius, -this.radius, SceneManager.Z_ROOF);
		glTexCoord2d(1, 1);
		glVertex3d(this.radius, this.radius, SceneManager.Z_ROOF);
		glTexCoord2d(0, 1);
		glVertex3d(-this.radius, this.radius, SceneManager.Z_ROOF);
		
		glEnd();	
		
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_ALPHA_TEST);
	}

	@Override
	public void Update(double deltaTime) {
		// TODO Auto-generated method stub
		
	}
}