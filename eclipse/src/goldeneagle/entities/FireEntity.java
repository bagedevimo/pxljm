package goldeneagle.entities;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;

import goldeneagle.ResourceCache;
import goldeneagle.Vec3;
import goldeneagle.scene.Entity;
import goldeneagle.scene.Frame;
import goldeneagle.scene.Light;
import goldeneagle.scene.Scene;
import goldeneagle.scene.SceneManager;
import goldeneagle.util.Profiler;

public class FireEntity extends Entity {

	Animation fire;
	Light.SpotLight fireLight;
	
	public FireEntity(Frame parent_, Scene scene) {
		super(parent_);

		this.setHeight(0.1);

		fireLight = new Light.SpotLight(this, Color.YELLOW, 3, 4, Math.PI/4, 5);
		fireLight.setPitch(-(Math.PI/2));
		scene.addLight(fireLight);
		fire = new Animation(this, "fire", 7);
		System.out.println("CREATED FIRE");
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
			texID = ResourceCache.GetGLTexture(fire.getFrameTexture());
		} catch (Exception e) {
			e.printStackTrace();
		}

		glBindTexture(GL_TEXTURE_2D, texID);
		glEnable(GL_TEXTURE_2D);

		glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE,
				SceneManager.floatv(1f, 1f, 1f, 1f));

		glEnable(GL_ALPHA_TEST);
		glAlphaFunc(GL_GREATER, 0.5f);

		glBegin(GL_POLYGON);

		glColor3d(1, 1, 1);
		glNormal3d(0, 0, 1);

		double size = 1.0f;
		glTexCoord2d(0, 0);
		glVertex3d(-size, -size, 0);
		glTexCoord2d(1, 0);
		glVertex3d(size, -size, 0);
		glTexCoord2d(1, 1);
		glVertex3d(size, size, 0);
		glTexCoord2d(0, 1);
		glVertex3d(-size, size, 0);

		glEnd();

		glDisable(GL_ALPHA_TEST);
		glDisable(GL_TEXTURE_2D);

	}

}
