package goldeneagle.entities;

import static org.lwjgl.opengl.GL11.*;
import goldeneagle.scene.Entity;
import goldeneagle.scene.Frame;
import goldeneagle.scene.Scene;

public class UI extends Entity {

	PlayerEntity pe;
	public UI(Frame parent_, PlayerEntity player) {
		
		super(parent_);
		this.pe = player;
		
		this.setHeight(5.1);
	}

	@Override
	protected boolean update(double deltaTime, Scene scene) {
		return true;
	}

	@Override
	protected void draw() {
		glEnable(GL_ALPHA_TEST);
		glAlphaFunc(GL_GREATER, 0.5f);
		
		glDisable(GL_LIGHTING);
		
		glBegin(GL_QUADS);
		
		glColor3d(0, 1, 0);
		glNormal3d(0, 0, 1);
		
		double thickness = 0.1;
		
		glVertex3d(-1, -thickness, 0);
		glVertex3d(-1 + ((pe.Nutrition / 200)*2), -thickness, 0);
		glVertex3d(-1 + ((pe.Nutrition / 200)*2), 0, 0);
		glVertex3d(-1, 0, 0);
		
		if(pe.Temp < 37)
			glColor3d(0, 0, 1);
		else
			glColor3d(1, 0, 0);
		
		glNormal3d(0, 0, 1);
		
		glVertex3d(-1, 0, 0);
		glVertex3d(0 + (pe.Temp-37), 0, 0);
		glVertex3d(0 + (pe.Temp-37), thickness, 0);
		glVertex3d(-1, thickness, 0);
		
		glColor3d(0.3, 0.3, 0.2);
		
		glNormal3d(0, 0, 1);
		
		glVertex3d(-1, thickness, 0);
		glVertex3d(-1 + (double)(pe.getNumSticks() / 10.0), thickness, 0);
		glVertex3d(-1 + (double)(pe.getNumSticks() / 10.0), thickness*2, 0);
		glVertex3d(-1, thickness*2, 0);
		
		glEnd();
		glDisable(GL_ALPHA_TEST);
		glEnable(GL_LIGHTING);
		
	}
	
}
