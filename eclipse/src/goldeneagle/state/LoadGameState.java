package goldeneagle.state;

import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_GREATER;
import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glAlphaFunc;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glNormal3d;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glVertex3d;
import goldeneagle.ResourceCache;
import goldeneagle.ResourceLoader;
import goldeneagle.util.Profiler;

// This GameState should load any assets required to display the intros
// and the menus which occur before we enter the game.

public class LoadGameState extends GameState {
	ResourceLoader rl;
	
	@Override
	protected void init() {
		rl = new ResourceLoader();
		rl.Add("./assets/tiles/atlas.jpg");
		rl.Add("./assets/entities/tree.png");
		rl.Add("./assets/entities/bush.png");
		rl.AddAnimation("character_walk", 21);
		rl.AddAnimation("character_idle", 78);
		rl.AddAnimation("character_rummage", 13);
		rl.AddAnimation("fire", 7);
		rl.Add("assets/fonts/arial.ttf");
		
		try {
			ResourceCache.AddShaderProgram("test");
			System.out.printf("ShaderProgram: %d\n", ResourceCache.GetProgram("test")); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void update(double deltaTime) {
		if(!rl.isStarted())
			rl.Start();
		
		if(rl.isComplete())
			this.nextState = new MainGameState();
	}

	@Override
	protected void draw() {
		glEnable(GL_ALPHA_TEST);
		glAlphaFunc(GL_GREATER, 0.5f);
		
		glBegin(GL_POLYGON);
		
		glColor3d(0, 0, 1);
		glNormal3d(0, 0, 1);
		
		System.out.printf("Progress: %f\n", rl.getProgress());
		
		glVertex3d(-1, -1, 0);
		glVertex3d(-1 + rl.getProgress() * 2, -1, 0);
		glVertex3d(-1 + rl.getProgress() * 2, 1, 0);
		glVertex3d(-1, 1, 0);
		
		glEnd();
		glDisable(GL_ALPHA_TEST);
	}

}
