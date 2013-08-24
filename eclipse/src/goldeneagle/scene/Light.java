package goldeneagle.scene;

import java.awt.Color;

import goldeneagle.Vec3;
import goldeneagle.Vec4;

import static org.lwjgl.opengl.GL11.*;

public class Light extends BoundFrame {

	protected Vec4 lightpos = Vec4.zero;
	protected Vec4 spotdir = Vec4.zero;
	protected Color color_d = Color.WHITE, color_s = Color.WHITE, color_a = Color.BLACK;
	protected float spot_cutoff = (float) Math.PI;
	protected float spot_exp = 0f;
	protected float atten_const = 1f;
	protected float atten_lin = 0f;
	protected float atten_quad = 0f;
	
	public Light(Frame parent_) {
		super(parent_);
	}
	
	public void load(int id) {
		// load modelview sensitive params
		glPushMatrix();
		//SceneManager.multMatrix(getTransformToRoot());
		int light = GL_LIGHT0 + id;
		glLight(light, GL_POSITION, SceneManager.floatv(0f, 0f, 2f, 1f));
		glLight(light, GL_SPOT_DIRECTION, SceneManager.floatv(spotdir));
		glPopMatrix();
		// load other params
		glLightf(light, GL_SPOT_CUTOFF, spot_cutoff);
		glLightf(light, GL_SPOT_EXPONENT, spot_exp);
		glLight(light, GL_DIFFUSE, SceneManager.floatv(color_d));
		glLight(light, GL_SPECULAR, SceneManager.floatv(color_s));
		glLight(light, GL_AMBIENT, SceneManager.floatv(color_a));
		glLightf(light, GL_CONSTANT_ATTENUATION, atten_const);
		glLightf(light, GL_LINEAR_ATTENUATION, atten_lin);
		glLightf(light, GL_QUADRATIC_ATTENUATION, atten_quad);
	}
	
	public static class DirectionalLight extends Light {

		public DirectionalLight(Frame f_, Color c, Vec3 norm) {
			super(f_);
			color_d = c;
			color_s = c;
			lightpos = new Vec4(norm, 0);
		}
		
		public void setColor(Color c) {
			color_d = c;
			color_s = c;
		}

		public void setNormal(Vec3 n) {
			lightpos = new Vec4(n, 0);
		}
		
	}

	public static class PointLight extends Light {
		
		private volatile float radius = 1;

		public PointLight(Frame f_, Color c, float radius_) {
			super(f_);
			color_d = c;
			color_s = c;
			radius = radius_;
			atten_const = 1f;
			lightpos = new Vec4(Vec3.zero, 1);
		}
		
		public void setColor(Color c) {
			color_d = c;
			color_s = c;
		}
		
		public void setRadius(float radius_) {
			radius = radius_;
			atten_lin = 1f / radius;
			atten_quad = 1f / (4 * radius * radius);
		}
		
	}

	public static class SpotLight extends PointLight {
		
		public SpotLight(Frame f_, Color c, float radius_, float spotcutoff_ , float spotexp_) {
			super(f_, c, radius_);
			spotdir = new Vec4(Vec3.j, 0);
			spot_cutoff = spotcutoff_;
			spot_exp = spotexp_;
		}
		
		public void setSpotCutoff(float spotcutoff_) {
			spot_cutoff = spotcutoff_;
		}
		
		public void setSpotExponent(float spotexp_) {
			spot_exp = spotexp_;
		}

	}
}
