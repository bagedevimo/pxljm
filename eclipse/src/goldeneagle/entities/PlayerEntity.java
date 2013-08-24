package goldeneagle.entities;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;

import goldeneagle.ResourceCache;
import goldeneagle.Vec3;
import goldeneagle.clock.DerivedClock;
import goldeneagle.scene.Entity;
import goldeneagle.scene.Frame;
import goldeneagle.scene.SceneManager;
import goldeneagle.util.Profiler;

public class PlayerEntity extends Entity {
	private static final int PlayerDraw = Profiler.createSection("PlayerDraw");
	
	

	int nFrames = 21;
	double frameWidth = 1.0f/nFrames;

	DerivedClock animationClock;
	double animationStart;
	double walkFrameTime = 1/20f;
	double runFrameTime = 1/30f;
	
	double lastAttrUpdate;
	
	boolean isMoving = false;
	boolean isRunning = false;

	double MaxHealth = 100;
	double Health = MaxHealth;
	
	double MaxNutrition = 100;
	double Nutrition = MaxNutrition;
	double DefaultHunger = 0.01;
	double Hunger = DefaultHunger;
	
	double MaxHydration = 100;
	double Hydration = MaxHydration;
	double DefaultThirst = 0.02;
	double Thirst = DefaultThirst;
	
	double MaxEnergy = 100;
	double Energy = MaxEnergy;
	
	double NormalTemp = 37.0f;
	double Temp = NormalTemp;
	
	public PlayerEntity(Frame parent_) {
		super(parent_);
		
		setHeight(2);
		
		animationClock = new DerivedClock(this.getClock(), 0);
		animationStart = this.animationClock.get();
		
		this.lastAttrUpdate = this.getClock().get();
	}
	
	private double getFrameTime() {
		if(this.isRunning)
			return runFrameTime;
		return walkFrameTime;
	}

	@Override
	public void draw() {				
		int texID = -1;
		
		double dT = (this.animationClock.get() - this.animationStart);
		int frame = (int)(dT / getFrameTime());
		if(frame >= nFrames) {
			this.animationStart = this.animationClock.get();
			dT = (this.animationClock.get() - this.animationStart);
			frame = (int)(dT / getFrameTime());
		}		
		
		try {
			texID = ResourceCache.GetGLTexture("./assets/sprites/character_walk.png");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
 		glBindTexture(GL_TEXTURE_2D, texID);
		glEnable(GL_TEXTURE_2D);
		
		
		glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE,
				SceneManager.floatv(1f, 0f, 0f, 1f));
		
		glEnable(GL_ALPHA_TEST);
		glAlphaFunc(GL_GREATER, 0.5f);
		
		Profiler.enter(PlayerDraw);
		
		glBegin(GL_POLYGON);
		
		glColor3d(1, 1, 1);
		glNormal3d(0, 0, 1);
		
		double u = frameWidth*frame;
		
		double size = 1.8f;
		glTexCoord2d(u, 0);
		glVertex3d(-size, -size, 0);
		glTexCoord2d(u+frameWidth, 0);
		glVertex3d(size, -size, 0);
		glTexCoord2d(u+frameWidth, 1);
		glVertex3d(size, size, 0);
		glTexCoord2d(u, 1);
		glVertex3d(-size, size, 0);
		
		glEnd();
		
		Profiler.exit(PlayerDraw);
		
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_ALPHA_TEST);
	}

	@Override
	public void update(double deltaTime) {
		Vec3 motion = new Vec3(0, 0, 0);
		double rot = 0;
		double rotSpeed = 2.5 * deltaTime;

		if (Keyboard.isKeyDown(Keyboard.KEY_A))
			rot = rotSpeed;
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
			rot = -rotSpeed;

		this.setAngular(rot + this.getRotation(), 0);

		double speed = 3.5 * deltaTime;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
		{
			isRunning = true;
			speed *= 2;
		} else
			isRunning = false;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
			speed *= 10;
		
		double x = 0, y = 0;
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			x = (Math.sin(this.getRotation()) * -speed);
			y = -(Math.cos(this.getRotation()) * -speed);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			x = Math.sin(this.getRotation()) * speed;
			y = -(Math.cos(this.getRotation()) * speed);
		}

		motion = this.getPosition().add(new Vec3(x, y, 0));

		if(x == 0 && y == 0) {
			this.animationClock.pause();
			this.isMoving = false;
		}
		else {
			this.animationClock.play();
			this.isMoving = true;
		}
			
		
		this.setLinear(motion, Vec3.zero);
		
		if(this.getClock().get() - this.lastAttrUpdate > 1)
			this.updateAttrs();
	}
	
	private void updateAttrs() {
		this.lastAttrUpdate = this.getClock().get();
		
		this.Nutrition -= this.Hunger;
		this.Hydration -= this.Thirst;
		
		if(this.isRunning && this.Thirst < 0.03f)
			this.Thirst += 0.0001;
		else if(this.Thirst > this.DefaultThirst)
			this.Thirst -= 0.0001;
		
		System.out.printf("Energy: %f\tdEnergy: %f\n", this.Energy, 0f);
		System.out.printf("Nutrition: %f\tdNutrition: %f\n", this.Nutrition, this.Hunger);
		System.out.printf("Hydration: %f\tddHydration: %f\n", this.Hydration, this.Thirst);
		System.out.printf("Health: %f\tdHealth: %f\n",  this.Health, 0f);
		System.out.printf("Temperature: %f\tdTemperature: %f\n", this.Temp, 0f);
	}

}
