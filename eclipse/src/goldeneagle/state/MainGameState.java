package goldeneagle.state;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;

import goldeneagle.MovingFrame;
import goldeneagle.ParticleEmitter;
import goldeneagle.Vec3;
import goldeneagle.entities.*;
import goldeneagle.scene.Camera;
import goldeneagle.scene.Entity;
import goldeneagle.scene.Light;
import goldeneagle.scene.Scene;
import goldeneagle.scene.SceneManager;

public class MainGameState extends GameState {
	private Scene scene;
	private Camera cam;
	private Map<Long, ChunkEntity> chunks;
	private PlayerEntity player;
	
	@Override
	protected void init() {		
		this.chunks = new HashMap<Long, ChunkEntity>();
		this.scene = new Scene(getClock());
		this.cam = new Camera(scene.getRoot());
		player = new PlayerEntity(scene.getRoot());
		
		MovingFrame offset = new MovingFrame(player);
		offset.setLinear(new Vec3(0, 4, 0), Vec3.zero);
		cam.bindFrame(offset);
		cam.setRadius(11);
		
		MovingFrame uiOffset = new MovingFrame(cam);
		uiOffset.setLinear(new Vec3(-6, -6, 0), Vec3.zero);
		UI ui = new UI(uiOffset, player);
		scene.AddEntity(ui);
		
		int playerSpawnX = 4112;
		int playerSpawnY = 4112;
		
		player.setLinear(new Vec3(playerSpawnX, playerSpawnY), Vec3.zero);
//		
//		scene.AddEntity(new ChunkEntity(scene.getRoot(), playerSpawnX, playerSpawnY, scene));
		
		scene.AddEntity(player);
		
		scene.setAmbient(new Color(30, 30, 30));
		Light.SpotLight l = new Light.SpotLight(player, Color.YELLOW, 1, 10, Math.PI / 6, 2);
		l.setPitch(-Math.PI / 6);
		scene.addLight(l);

		
		MovingFrame mf = new MovingFrame(scene.getRoot());
		mf.setLinear(new Vec3(playerSpawnX, playerSpawnY), Vec3.zero);
		
		Light.PointLight lfollow = new Light.PointLight(player,  new Color(0.4f, 0.4f, 0.4f), 2, 0.8);
		scene.addLight(lfollow);
	

		FireEntity fire = new FireEntity(mf, scene);
		scene.AddEntity(fire);

		
//		ParticleEmitter pe = new ParticleEmitter(mf);
//		scene.AddEntity(pe);
	}	
	
	private long getID(int x, int y) {
		long id = (((long)x) << 32) | ((long)y);
		return id;
		
	}

	@Override
	protected void update(double deltaTime) {
		if(Keyboard.isKeyDown(Keyboard.KEY_EQUALS))
			cam.setRadius(cam.getRadius() + 0.1);
		if(Keyboard.isKeyDown(Keyboard.KEY_MINUS))
			cam.setRadius(cam.getRadius() - 0.1);
		
		this.ensureChunks();
		
		scene.Update(deltaTime);
	}
	
	protected void ensureChunks() {
		int spawnRadius = (int) (cam.getRadius() / 32)+1;
		for(int x = -spawnRadius; x <= spawnRadius; x++) {
			for(int y = -spawnRadius; y <= spawnRadius; y++) {
				long id = getID(player.getCurrentChunkX() + x, player.getCurrentChunkY() + y);
				if(!chunks.containsKey(id) || (chunks.containsKey(id) && !scene.hasEntity(chunks.get(id)))) {
					
					int chunkCoordX = (player.getCurrentChunkX()+x)*32;
					System.out.printf("chunkCoordX: %d\n", chunkCoordX);
					ChunkEntity chunk = new ChunkEntity(scene.getRoot(), chunkCoordX, (player.getCurrentChunkY()+y)*32, scene);
					
					
					chunks.put(id, chunk);
					scene.AddEntity(chunk);				
				}
			}
		}
		
		for(ChunkEntity chunk : this.chunks.values()) {
			int chunkX = (int) (chunk.getPosition().x / 32);
			int chunkY = (int) (chunk.getPosition().y / 32);
			int playerX = player.getCurrentChunkX();
			int playerY = player.getCurrentChunkY();
			
			if(new Vec3(playerX, playerY, 0).dist(new Vec3(chunkX, chunkY, 0)) > (cam.getRadius() / 32)+2) {
				if(scene.hasEntity(chunk)) {
				
					System.out.println("removing chunk");
					chunk.unload(scene);
					scene.RemoveEntity(chunk);
				}
			}
		}
		
//		int testRadius = 3;
//		for(int x = -testRadius; x <= testRadius; x++) {
//			if(x >= -spawnRadius && x <= spawnRadius)
//				continue;
//			
//			for(int y = -testRadius; y <= testRadius; y++) {
//				if(y >= -spawnRadius && y <= spawnRadius)
//					continue;
//				
//				long id = getID(player.getCurrentChunkX() + x, player.getCurrentChunkY() + y);
//				if(chunks.containsKey(id)) {
//					ChunkEntity chunk = chunks.get(id);

//				}
//			}
//		}
	}

	@Override
	protected void draw() {
		// hello, world!
		SceneManager.doFrame(scene, cam);
	}
}
