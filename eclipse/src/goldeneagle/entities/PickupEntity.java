package goldeneagle.entities;

import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
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
import static org.lwjgl.opengl.GL11.glNormal3d;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glVertex3d;

import java.awt.TexturePaint;

import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.TextureLoader;

import map.Segment;
import goldeneagle.Bound;
import goldeneagle.BoundingSphere;
import goldeneagle.scene.Frame;
import goldeneagle.ResourceCache;
import goldeneagle.Vec3;
import goldeneagle.items.Item;
import goldeneagle.scene.Entity;
import goldeneagle.scene.SceneManager;
import goldeneagle.util.Profiler;

public class PickupEntity extends Entity {

	private final Item item;
	private Bound bound;
	
	public PickupEntity(Frame parent_, double xPos_, double yPos_, Item item_) {
		super(parent_);
		this.setLinear(new Vec3(xPos_, yPos_, 0), Vec3.zero);
		item = item_;
		bound = new BoundingSphere(parent_, Segment.size*2);
	}

	@Override
	public void update(double deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
//		int texID = -1;
//		try {
//			texID = ResourceCache.GetGLTexture("./assets/entities/bush.png");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	
// 		glBindTexture(GL_TEXTURE_2D, texID);
//		glEnable(GL_TEXTURE_2D);
//		
//		glEnable(GL_ALPHA_TEST);
//		glAlphaFunc(GL_GREATER, 0.5f);
//		
//		Profiler.enter(PlantEntity);
//		
//		glBegin(GL_POLYGON);
//		
//		glColor3d(1, 1, 1);
//		glNormal3d(0, 0, 1);
//		
//		glTexCoord2d(0, 0);
//		glVertex3d(, -this.radius, SceneManager.Z_OBJECT);
//		glTexCoord2d(1, 0);
//		glVertex3d(this.radius, -this.radius, SceneManager.Z_OBJECT);
//		glTexCoord2d(1, 1);
//		glVertex3d(this.radius, this.radius, SceneManager.Z_OBJECT);
//		glTexCoord2d(0, 1);
//		glVertex3d(-this.radius, this.radius, SceneManager.Z_OBJECT);
//		
//		glEnd();	
//		
//		Profiler.exit(PlantEntity);
//		
//		glDisable(GL_TEXTURE_2D);
//		glDisable(GL_ALPHA_TEST);
	}

}
