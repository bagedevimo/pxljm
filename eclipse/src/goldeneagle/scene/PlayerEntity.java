package goldeneagle.scene;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;

import goldeneagle.Frame;
import goldeneagle.ResourceCache;
import goldeneagle.Vec3;

public class PlayerEntity extends Entity {
	
	public PlayerEntity(Frame parent_) {
		super(parent_);
	}

	@Override
	public void Draw() {				
		glBegin(GL_POLYGON);
	
		glBindTexture(GL_TEXTURE_2D, 0);
		glColor3d(1, 0, 0);
		glNormal3d(0, 0, 1);
		
		glVertex3d(0, 0, SceneManager.Z_PLAYER);
		glVertex3d(1, 0, SceneManager.Z_PLAYER);
		glVertex3d(1, 1, SceneManager.Z_PLAYER);
		glVertex3d(0, 1, SceneManager.Z_PLAYER);
		
		glEnd();
		
		glDisable(GL_TEXTURE_2D);
	}
	
	@Override
	public void Update() {
	}

}
