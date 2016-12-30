package gui;
import model.Terrain;

public class CharMap {
	public static final char get(int i){
		switch(i){
			case Terrain.CHASM:
				return ' ';
			case Terrain.EMPTY:
				return '.';
			case Terrain.WALL:
				return '█';
			case Terrain.DOOR:
				return 'd';
			case Terrain.OPEN_DOOR:
				return 'D';
			default:
				return 0;
		}
	}
}
