package goldeneagle.scene;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import map.Segment;
import map.SegmentGenerator;
import map.TileTextureLoader;
import map.TileType;
import goldeneagle.Frame;
import goldeneagle.ResourceCache;

public class ChunkEntity extends Entity {
	public final int xOrigin;
	public final int yOrigin;
	
	public final TileType[] tiles;
	public final int[] tilesR;
	private List<Point> sandTiles; 
	private TileTextureLoader ttl;
	
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
		sandTiles = ttl.getTileLocation(TileType.GRASS);
		
//		System.out.println("Starting seg-gen");
////		Segment seg = SegmentGenerator.getInst().segmentAt(this.xOrigin / 32, this.yOrigin / 32);
//		System.out.println("seg-gen complete");
//		TileType[][] temp = seg.getTiles();
		tiles = new TileType[1024];
		Random rand = new Random();
		tilesR = new int[1024];
		for(int i = 0; i < 1024; i++)
			tilesR[i] = rand.nextInt(sandTiles.size());
//		for(int x = 0; x < temp.length; x++) {
//			for(int y = 0; y < temp[x].length; y++) {
//				tiles[(y*32)] = temp[x][y];
//			}
//		}
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
			
			int r = tilesR[i];
			double uvx = ((double)sandTiles.get(r).x / 32) * uv;
			double uvy = ((double)sandTiles.get(r).y / 32) * uv;
			
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
	public void Update() {
	}

}
