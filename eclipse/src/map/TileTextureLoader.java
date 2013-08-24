package map;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TileTextureLoader {

	private int TILE_SIZE = 32;
	
	//maps the tile types to their position in the texture
	private Map<TileType, List<Point>> textureMap;
	
	public TileTextureLoader(String path) throws IOException {
		textureMap = new HashMap<TileType, List<Point>>();
		Scanner s = new Scanner(new File(path));
		try {
			String line = s.nextLine();
			Scanner sc = new Scanner(line);
			TILE_SIZE = sc.nextInt();
			sc.close();
		}catch(Exception e){ s.close(); throw e;}
		
		
		while(s.hasNextLine()){
			String line = s.nextLine();
			Scanner sc = new Scanner(line);
			
			if( line.charAt(0) == '#'){
				continue; //ignore comments
			}
			System.out.printf("Line: %s\n", line);
			
			TileType type = TileType.UNKNOWN; //default value
			try{
				String toke = sc.next().toUpperCase();
				if(toke.equals("GRASS"))
					type = TileType.GRASS;
				if(toke.equals("BEACH"))
					type = TileType.BEACH;
				if(toke.equals("RIVER"))
					type = TileType.RIVER;
				else if(toke.equals("STONE"))
					 type = TileType.STONE;
				else if(toke.equals("OCEAN"))
					type = TileType.OCEAN;
				
				System.out.printf("Type: %s\n", type);
				
				if (!textureMap.containsKey(type))
					textureMap.put(type, new ArrayList<Point>());
				
				int xPos = sc.nextInt();
				
				int yPos = sc.nextInt();
				int xNum = sc.nextInt();
				int yNum = sc.nextInt();
				
				for(int x=0; x<xNum; x++){
					for(int y=0; y<yNum; y++){
						textureMap.get(type).add(new Point((xPos + x * TILE_SIZE), (yPos + y * TILE_SIZE)));
					}
				}
			}catch(Exception e){}
			sc.close();
		}
		s.close();
	} 
	
	public List<Point> getTileLocation(TileType type){
		List<Point> list = textureMap.get(type);
		if(list != null)
			return new ArrayList<Point>(textureMap.get(type));
		return null;
	}
	
	public int getTileSize(){
		return TILE_SIZE;
	}
	
}
