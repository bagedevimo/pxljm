package goldeneagle.scene;

import java.util.ArrayList;

import goldeneagle.BoundingBox;

public class QuadTree {
	public static interface Element {
		public BoundingBox getBoundingBox();
	}
	
	private ArrayList<Element> elements;
	
	private QuadTree topLeft;
	private QuadTree topRight;
	private QuadTree bottomLeft;
	private QuadTree bottomRight;
	
	public QuadTree() {
		this.elements = new ArrayList<Element>();
	}
}
