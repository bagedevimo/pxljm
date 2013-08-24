package goldeneagle.util;

import goldeneagle.Bound;
import goldeneagle.BoundingBox;
import goldeneagle.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class QuadTree<T extends QuadTree.Element> implements Iterable<T> {

	public static final int MAX_LEAF_ELEMENTS = 8;
	public static final String INDENT_STRING = "    ";

	public static interface Element {

		public Bound getBound();
	}

	protected static class OutOfBoundsException extends Exception {

		private static final long serialVersionUID = 1L;

		public OutOfBoundsException() {
			super();
		}

		public OutOfBoundsException(String arg0, Throwable arg1) {
			super(arg0, arg1);
		}

		public OutOfBoundsException(String arg0) {
			super(arg0);
		}

		public OutOfBoundsException(Throwable arg0) {
			super(arg0);
		}

	}

	protected static class Node<U extends Element> implements Iterable<U> {

		private Node<U> parent;
		private BoundingBox bound;

		private boolean isleaf = true;
		private Set<U> elements = new HashSet<U>();

		private int count = 0;

		// up to pow(2, n) children
		private Map<Integer, Node<U>> children = new HashMap<Integer, Node<U>>();

		public Node(Node<U> parent_, BoundingBox aabb_) {
			parent = parent_;
			this.bound = aabb_;
		}

		public Node<U> getParent() {
			return parent;
		}

		public void setParent(Node<U> nu) {
			parent = nu;
		}

		public BoundingBox getAABB() {
			return this.bound;
		}

		public int count() {
			return count;
		}

		public int countRecursively() {
			int c = elements.size();
			for (Node<U> nu : children.values()) {
				c += nu.count();
			}
			return c;
		}

		private int childID(Vec3 v) {
			Vec3 center = this.bound.center();
			int cid = 0;
			if(v.x >= center.x)
				cid |= 1;
			if(v.y >= center.y)
				cid |= 2;
			
			return cid;
		}

		private void unleafify() {
			if (isleaf) {
				// unleafify
				isleaf = false;
				Object[] temp = new Object[elements.size()];
				elements.toArray(temp);
				elements.clear();
				count -= temp.length;
				for (Object o : temp) {
					@SuppressWarnings("unchecked")
					U u2 = (U)o;
					try {
						add(u2);
					} catch (OutOfBoundsException e) {
						// this should NOT happen
						throw new AssertionError(e);
					}
				}
			}
		}

		private void leafify() {
			if (!isleaf) {
				// leafify
				isleaf = true;
				Set<U> elements2 = new HashSet<U>();
				for (U u : this) {
					elements2.add(u);
				}
				children.clear();
				elements = elements2;
			}
		}

		public boolean add(U u) throws OutOfBoundsException {
			Bound a = u.getBound();
			if (!this.bound.contains(a)) throw new OutOfBoundsException();
			if (isleaf && elements.size() < MAX_LEAF_ELEMENTS) {
				if (!elements.add(u)) return false;
			} else {
				// not a leaf or should not be. add to appropriate child node, or this if none.
				unleafify();
				final int cid_min = childID(a.min());
				final int cid_max = childID(a.max());
				if (cid_min != cid_max) {
					// element spans multiple child nodes. add to this.
					if (!elements.add(u)) return false;
				} else {
					// element is contained in one child node. add to it, creating if necessary.
					Node<U> child = children.get(cid_min);
					if (child == null) {
						Vec3 centre = this.bound.center();
						// this always points to a corner of the current node's aabb
						Vec3 vr = this.bound.max().sub(centre);
						if((cid_min & 1) == 0)
							vr = new Vec3(-vr.x, vr.y);
						if((cid_min & 2) == 0)
							vr = new Vec3(vr.x, -vr.y);
						
						child = new Node<U>(this, BoundingBox.fromExtremes(centre, centre.add(vr)));
						children.put(cid_min, child);
					}
					try {
						if (!child.add(u)) return false;
					} catch (OutOfBoundsException e) {
						// child doesnt want to accept it (fp inaccuracies maybe?)
						if (!elements.add(u)) return false;
					}
				}
			}
			count++;
			return true;
		}

		public void putChild(Node<U> child) {
			if (child == null) throw new NullPointerException();
			Integer cid = childID(child.getAABB().center());
			children.put(cid, child);
			count += child.count();
			unleafify();
		}

		public boolean remove(U u) {
			// TODO SPTree.Node remove
			return false;
		}

		public boolean contains(U u) {
			Bound a = u.getBound();
			if (!this.bound.contains(a)) return false;
			if (elements.contains(u)) return true;
			if (isleaf) return false;
			final int cid_min = childID(a.min());
			final int cid_max = childID(a.max());
			// if split across children, should be in elements
			if (cid_min != cid_max) return false;
			Node<U> child = children.get(cid_min);
			// if the child it would be in doesnt exist
			if (child == null) return false;
			return child.contains(u);
		}

		public void find(List<? super U> lu, Bound a) {
			if (bound.intersects(a)) {
				for (U u : elements) {
					if (u.getBound().intersects(a)) lu.add(u);
				}
				if (isleaf) return;
				final int cid_min = childID(a.min());
				final int cid_max = childID(a.max());
				if (cid_min ==cid_max) {
					// a is contained in one child
					Node<U> child = children.get(cid_min);
					if (child != null) child.find(lu, a);
				} else {
					for (Node<U> nu : children.values()) {
						nu.find(lu, a);
					}
				}
			}
		}

		public void print(String indent, String indent_delta) {
			System.out.println(indent + this.bound);
			for (U u : elements) {
				System.out.println(indent + indent_delta + u);
			}
			for (Node<U> nu : children.values()) {
				nu.print(indent, indent_delta);
			}
		}

		public int height() {
			int h = 0;
			for (Node<U> nu : children.values()) {
				int hn = nu.height();
				h = Math.max(h, hn);
			}
			return h + 1;
		}

		public double heightAvg() {
			double h = 0;
			int i = 0;
			for (Node<U> nu : children.values()) {
				h += nu.heightAvg();
				i++;
			}
			return 1 + (i > 0 ? h / i : 0);
		}

		@Override
		public Iterator<U> iterator() {
			return new NodeIterator();
		}

		private class NodeIterator implements Iterator<U> {

			private Iterator<U> it_el = elements.iterator();
			private Iterator<Node<U>> it_ch = children.values().iterator();
			private U next_u = null;

			public NodeIterator() {
				next_u = findNext();
			}

			private U findNext() {
				// this is depth-first
				try {
					while (true) {
						if (it_el.hasNext()) return it_el.next();
						it_el = it_ch.next().iterator();
					}
				} catch (NoSuchElementException e) {
					return null;
				}
			}

			@Override
			public boolean hasNext() {
				return next_u != null;
			}

			@Override
			public U next() {
				try {
					return next_u;
				} finally {
					next_u = findNext();
				}
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}

		}

	}

	private Node<T> root;

	public QuadTree() {
		root = new Node<T>(null, BoundingBox.fromExtremes(Vec3.zero, Vec3.one));
	}
	
	public QuadTree(Vec3 root_p0, Vec3 root_p1) {
		// TODO what happens if you have a non-regular root node?
		if (root_p0 == null || root_p1 == null) throw new NullPointerException("Cut it out!");
		root = new Node<T>(null, BoundingBox.fromExtremes(root_p0, root_p1));
	}

	public boolean add(T t) {
		if (t == null) throw new NullPointerException("Boo!");
		try {
			return root.add(t);
		} catch (OutOfBoundsException e) {
			// make new root
			Node<T> oldroot = root;
			Bound a = root.getAABB();
			// vector from centre to max of new root
			Vec3 vr = a.max().sub(a.min());
			// vector from centre of current root to centre of new element
			Vec3 vct = t.getBound().center().sub(a.center());
			// vector from centre of current root to corner nearest centre of new element
			Vec3 corner = vr.mul(0.5);
			if(vct.x < 0) corner = new Vec3(-corner.x, corner.y);
			if(vct.y < 0) corner = new Vec3(corner.x, -corner.y);
			
			// centre of new root
			Vec3 newcentre = a.center().add(corner);
			root = new Node<T>(null, BoundingBox.fromExtremes(newcentre.sub(vr), newcentre.add(vr)));
			if (oldroot.count() > 0) {
				// only preserve the old root if it had elements
				oldroot.setParent(root);
				root.putChild(oldroot);
			}
			return add(t);
		}
	}

	public boolean remove(T t) {

		return false;
	}

	public boolean contains(T t) {
		return root.contains(t);
	}

	public List<T> find(BoundingBox a) {
		if (a == null) throw new NullPointerException("Game over.");
		List<T> lt = new ArrayList<T>();
		root.find(lt, a);
		return lt;
	}

	public void print() {
		root.print("", INDENT_STRING);
	}

	public int height() {
		return root.height();
	}

	public double heightAvg() {
		return root.heightAvg();
	}

	public int count() {
		return root.count();
	}

	public int countRecursively() {
		return root.countRecursively();
	}

	@Override
	public Iterator<T> iterator() {
		return root.iterator();
	}
}
