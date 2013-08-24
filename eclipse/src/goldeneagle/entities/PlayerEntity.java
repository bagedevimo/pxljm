package goldeneagle.entities;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;

import goldeneagle.Frame;
import goldeneagle.ResourceCache;
import goldeneagle.Vec3;
import goldeneagle.scene.Entity;
import goldeneagle.scene.SceneManager;

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
		
		glVertex3d(-0.5, -0.5, SceneManager.Z_PLAYER);
		glVertex3d(0.5, -0.5, SceneManager.Z_PLAYER);
		glVertex3d(0.5, 0.5, SceneManager.Z_PLAYER);
		glVertex3d(-0.5, 0.5, SceneManager.Z_PLAYER);
		
		glEnd();
		
		glDisable(GL_TEXTURE_2D);
	}
	
	@Override
	public void Update(double deltaTime) {
		Vec3 motion = new Vec3(0, 0, 0);
		double rot = 0;
		double rotSpeed = 2.5 * deltaTime;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
			rot = rotSpeed;
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
			rot = -rotSpeed;
		
		this.setAngular(rot + this.getRotation(), 0);
		
		double speed = 3.5 * deltaTime;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
			speed *= 8;
		
		double x = 0, y = 0;
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			x = (Math.sin(this.getRotation()) * -speed);
			y = -(Math.cos(this.getRotation()) * -speed);
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			x = Math.sin(this.getRotation()) * speed;
			y = -(Math.cos(this.getRotation()) * speed);
		}
			
		
		motion = this.getPosition().add(new Vec3(x, y, 0));
		
		this.setLinear(motion, Vec3.zero);
			
	}

}
