package goldeneagle.scene;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GLContext;

import goldeneagle.Frame;
import goldeneagle.ResourceCache;

public class ChunkEntity extends Entity {
	public final int xOrigin;
	public final int yOrigin;
	
	private final double R;
	private final double G;
	private final double B;

	public ChunkEntity(Frame parent_, int baseX, int baseY) {
		super(parent_);
		
		this.xOrigin = baseX;
		this.yOrigin = baseY;
		
		this.R = Math.random();
		this.G = Math.random();
		this.B = Math.random();
	}

	@Override
	public void Draw() {
		glEnable(GL_TEXTURE_2D);
		
		glBegin(GL_POLYGON);
		int texID = -1;
		try {
			texID = ResourceCache.GetGLTexture("./assets/test.png");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.printf("texID: %d\n", texID);
		
		glBindTexture(GL_TEXTURE_2D, texID);
	
//		glColor3d(R, G, B);
		glNormal3d(0, 0, 1);
		
		glTexCoord2d(0, 0);
		glVertex3d(0, -1, -1);
		glTexCoord2d(1, 0);
		glVertex3d(1, 0, -1);
		glTexCoord2d(0, 1);
		glVertex3d(0, 1, -1);
		glTexCoord2d(1, 1);
		glVertex3d(-1, 0, -1);
		
		glEnd();
		
		glDisable(GL_TEXTURE_2D);
	}

}
