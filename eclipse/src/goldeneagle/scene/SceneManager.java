package goldeneagle.scene;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.util.Iterator;

import org.lwjgl.opengl.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import goldeneagle.*;

public class SceneManager {
	public static final double Z_TERRAIN = 0.0;
	public static final double Z_OBJECT = 0.3;
	public static final double Z_PLAYER = 0.5;
	public static final double Z_ROOF = 1.0;
	
	public static final PixelFormat PIXEL_FORMAT = new PixelFormat(8, 24, 8, 4);
	public static final ContextAttribs CONTEXT_ATTRIBS = new ContextAttribs(2, 1);
	
	private static boolean is_inited = false;
	
	private static void multMatrix(Mat4 m) {
		// assemble column-major
		ByteBuffer buf0 = ByteBuffer.allocateDirect(16 * 8);
		buf0.order(ByteOrder.nativeOrder());
		DoubleBuffer buf = buf0.asDoubleBuffer();
		buf.position(0);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				double d = m.get(j, i);
				buf.put(d);
			}
		}
		buf.position(0);
		glMultMatrix(buf);
	}
	
	public static void init() {
		if (is_inited) return;
		is_inited = true;
		
	}
	
	public static void doFrame(Scene s, Camera c) {
		init();
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		double ratio = Display.getWidth() / (double) Display.getHeight();
		if (ratio >= 1) {
			double right = c.getRadius();
			glOrtho(-right, right, -right / ratio, right / ratio, -10, 10);
		} else {
			double top = c.getRadius();
			glOrtho(-top * ratio, top * ratio, -top, top, -10, 10);
		}
		
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		multMatrix(c.getViewTransform());
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		glEnable(GL_DEPTH_TEST);
		for(Entity e : s) {
			glPushMatrix();
			multMatrix(e.getTransformToRoot());
			e.Draw();
			glPopMatrix();
		}
		glDisable(GL_DEPTH_TEST);
		
		glFinish();
		
	}
	
}
