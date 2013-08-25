package goldeneagle.entities;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.Display;

import goldeneagle.ResourceCache;
import goldeneagle.scene.Entity;
import goldeneagle.scene.Frame;
import goldeneagle.scene.Scene;
import goldeneagle.scene.SceneManager;

public class BackgroundImageEntity extends Entity {

	String path;
	public BackgroundImageEntity(Frame parent_, String p) {
		super(parent_);
		// TODO Auto-generated constructor stub
		this.path = p;
	}

	@Override
	protected boolean update(double deltaTime, Scene scene) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void draw() {
		int texID = -1;
		try {
			texID = ResourceCache.GetGLTexture(this.path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
 		glBindTexture(GL_TEXTURE_2D, texID);

		glEnable(GL_ALPHA_TEST);
		glEnable(GL_TEXTURE_2D);
		glAlphaFunc(GL_GREATER, 0.5f);
		
		glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, SceneManager.floatv(1f, 1f, 1f, 1f));
		
		glBegin(GL_POLYGON);
		
		glColor3d(1, 1, 1);
		glNormal3d(0, 0, 1);
		
		double ratio = Display.getWidth() / Display.getHeight();
		
		glTexCoord2d(0, 0);
		glVertex3d(-960, 540, 0);
		glTexCoord2d(1, 0);
		glVertex3d(960, 540, 0);
		glTexCoord2d(1, 1);
		glVertex3d(960, -540, 0);
		glTexCoord2d(0, 1);
		glVertex3d(-960, -540, 0);
		
		glEnd();
		glDisable(GL_ALPHA_TEST);
		glDisable(GL_TEXTURE_2D);
	}

}
