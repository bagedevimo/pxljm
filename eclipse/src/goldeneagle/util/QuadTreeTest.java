package goldeneagle.util;

import java.util.ArrayList;
import java.util.List;

import goldeneagle.Bound;
import goldeneagle.BoundingSphere;
import goldeneagle.MovingFrame;
import goldeneagle.Vec3;
import goldeneagle.clock.SystemClock;
import goldeneagle.scene.Frame;

public class QuadTreeTest {

	public static class Thing extends Frame implements QuadTree.Element {

		private BoundingSphere bs;
		private Vec3 p;
		
		public Thing(Frame parent_, Vec3 p_) {
			super(parent_);
			p = p_;
			bs = new BoundingSphere(this, 0.1);
		}
		
		@Override
		public Bound getBound() {
			return bs;
		}

		@Override
		public Vec3 getPosition() {
			return p;
		}

		@Override
		public double getRotation() {
			return 0;
		}
		
		@Override
		public String toString() {
			return p.toString();
		}
		
	}
	
	public static void main(String[] args) {

		Frame root = new Frame.Root(new SystemClock());
		
		QuadTree<Thing> qt = new QuadTree<Thing>();
		
		Vec3 min = new Vec3(-10, -10);
		Vec3 max = new Vec3(10, 10);
		
		List<Thing> list = new ArrayList<Thing>();
		for (int i = 0; i < 300; i++) {
			Thing t = new Thing(root, Vec3.random(min, max));
			list.add(t);
			qt.add(t);
		}
		
		qt.print();
		
		for (Thing t : list) {
			if (!qt.contains(t)) {
				System.err.println("THIS IS BAD : " + t);
			}
		}
		
		System.out.println("Avg height: " + qt.heightAvg());
		
		MovingFrame mf = new MovingFrame(root);
		BoundingSphere bs = new BoundingSphere(mf, 1);
		mf.setLinear(new Vec3(3, 7), Vec3.zero);
		
		System.out.println();
		
		List<Thing> hits = new ArrayList<Thing>();
		qt.find(hits, bs);
		for (Thing t : hits) {
			System.out.println(t);
		}
		
	}

}
