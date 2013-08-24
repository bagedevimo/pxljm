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
import goldeneagle.Frame;
import goldeneagle.ResourceCache;
import goldeneagle.scene.Entity;
import goldeneagle.scene.SceneManager;

public class ChunkEntity extends Entity {
	public final double xOrigin;
	public final double yOrigin;
	
	public final TileType[] tiles;
	public final int[] tilesR;
	private List<Point> sandTiles; 
	private TileTextureLoader ttl;
	private Map<TileType, List<Point>> tileMaps;
	
	public ChunkEntity(Frame parent_, int baseX, int baseY) {
		super(parent_);
		
		this.xOrigin = baseX;
		this.yOrigin = baseY;
		
		try {
			ttl = new TileTextureLoader("./assets/tiles/tiletex_info.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tileMaps = new HashMap<TileType, List<Point>>();
		tileMaps.put(TileType.GRASS, ttl.getTileLocation(TileType.GRASS));
		tileMaps.put(TileType.BEACH, ttl.getTileLocation(TileType.BEACH));
//		tileMaps.put(TileType.OCEAN, ttl.getTileLocation(TileType.GRASS));
		
		System.out.println("Starting seg-gen");
		System.out.println("Originas" + this.xOrigin / 32 + " :: " + this.yOrigin / 32);
		Segment seg = SegmentGenerator.getInst().segmentAt(this.xOrigin, this.yOrigin);
		System.out.println("seg-gen complete");
		TileType[][] temp = seg.getTiles();
		
		boolean hasGrass = false;
		tiles = new TileType[1024];
		for(int x = 0; x < temp.length; x++) {
			for(int y = 0; y < temp[x].length; y++) {
				tiles[(y*32)+x] = temp[x][y];
				System.out.printf("temp[%d][%d]=%s\n", x, y, temp[x][y]);
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
		
		glBegin(GL_QUADS);
	
		glColor3d(1, 1, 1);
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
			
			glTexCoord2d(uvx, uvy);
			glVertex3d(x, y, SceneManager.Z_TERRAIN);
			glTexCoord2d(uvx+uv, uvy);
			glVertex3d(x+1, y, SceneManager.Z_TERRAIN);
			glTexCoord2d(uvx+uv, uvy+uv);
			glVertex3d(x+1, y+1, SceneManager.Z_TERRAIN);
			glTexCoord2d(uvx, uvy+uv);
			glVertex3d(x, y+1, SceneManager.Z_TERRAIN);
		}
		
		glEnd();
		
		glDisable(GL_TEXTURE_2D);
	}

	@Override
	public void Update(double deltaTime) {
	}

}
