package goldeneagle.scene;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.awt.Color;

import goldeneagle.*;
import goldeneagle.clock.Clock;
import goldeneagle.util.*;

public class Scene implements Iterable<Entity> {
	private static final int updateProfile = Profiler.createSection("Scene.update");
	private final Clock clock;
	private final Frame root;
	private final Set<Entity> entities;
	private final Set<Light> lights;
	
	private Queue<Entity> removeList;
	
	private Color ambient;
	
	public Scene(Clock clock_) {
		clock = clock_;
		root = new Frame.Root(clock);
		entities = new HashSet<Entity>();
		lights = new HashSet<Light>();
		removeList = new LinkedList<Entity>();
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
		Profiler.enter(updateProfile);
		for(Entity e : this.entities)
			if(!e.update(deltaTime))
				this.removeList.add(e);
		
		while(!this.removeList.isEmpty()) {
			Entity e = this.removeList.remove();
			if(this.entities.contains(e))
				this.entities.remove(e);
		}
		Profiler.exit(updateProfile);
	}
	
	public void RemoveEntity(Entity e) {
		this.removeList.add(e);
	}
	
	public boolean hasEntity(Entity e) {
		return this.entities.contains(e) && !this.removeList.contains(e);
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
