package goldeneagle;

import goldeneagle.scene.Entity;
import goldeneagle.scene.Frame;
import goldeneagle.scene.Scene;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

public class ParticleEmitter extends Entity {
	
	List<Particle> particles;
	Queue<Particle> removeList;
	
	double emitRate = 1.5f;
	double emitStart;
	
	Random rand;

	public ParticleEmitter(Frame parent_) {
		super(parent_);
		this.setHeight(1);
		
		rand = new Random();
		
		emitStart = this.getClock().get();
		
		removeList = new LinkedList<Particle>();
		particles = new ArrayList<Particle>();
	}

	@Override
	protected boolean update(double deltaTime, Scene scene) {
		
		if(this.getClock().get() - emitStart > (1f / emitRate)) {
			emitStart = this.getClock().get();
			Particle p = new Particle(0, 0, 500);
			p.dX = 0.0;
			p.dY = 0.0;
			particles.add(p);
		}
		
		
		for(Particle particle : particles) {
			particle.X += particle.dX;
			particle.Y += particle.dY;
			particle.dX += (rand.nextDouble() * 0.001) - 0.0005f;
			particle.dY += (rand.nextDouble() * 0.001) - 0.0005f;
			
			particle.Life--;
			if(particle.Life < 0)
				removeList.add(particle);
		}
		
		while(!removeList.isEmpty())
			particles.remove(removeList.remove());
		
		return true;
	}

	@Override
	protected void draw() {
		glPointSize(1.0f);
		
		glBegin(GL_POINTS);
		for(Particle p : particles) {
			glColor4d(1, 0, 0, p.trans());
			glVertex3d(p.X, p.Y, 0);
		}
		glEnd();
	}

}
