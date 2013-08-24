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
	private Map<Long, ChunkEntity> chunks;
	
	@Override
	protected void init() {
		this.chunks = new HashMap<Long, ChunkEntity>();
		this.scene = new Scene(getClock());
		this.cam = new Camera();
		PlayerEntity player = new PlayerEntity(scene.getRoot());
		MovingFrame offset = new MovingFrame(player);
		offset.setLinear(new Vec3(0, 4, 0), Vec3.zero);
		cam.bindFrame(offset);
		cam.setRadius(10);
		
		int playerSpawnX = 4096;
		int playerSpawnY = 4096;
		int spawnRadius = 2;
		player.setLinear(new Vec3(playerSpawnX, playerSpawnY), Vec3.zero);
		
//		for(int x = -spawnRadius; x <= spawnRadius; x++) {
//			for(int y = -spawnRadius; y <= spawnRadius; y++) {
//				double thisX = player.getPosition().x + (x*32);
//				double thisY = player.getPosition().y + (y*32);
//				System.out.printf("X:%f\tY:%f\n", thisX, thisY);
//				ChunkEntity chunk = new ChunkEntity(scene.getRoot(), (int)thisX, (int)thisY, scene);
////				chunks.put(id, chunk);
//				scene.AddEntity(chunk);				
//			}
//		}
//		
		scene.AddEntity(new ChunkEntity(scene.getRoot(), playerSpawnX, playerSpawnY, scene));
		
		scene.AddEntity(player);
	}	
	
	private long getID(int x, int y) {
		long id = (((long)x) << 32) | ((long)y);
		return id;
		
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
