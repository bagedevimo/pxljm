package goldeneagle.entities;

import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_AMBIENT_AND_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_GREATER;
import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glAlphaFunc;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glMaterial;
import static org.lwjgl.opengl.GL11.glNormal3d;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glVertex3d;

import org.lwjgl.opengl.Display;

import goldeneagle.ResourceCache;
import goldeneagle.scene.Entity;
import goldeneagle.scene.Frame;
import goldeneagle.scene.Scene;
import goldeneagle.scene.SceneManager;

public class ButtonEntity extends Entity {

	private int Width = 0, Height = 0;
	private String Normal, Hover;
	
	public ButtonEntity(Frame parent, int width, int height, String normal, String hover) {
		super(parent);
		
		this.Width = width;
		this.Height = height;
		this.Normal = normal;
		this.Hover = hover;
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
			texID = ResourceCache.GetGLTexture(this.Normal);
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
		
		glTexCoord2d(0, 0);
		glVertex3d(-Width, Height, 0);
		glTexCoord2d(1, 0);
		glVertex3d(Width, Height, 0);
		glTexCoord2d(1, 1);
		glVertex3d(Width, -Height, 0);
		glTexCoord2d(0, 1);
		glVertex3d(-Width, -Height, 0);
		
		glEnd();
		glDisable(GL_ALPHA_TEST);
		glDisable(GL_TEXTURE_2D);

	}
	
}
