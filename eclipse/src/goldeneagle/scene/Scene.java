package goldeneagle.scene;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.awt.Color;

import goldeneagle.*;
import goldeneagle.clock.Clock;
import goldeneagle.state.Collidable;
import goldeneagle.util.*;

public class Scene implements Iterable<Entity> {
	private static final int updateProfile = Profiler
			.createSection("Scene.update");
	private final Clock clock;
	private final Frame root;
	private final QuadTree<Entity> bounded_entities;
	private final Set<Entity> unbounded_entities;
	private final Set<Light> lights;

	private Queue<Entity> removeList;

	private Color ambient;

	public Scene(Clock clock_) {
		clock = clock_;
		root = new Frame.Root(clock);
		unbounded_entities = new HashSet<Entity>();
		bounded_entities = new QuadTree<Entity>();
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
		return this.unbounded_entities.iterator();
	}

	public void AddEntity(Entity entity) {
		if (entity.getBound() == null) {
			unbounded_entities.add(entity);
		} else {
			bounded_entities.add(entity);
		}
	}

	public void Update(double deltaTime) {
		Profiler.enter(updateProfile);
		for (Entity e : this.unbounded_entities)
			if (!e.update(deltaTime, this))
				this.removeList.add(e);

		for (Entity e : this.bounded_entities)
			if (!e.update(deltaTime, this))
				this.removeList.add(e);

		while (!this.removeList.isEmpty()) {
			Entity e = this.removeList.remove();
			this.unbounded_entities.remove(e);
			if (e.getBound() != null && bounded_entities.contains(e)) {
				bounded_entities.remove(e);
			}
		}
		Profiler.exit(updateProfile);
	}

	public void RemoveEntity(Entity e) {
		if (e == null)
			return;
		this.removeList.add(e);
	}

	public boolean hasEntity(Entity e) {
		return (unbounded_entities.contains(e) || (e.getBound() != null && bounded_entities
				.contains(e))) && !removeList.contains(e);
	}

	public void addLight(Light l) {
		lights.add(l);
	}

	public Set<Light> getLights() {
		return Collections.unmodifiableSet(lights);
	}

	public void getEntities(Collection<Entity> ents, Bound b) {
		// add all entitities of unknown bound
		for (Entity e : unbounded_entities) {
			ents.add(e);
		}
		// bounded lookup
		bounded_entities.find(ents, b);
	}
	
	public List<Collidable> getCollisions(Entity src){
		List<Collidable> col = new ArrayList<Collidable>();
		Collidable c = (Collidable)src;
		List<Entity> sample = new ArrayList<Entity>();
		this.getEntities(sample, new BoundingSphere(src, 2));
		for (Entity e : sample) {
			if (e instanceof Collidable && !e.equals(c)) {
				if(((Collidable)e).getCollisionBound().intersects( c.getCollisionBound())){
					col.add((Collidable)e);
				}
			}
		}
		return col;
	} 

}
