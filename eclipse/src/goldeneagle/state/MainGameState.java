package goldeneagle.state;

import java.util.HashMap;
import java.util.Map;

import goldeneagle.MovingFrame;
import goldeneagle.Vec3;
import goldeneagle.entities.ChunkEntity;
import goldeneagle.entities.PlayerEntity;
import goldeneagle.scene.Camera;
import goldeneagle.scene.Scene;
import goldeneagle.scene.SceneManager;

public class MainGameState extends GameState {
	private Scene scene;
	private Camera cam;
	private Map<Long, ChunkEntity> chunks = new HashMap<Long, ChunkEntity>();
	
	@Override
	protected void init() {
		this.scene = new Scene(getClock());
		this.cam = new Camera();
		PlayerEntity player = new PlayerEntity(scene.getRoot());
		MovingFrame offset = new MovingFrame(player);
		offset.setLinear(new Vec3(0, 4, 0), Vec3.zero);
		cam.bindFrame(offset);
		cam.setRadius(10);
		
//		for(int x = 0; x <= 2; x++) {
//			for(int y = 0; y <= 2; y++) {
//				chunks.put(getID(x, y), new ChunkEntity(scene.getRoot(), x*1024, y*1024));
//				scene.AddEntity(chunks.get(getID(x, y)));				
//			}
//		}
//		
		scene.AddEntity(new ChunkEntity(scene.getRoot(), 64*32, 64*32));
		
		scene.AddEntity(player);
	}	
	
	private long getID(int x, int y) {
		return (((long)x) << 32) | ((long)y);
		
	}

	@Override
	protected void update(double deltaTime) {
		scene.Update(deltaTime);
	}

	@Override
	protected void draw() {
		// hello, world!
		SceneManager.doFrame(scene, cam);
	}
}
