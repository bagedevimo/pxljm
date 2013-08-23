package goldeneagle.scene;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
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
	
	public static final PixelFormat PIXEL_FORMAT = new PixelFormat(8, 24, 8, 16);
	public static final ContextAttribs CONTEXT_ATTRIBS = new ContextAttribs(2, 1);
	
	private static boolean is_inited = false;
	
	private static void multMatrix(Mat4 m) {
		// assemble column-major
		DoubleBuffer buf = ByteBuffer.allocate(16 * 8).asDoubleBuffer();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				buf.put(m.get(j, i));
			}
		}
		glMultMatrix(buf);
	}
	
	public static void init() {
		if (is_inited) return;
		is_inited = true;
		
	}
	
	public static void doFrame(Scene s, Camera c) {
		init();
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		glBegin(GL_POLYGON);
		glColor3d(1, 0, 0);
		glNormal3d(0, 0, 1);
		glVertex3d(0, -1, -1);
		glVertex3d(1, 0, -1);
		glVertex3d(0, 1, -1);
		glVertex3d(-1, 0, -1);
		glEnd();
		
		glFinish();
		
	}
	
}
