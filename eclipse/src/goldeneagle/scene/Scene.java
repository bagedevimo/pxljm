package goldeneagle.scene;

import goldeneagle.*;
import goldeneagle.clock.Clock;

public class Scene {
	
	private final Clock clock;
	private final Frame root;
	
	public Scene(Clock clock_) {
		clock = clock_;
		root = new Frame.Root(clock);
	}
	
	public Frame getRoot() {
		return root;
	}
	
	public Clock getClock() {
		return clock;
	}
	
	// TODO actual containment of entities
	
}
