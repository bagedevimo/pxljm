package goldeneagle.scene;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.awt.Color;

import goldeneagle.*;
import goldeneagle.clock.Clock;

public class Scene implements Iterable<Entity> {
	
	private final Clock clock;
	private final Frame root;
	private final Set<Entity> entities;
	private final Set<Light> lights;
	
	private Color ambient;
	
	public Scene(Clock clock_) {
		clock = clock_;
		root = new Frame.Root(clock);
		entities = new HashSet<Entity>();
		lights = new HashSet<Light>();
	}
	
	public Frame getRoot() {
		return root;
	}
	
	public Clock getClock() {
		return clock;
	}

	public Color getAmbient() {
		return ambient;
	}
	
	public void setAmbient(Color c) {
		ambient = c;
	}
	
	@Override
	public Iterator<Entity> iterator() {
		return this.entities.iterator();
	}

	public void AddEntity(Entity entity) {
		this.entities.add(entity);
	}

	public void Update(double deltaTime) {
		for(Entity e : this.entities)
			e.update(deltaTime);
	}
	
	public void addLight(Light l) {
		lights.add(l);
	}
	
	public Set<Light> getLights() {
		return Collections.unmodifiableSet(lights);
	}
	
	public void getEntities(Collection<Entity> ents, Bound b) {
		// TODO quadtree
		for (Entity e : entities) {
			// TODO bound intersect niceness
			if (e.getBound() == null || b.intersects(e.getBound()) != null) {
				ents.add(e);
			}
		}
	}

}
