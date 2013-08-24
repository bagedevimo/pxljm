package map;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TileTextureLoader {

	private int TILE_SIZE = 32;
	private final String TILE_TEXTURE_PATH = "../assets/images/tiletex_info.txt";
	
	//maps the tile types to their position in the texture
	private Map<TileType, List<Point>> textureMap;
	
	public TileTextureLoader() {
		Scanner s = new Scanner(TILE_TEXTURE_PATH);
		try{
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
			
			TileType type = TileType.GRASS; //default value
			try{
				switch (sc.next().toUpperCase()){
				case "GRASS": type = TileType.GRASS;
				case "BEACH": type = TileType.BEACH;
				case "RIVER": type = TileType.RIVER;
				case "STONE": type = TileType.STONE;
				case "OCEAN": type = TileType.OCEAN;
				}
				
				if (!textureMap.containsKey(type))
					textureMap.put(type, new ArrayList<Point>());
				
				int xPos = sc.nextInt();
				int xNum = sc.nextInt();
				int yPos = sc.nextInt();
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
