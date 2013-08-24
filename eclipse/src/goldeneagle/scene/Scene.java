package goldeneagle.scene;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import goldeneagle.*;
import goldeneagle.clock.Clock;

public class Scene implements Iterable<Entity> {
	
	private final Clock clock;
	private final Frame root;
	private final Set<Entity> entities;
	
	public Scene(Clock clock_) {
		clock = clock_;
		root = new Frame.Root(clock);
		entities = new HashSet<Entity>();
	}
	
	public Frame getRoot() {
		return root;
	}
	
	public Clock getClock() {
		return clock;
	}

	@Override
	public Iterator<Entity> iterator() {
		return this.entities.iterator();
	}

	public void AddEntity(Entity entity) {
		this.entities.add(entity);
	}	
}
