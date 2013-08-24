package goldeneagle.entities;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import map.Segment;
import map.SegmentGenerator;
import map.TileTextureLoader;
import map.TileType;
import goldeneagle.ResourceCache;
import goldeneagle.Vec3;
import goldeneagle.scene.Entity;
import goldeneagle.scene.Frame;
import goldeneagle.scene.Scene;
import goldeneagle.scene.SceneManager;
import goldeneagle.util.Profiler;

public class ChunkEntity extends Entity {
	public final TileType[] tiles;
	public final int[] tilesR;
	private List<Point> sandTiles; 
	private TileTextureLoader ttl;
	private Map<TileType, List<Point>> tileMaps;
	static final int ChunkDraw = Profiler.createSection("ChunkEntity.Draw.glDRAW");
	static final int ChunkUV = Profiler.createSection("ChunkEntity.Draw.uv");
	
	public ChunkEntity(Frame parent_, int baseX, int baseY, Scene scene) {
		super(parent_);
		this.setLinear(new Vec3(baseX, baseY), Vec3.zero);
		
		try {
			ttl = new TileTextureLoader("./assets/tiles/tiletex_info.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tileMaps = new HashMap<TileType, List<Point>>();
		tileMaps.put(TileType.GRASS, ttl.getTileLocation(TileType.GRASS));
		tileMaps.put(TileType.BEACH, ttl.getTileLocation(TileType.BEACH));
		tileMaps.put(TileType.STONE, ttl.getTileLocation(TileType.STONE));
		tileMaps.put(TileType.RIVER, ttl.getTileLocation(TileType.RIVER));
		tileMaps.put(TileType.OCEAN, ttl.getTileLocation(TileType.OCEAN));
//		tileMaps.put(TileType.OCEAN, ttl.getTileLocation(TileType.GRASS));
		
		System.out.println("Starting seg-gen");
		System.out.println("Originas" + this.getPosition().x / 32 + " :: " + this.getPosition().y / 32);
		Segment seg = SegmentGenerator.getInst().segmentAt(this.getPosition().x, this.getPosition().y);
		
		for(Vec3 tree : seg.getTrees()) {
			System.out.printf("Added tree @ %f %f, r=%f\n", tree.x, tree.y, tree.z);
			scene.AddEntity(new TreeEntity(scene.getRoot(), this.getPosition().x+tree.x, this.getPosition().y+tree.y, tree.z));
		}
		
		for(Vec3 plant : seg.getPlants()) {
			System.out.printf("Added tree @ %f %f, r=%f\n", plant.x, plant.y, plant.z);
			scene.AddEntity(new PlantEntity(scene.getRoot(), this.getPosition().x+plant.x, this.getPosition().y+plant.y, plant.z));
		}
		
		System.out.println("seg-gen complete");
		TileType[][] temp = seg.getTiles();
		
		boolean hasGrass = false;
		tiles = new TileType[1024];
		for(int x = 0; x < temp.length; x++) {
			for(int y = 0; y < temp[x].length; y++) {
				tiles[(y*32)+x] = temp[x][y];
			}
		}
		
		Random rand = new Random();
		tilesR = new int[1024];
		for(int i = 0; i < 1024; i++) {
			if(tileMaps.containsKey(tiles[i]))
				tilesR[i] = rand.nextInt(tileMaps.get(tiles[i]).size());
			else
				tilesR[i] = 0;
		}
	}
	
	public Vec3 Center() {
		return this.getPosition().add(new Vec3(512, 512, 0));
	}

	@Override
	public void Draw() {
		int texID = -1;
		try {
			texID = ResourceCache.GetGLTexture("./assets/tiles/atlas.jpg");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 0.03125
		
		final double uv = 0.03125;
		
		glBindTexture(GL_TEXTURE_2D, texID);
		glEnable(GL_TEXTURE_2D);
		Profiler.enter(ChunkDraw);
		glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, SceneManager.floatv(1f, 1f, 1f, 1f));
		
		glBegin(GL_QUADS);
		glNormal3d(0, 0, 1);
		
		for(int i = 0; i < 1024; i++) {
			int x = i % 32;
			int y = i / 32;
			
			double uvx = 0, uvy = 0;
			int r = tilesR[i];
			if(tileMaps.containsKey(tiles[i])) {
				uvx = ((double)tileMaps.get(tiles[i]).get(r).x / 1024);
				uvy = ((double)tileMaps.get(tiles[i]).get(r).y / 1024);
			}
			
//			Profiler.enter(ChunkUV);
			
			glTexCoord2d(uvx, uvy);
			glVertex3d(x, y, SceneManager.Z_TERRAIN);
			glTexCoord2d(uvx+uv, uvy);
			glVertex3d(x+1, y, SceneManager.Z_TERRAIN);
			glTexCoord2d(uvx+uv, uvy+uv);
			glVertex3d(x+1, y+1, SceneManager.Z_TERRAIN);
			glTexCoord2d(uvx, uvy+uv);
			glVertex3d(x, y+1, SceneManager.Z_TERRAIN);
			
//			Profiler.exit(ChunkUV);
		}
		
		glEnd();
		
		Profiler.exit(ChunkDraw);
		glDisable(GL_TEXTURE_2D);
	}

	@Override
	public void Update(double deltaTime) {
	}

}
