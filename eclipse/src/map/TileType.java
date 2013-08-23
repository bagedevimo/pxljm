package map;

import map.islandbase.Biome;

public enum TileType {
	GRASS,
	BEACH,
	RIVER,
	STONE,
	OCEAN;

	static TileType getType(Biome b){
		switch(b){
		case MARSH:
		case ICE:
		case LAKE:
			return TileType.RIVER;
		case OCEAN:
			return TileType.OCEAN;
		case BEACH:
			return TileType.BEACH;
		case SNOW:
		case TUNDRA:
		case BARE:
		case SCORCHED:
		case TEMPERATE_DESERT:
		case SUBTROPICAL_DESERT:
			return RIVER;
		default:
			return TileType.GRASS;
		}
	}
}
