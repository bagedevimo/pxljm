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

import java.util.ArrayList;
import java.util.List;

import goldeneagle.Bound;
import goldeneagle.BoundingSphere;
import goldeneagle.ResourceCache;
import goldeneagle.Vec3;
import goldeneagle.items.Item;
import goldeneagle.scene.Frame;
import goldeneagle.scene.Entity;
import goldeneagle.scene.Scene;
import goldeneagle.scene.SceneManager;
import goldeneagle.scene.ShadowCaster;
import goldeneagle.state.Collidable;
import goldeneagle.util.Profiler;

public class PickupEntity extends Entity implements Collidable{

	private static final double size = 1.4f;
	private final Item item;
	private Bound bound; 
	
	public PickupEntity(Frame parent_, double xPos_, double yPos_, Item item_) {
		super(parent_);
		item = item_;
		bound = new BoundingSphere(this, 0.001);
		
		this.setLinear(new Vec3(xPos_, yPos_, 0), Vec3.zero);
		this.setHeight(0.3);
		
	}
	
	protected boolean update(double deltaTime, Scene scene) {
		List<Entity> collided = new ArrayList<Entity>();
		scene.getEntities(collided, bound);
		for(Entity e : collided){
			if(e instanceof PlayerEntity){
				PlayerEntity p = (PlayerEntity)e;
				if(p.isRummaging){
					p.addItem(item);
					System.out.println("Player Picked Up item");
					return false;
				}
			}
		}
		return true;
	}

	@Override
	protected void draw() {
		int texID = -1;
		try {
			texID = ResourceCache.GetGLTexture(item.getTexturePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		glBindTexture(GL_TEXTURE_2D, texID);
		glEnable(GL_TEXTURE_2D);
		
		glEnable(GL_ALPHA_TEST);
		glAlphaFunc(GL_GREATER, 0.5f);

		glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, SceneManager.floatv(1, 1, 1, 1));
		
		glBegin(GL_POLYGON);
		glNormal3d(0, 0, 1);
		glTexCoord2d(0, 0);
		glVertex3d(-this.size, -this.size, 0);
		glTexCoord2d(1, 0);
		glVertex3d(this.size, -this.size, 0);
		glTexCoord2d(1, 1);
		glVertex3d(this.size, this.size, 0);
		glTexCoord2d(0, 1);
		glVertex3d(-this.size, this.size, 0);
		
		glEnd();	
		
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_ALPHA_TEST);
	}

	@Override
	public Bound getCollisionBound() {
		return new BoundingSphere(this, 0.01);
	}

}
