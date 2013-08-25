package goldeneagle.entities;

import static org.lwjgl.opengl.GL11.*;
import goldeneagle.BoundingSphere;
import goldeneagle.scene.Frame;
import goldeneagle.scene.Scene;
import goldeneagle.scene.ShadowCaster;
import goldeneagle.Bound;
import goldeneagle.ResourceCache;
import goldeneagle.Vec3;
import goldeneagle.scene.Entity;
import goldeneagle.state.Collidable;
import goldeneagle.util.Profiler;

public class PlantEntity extends Entity implements Collidable {
	private final double radius;
	
	static final int PlantEntity = Profiler.createSection("PlantEntity");
	
	public PlantEntity(Frame parent_, double xPos_, double yPos_, double radius) {
		super(parent_);
		this.setLinear(new Vec3(xPos_, yPos_, 0), Vec3.zero);
		this.radius = radius;
		setBound(new BoundingSphere(this, radius));
		setHeight(0.5);
		
		ShadowCaster sc = new ShadowCaster();
		sc.addVertex(new Vec3(-0.5, -0.5, 0));
		sc.addVertex(new Vec3(0.5, -0.5, 0));
		sc.addVertex(new Vec3(0.5, 0.5, 0));
		sc.addVertex(new Vec3(-0.5, 0.5, 0));
		addShadowCaster(sc);
	}

	@Override
	public void draw() {
		int texID = -1;
		try {
			texID = ResourceCache.GetGLTexture("./assets/entities/bush.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
	
 		glBindTexture(GL_TEXTURE_2D, texID);
		glEnable(GL_TEXTURE_2D);
		
		glEnable(GL_ALPHA_TEST);
		glAlphaFunc(GL_GREATER, 0.5f);
		
		Profiler.enter(PlantEntity);
		
		glBegin(GL_POLYGON);
		
		glColor3d(1, 1, 1);
		glNormal3d(0, 0, 1);
		
		glTexCoord2d(0, 0);
		glVertex3d(-this.radius, -this.radius, 0);
		glTexCoord2d(1, 0);
		glVertex3d(this.radius, -this.radius, 0);
		glTexCoord2d(1, 1);
		glVertex3d(this.radius, this.radius, 0);
		glTexCoord2d(0, 1);
		glVertex3d(-this.radius, this.radius, 0);
		
		glEnd();	
		
		Profiler.exit(PlantEntity);
		
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_ALPHA_TEST);
	}

	@Override
	public boolean update(double deltaTime, Scene scene) {
		return true;
	}

	@Override
	public Bound getCollisionBound() {
		return new BoundingSphere(this, this.radius/10);
	}

}
