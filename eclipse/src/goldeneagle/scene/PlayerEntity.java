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
		Vec3 motion = new Vec3(0, 0, 0);
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
			motion = motion.add(new Vec3(-1, 0, 0));
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
			motion = motion.add(new Vec3(1, 0, 0));
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
			motion = motion.add(new Vec3(0, 1, 0));
		if(Keyboard.isKeyDown(Keyboard.KEY_S))
			motion = motion.add(new Vec3(0, -1, 0));
			
		this.setLinear(this.getPosition(), motion);
			
	}

}
