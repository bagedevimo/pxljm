package goldeneagle.scene;

import static org.lwjgl.opengl.GL11.*;

import goldeneagle.Frame;
import goldeneagle.ResourceCache;

public class ChunkEntity extends Entity {
	public final int xOrigin;
	public final int yOrigin;
	
	public ChunkEntity(Frame parent_, int baseX, int baseY) {
		super(parent_);
		
		this.xOrigin = baseX;
		this.yOrigin = baseY;
	}

	@Override
	public void Draw() {
		int texID = -1;
		try {
			texID = ResourceCache.GetGLTexture("./assets/test.png");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.printf("texID: %d\n", texID);
		
		glBindTexture(GL_TEXTURE_2D, texID);
		glEnable(GL_TEXTURE_2D);
		
		glBegin(GL_POLYGON);
	
//		glColor3d(R, G, B);
		glNormal3d(0, 0, 1);
		
		glTexCoord2d(0, 0);
		glVertex3d(0, -16, SceneManager.Z_TERRAIN);
		glTexCoord2d(1, 0);
		glVertex3d(16, 0, SceneManager.Z_TERRAIN);
		glTexCoord2d(0, 1);
		glVertex3d(0, 16, SceneManager.Z_TERRAIN);
		glTexCoord2d(1, 1);
		glVertex3d(-16, 0, SceneManager.Z_TERRAIN);
		
		glEnd();
		
		glDisable(GL_TEXTURE_2D);
	}

	@Override
	public void Update() {
	}

}
