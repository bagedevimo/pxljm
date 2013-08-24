package goldeneagle.scene;

import java.awt.Color;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import goldeneagle.*;

@SuppressWarnings("unused")
public class SceneManager {
	
	public static final PixelFormat PIXEL_FORMAT = new PixelFormat(8, 24, 8, 4);
	public static final ContextAttribs CONTEXT_ATTRIBS = new ContextAttribs(2, 1);
	
	private static boolean is_inited = false;
	
	private static List<Entity> entities_draw = new ArrayList<Entity>();
	private static List<Entity> entities_shadow = new ArrayList<Entity>();
	
	private static ByteBuffer buftemp;
	
	public static void multMatrix(Mat4 m) {
		// assemble column-major
		DoubleBuffer buf = buftemp.asDoubleBuffer();
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
	
	public static DoubleBuffer doublev(double... ds) {
		DoubleBuffer buf = buftemp.asDoubleBuffer();
		buf.position(0);
		for (double d : ds) {
			buf.put(d);
		}
		buf.position(0);
		return buf;
	}
	
	public static FloatBuffer floatv(double... ds) {
		float[] fs = new float[ds.length];
		for (int i = 0; i < ds.length; i++) {
			fs[i] = (float) ds[i];
		}
		return floatv(fs);
	}
	
	public static FloatBuffer floatv(float... ds) {
		FloatBuffer buf = buftemp.asFloatBuffer();
		buf.position(0);
		for (float d : ds) {
			buf.put(d);
		}
		buf.position(0);
		return buf;
	}
	
	public static IntBuffer intv(int... ds) {
		IntBuffer buf = buftemp.asIntBuffer();
		buf.position(0);
		for(int d : ds)
			buf.put(d);
		buf.position(0);
		return buf;		
	}
	
	public static FloatBuffer floatv(Vec3 v) {
		return floatv((float) v.x, (float) v.y, (float) v.z);
	}
	
	public static FloatBuffer floatv(Vec4 v) {
		return floatv((float) v.x, (float) v.y, (float) v.z, (float) v.w);
	}
	
	public static FloatBuffer floatv(Color c) {
		final float i255 = 1f / 255;
		return floatv(c.getRed() * i255, c.getGreen() * i255, c.getBlue() * i255, c.getAlpha() * i255);
	}
	
	public static void init() {
		if (is_inited) return;
		is_inited = true;
		buftemp = ByteBuffer.allocateDirect(4096);
		buftemp.order(ByteOrder.nativeOrder());
	}
	
	public static void doFrame(Scene s, Camera c) {
		init();
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		double ratio = Display.getWidth() / (double) Display.getHeight();
		if (ratio >= 1) {
			double right = c.getRadius();
			glOrtho(-right, right, -right / ratio, right / ratio, -100, 1);
		} else {
			double top = c.getRadius();
			glOrtho(-top * ratio, top * ratio, -top, top, -100, 1);
		}
		
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		multMatrix(c.getViewTransform());
		
		// clear the buffers
		glDepthMask(true);
		glColorMask(true, true, true, true);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glShadeModel(GL_SMOOTH);
		
		// get entities to draw and shadow casters
		entities_draw.clear();
		s.getEntities(entities_draw, new BoundingSphere(c, c.getRadius()));
		entities_shadow.clear();
		s.getEntities(entities_shadow, new BoundingSphere(c, 2 * c.getRadius()));
		
		// write entire scene to z-buffer only, with ambient
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);
		glDisable(GL_TEXTURE_2D);
	    glDisable(GL_BLEND);
	    glEnable(GL_LIGHTING);
	    glDisable(GL_LIGHT0);
	    glDepthMask(true);
	    glColorMask(true, true, true, true);
	    glLightModel(GL_LIGHT_MODEL_AMBIENT, floatv(s.getAmbient()));
	    draw();
		
		// blend lights
		glBlendFunc(GL_ONE, GL_ONE);
		glDepthMask(false);
		glLightModel(GL_LIGHT_MODEL_AMBIENT, floatv(0f, 0f, 0f, 1f));
		
		for (Light l : s.getLights()) {
			// skip light if out of range
			if (l.getGlobalPosition().dist(c.getGlobalPosition()) > 2 * c.getRadius()) {
				continue;
			}
			l.load(0);
			
			// stencil stuff here
			
			glEnable(GL_BLEND);
			glDepthFunc(GL_LEQUAL);
			glColorMask(true, true, true, true);
			glEnable(GL_LIGHTING);
			glEnable(GL_LIGHT0);
			draw();
			glDisable(GL_LIGHTING);
			
		}
		
		glFinish();
		
	}
	
	private static void draw() {
		for(Entity e : entities_draw) {
			glPushMatrix();
			multMatrix(e.getTransformToRoot());
			e.doDraw();
			glPopMatrix();
		}
	}
	
}
