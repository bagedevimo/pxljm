package goldeneagle.scene;

import goldeneagle.*;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

/**
 * 
 * Convex shape extruded on +Z to cast shadows.
 * 
 */
public class ShadowCaster {

	private List<Vec3> vertices = new ArrayList<Vec3>();

	public ShadowCaster() {

	}

	public void addVertex(Vec3 v) {
		vertices.add(v);
	}

	/**
	 * 
	 * @param lightpos
	 *            needs actual z value (height)
	 * @param height
	 */
	public void drawShadowVolume(Vec4 lightpos, double height) {
		// Vec3 ldir = lightpos.w < 0.0001 ? lightpos.xyz() :
		// lightpos.homogenise().sub(v);
		if (vertices.size() < 2) return;

		// light above caster => back edges (inverted) + top face
		// light not above caster => front edges
		
		boolean use_front = lightpos.z < height;
		
		if (!use_front) {
			// draw top face
			glBegin(GL_POLYGON);
			for (Vec3 v : vertices) {
				glVertex3d(v.x, v.y, height);
			}
			glEnd();
		}
		
		for (int i = 0; i < vertices.size(); i++) {
			int j = i + 1 >= vertices.size() ? 0 : i + 1;
			Vec3 a = vertices.get(i).withZ(height);
			Vec3 b = vertices.get(j).withZ(height);
			Vec3 n = b.sub(a).unit().cross(Vec3.k);
			Vec3 ldira = lightpos.xyz(), ldirb = lightpos.xyz();
			if (lightpos.w > 0.0001) {
				ldira = ldira.sub(a).unit();
				ldirb = ldirb.sub(b).unit();
			}
			Vec3 c = b.sub(ldirb.mul(100));
			Vec3 d = a.sub(ldira.mul(100));
			if (n.dot(ldira.reject(Vec3.k).unit()) > 0) {
				// front face
				if (use_front) {
					glBegin(GL_POLYGON);
					glVertex3d(a.x, a.y, a.z);
					glVertex3d(b.x, b.y, b.z);
					glVertex3d(c.x, c.y, c.z);
					glVertex3d(d.x, d.y, d.z);
					glEnd();
				}
			} else {
				// back face
				if (!use_front) {
					glBegin(GL_POLYGON);
					glVertex3d(b.x, b.y, b.z);
					glVertex3d(a.x, a.y, a.z);
					glVertex3d(d.x, d.y, d.z);
					glVertex3d(c.x, c.y, c.z);
					glEnd();
				}
			}
		}

	}

}
