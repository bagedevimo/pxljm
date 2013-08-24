package goldeneagle.scene;

import java.awt.Color;

import goldeneagle.Quat;
import goldeneagle.Vec3;
import goldeneagle.Vec4;
import static org.lwjgl.opengl.GL11.*;

/**
 * Light supertype and implementations. All angles handled by these classes are in radians.
 */
public class Light extends BoundFrame {

	protected Vec4 lightpos = Vec4.zero;
	protected Vec4 spotdir = Vec4.zero;
	protected Color color_d = Color.WHITE, color_s = Color.WHITE, color_a = Color.BLACK;
	protected float spot_cutoff = 180f;
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
		SceneManager.multMatrix(getTransformToRoot());
		int light = GL_LIGHT0 + id;
		glLight(light, GL_POSITION, SceneManager.floatv(lightpos));
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
		
		private float radius = 1;
		
		public PointLight(Frame f_, Color c, double height_, double radius_) {
			super(f_);
			color_d = c;
			color_s = c;
			radius = (float) radius_;
			atten_const = 1f;
			lightpos = new Vec4(0, 0, height_, 1);
		}
		
		public void setColor(Color c) {
			color_d = c;
			color_s = c;
		}
		
		public void setHeight(double h) {
			lightpos = new Vec4(0, 0, h, 1);
		}
		
		public void setRadius(double radius_) {
			radius = (float) radius_;
			atten_lin = 1f / radius;
			atten_quad = 1f / (4 * radius * radius);
		}
		
	}

	public static class SpotLight extends PointLight {
		
		public SpotLight(Frame f_, Color c, double height_, double radius_, double spotcutoff_, double spotexp_) {
			super(f_, c, height_, radius_);
			spotdir = new Vec4(0, 1, 0, 0);
			setSpotCutoff(spotcutoff_);
			setSpotExponent(spotexp_);
		}
		
		public void setPitch(double angle) {
			spotdir = new Vec4(new Quat(Vec3.i, angle).rot(Vec3.j), 0);
		}
		
		public void setSpotCutoff(double spotcutoff_) {
			spot_cutoff = (float) (spotcutoff_ * 180d / Math.PI);
		}
		
		public void setSpotExponent(double spotexp_) {
			spot_exp = (float) spotexp_;
		}

	}
}
