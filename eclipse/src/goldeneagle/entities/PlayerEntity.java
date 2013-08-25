package goldeneagle.entities;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.input.Keyboard;

import goldeneagle.AudioEngine;
import goldeneagle.Bound;
import goldeneagle.BoundingSphere;
import goldeneagle.ResourceCache;
import goldeneagle.Vec3;
import goldeneagle.clock.DerivedClock;
import goldeneagle.items.Item;
import goldeneagle.scene.Entity;
import goldeneagle.scene.Frame;
import goldeneagle.scene.Scene;
import goldeneagle.scene.SceneManager;
import goldeneagle.state.Collidable;
import goldeneagle.util.Profiler;

public class PlayerEntity extends Entity implements Collidable {
	private static final int PlayerEntity_glBegin = Profiler.createSection("PlayerEntity_glBegin");
	
	Animation walkingAnimation;
	Animation idleAnimation;
	Animation rummagingAnimation;
	
	double lastAttrUpdate;
	
	boolean isMoving = false;
	boolean isRunning = false;
	boolean isRummaging = false;

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
	
	private int inventoryIndex = -1;
	private final Map<Item, Integer> inventory = new HashMap<Item, Integer>();
	
	double lastStep;
	double stepInterval;
	
	public PlayerEntity(Frame parent_) {
		super(parent_);
		
		idleAnimation = new Animation(this, "character_idle", 78);
		walkingAnimation = new Animation(this, "character_walk", 21);
		rummagingAnimation = new Animation(this, "character_rummage", 13, 24);
		
		setHeight(2);
	
		stepInterval = 0.8;
		
		this.lastAttrUpdate = this.getClock().get();
		
	}

	@Override
	public void draw() {				
		
		Animation currentAnimation = (this.isMoving ? walkingAnimation : idleAnimation);
		if(this.isRummaging)
			currentAnimation = rummagingAnimation;
		
//		if((this.animationClock.get() - this.lastStep) > this.stepInterval && this.isMoving) {
//			this.lastStep = animationClock.get();
//			AudioEngine.PlayEffect("step", this.getGlobalPosition());
//			System.out.println("step");
//		}
			
		int texID = -1;
		try {
			texID = ResourceCache.GetGLTexture(currentAnimation.getFrameTexture());
		} catch (Exception e) {
			e.printStackTrace();
		}
	
 		glBindTexture(GL_TEXTURE_2D, texID);
		glEnable(GL_TEXTURE_2D);
		
		
		glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE,
				SceneManager.floatv(1f, 0f, 0f, 1f));
		
		glEnable(GL_ALPHA_TEST);
		glAlphaFunc(GL_GREATER, 0.5f);
		
		
		Profiler.enter(PlayerEntity_glBegin);		
		glBegin(GL_POLYGON);
		
		glColor3d(1, 1, 1);
		glNormal3d(0, 0, 1);
		
		double size = 1.8f;
		glTexCoord2d(0, 1);
		glVertex3d(-size, -size, 0);
		glTexCoord2d(1, 1);
		glVertex3d(size, -size, 0);
		glTexCoord2d(1, 0);
		glVertex3d(size, size, 0);
		glTexCoord2d(0, 0);
		glVertex3d(-size, size, 0);
		
		glEnd();
		Profiler.exit(PlayerEntity_glBegin);
		
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_ALPHA_TEST);
	}

	@Override
	public boolean update(double deltaTime, Scene scene) {
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

		Vec3 oldPosition = this.getPosition();
		motion = this.getPosition().add(new Vec3(x, y, 0));

		if (x == 0 && y == 0) {
			this.walkingAnimation.pause();
			this.idleAnimation.play();
			this.isMoving = false;
		} else {
			this.walkingAnimation.play();
			this.idleAnimation.pause();
			this.isMoving = true;
		}
			
		this.setLinear(motion, Vec3.zero);
		
		List<Entity> ents = new ArrayList<Entity>();
		scene.getEntities(ents, new BoundingSphere(this, 3));
		for(Entity e : ents) {
			if(e.equals(this)) continue;
			if(e instanceof Collidable) {
				
				Collidable en = (Collidable)e;
				
				if(this.getCollisionBound().intersects(en.getCollisionBound())) {
					System.out.printf("Colliding with a %s\n", en.toString());	
					this.setLinear(oldPosition, Vec3.zero);
					if(en instanceof PlantEntity && Keyboard.isKeyDown(Keyboard.KEY_W))
						this.isRummaging = true;
					else
						this.isRummaging = false;
				} else
					this.isRummaging = false;
			}
		}
		
		if(this.getClock().get() - this.lastAttrUpdate > 1)
			this.updateAttrs();
		
		return true;
	}
	
	public int getCurrentChunkX() {
		return (int) (this.getPosition().x / 32);
	}
	
	public int getCurrentChunkY() {
		return (int) (this.getPosition().y / 32);
	}
	
	private void updateAttrs() {
		this.lastAttrUpdate = this.getClock().get();
		
		this.Nutrition -= this.Hunger;
		this.Hydration -= this.Thirst;
		
		if(this.isRunning && this.Thirst < 0.03f)
			this.Thirst += 0.0001;
		else if(this.Thirst > this.DefaultThirst)
			this.Thirst -= 0.0001;
		
//		System.out.printf("Energy: %f\tdEnergy: %f\n", this.Energy, 0f);
//		System.out.printf("Nutrition: %f\tdNutrition: %f\n", this.Nutrition, this.Hunger);
//		System.out.printf("Hydration: %f\tddHydration: %f\n", this.Hydration, this.Thirst);
//		System.out.printf("Health: %f\tdHealth: %f\n",  this.Health, 0f);
//		System.out.printf("Temperature: %f\tdTemperature: %f\n", this.Temp, 0f);
	}

	@Override
	public Bound getCollisionBound() {
		return new BoundingSphere(this, 1f);
	}

	public void addItem(Item item) {
		if(inventory.containsKey(item)){
			inventory.put(item, inventory.get(item)+1);
		} else {
			inventory.put(item, 1);
		}
		if(inventoryIndex < 0)
			inventoryIndex++;
	}
	
	public void useItem(Scene scene) {
		Item item = getItemAt(inventoryIndex);
		if(inventory.get(item)==1)
			inventory.remove(item);
		item.use(this, scene);
	}
	
	public Item getItemAt(int index){
		int i = 0;
		for(Entry<Item, Integer> e : inventory.entrySet()){
			if(index == i)
				return e.getKey();
			i++;
		}
		return null;
	}
	
	public void modifyEnergy(double amount){
		this.Energy += amount;
	}
	
	public void modifyHealth(double amount){
		this.Health += amount;
	}
	
	public void modifyNutrition(double amount){
		this.Nutrition += amount;
	}
	
	public void modifyHydration(double amount){
		this.Hydration += amount;
	}
	
	public void modifyTemperature(double amount){
		this.Temp += amount;
	}
}
