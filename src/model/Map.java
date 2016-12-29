package model;
import gui.CharMap;

public class Map {
	int width;
	int height;
	int[] map;

	public Map(int w, int h) {
		width = w;
		height = h;

		map = new int[w * h];

		generateMap();
	}

	private void generateMap() {
		int index;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				index = x + y * width;
				if (x == 0 || y == 0 || x == width-1 || y == height-1) {
					map[index] = Terrain.WALL;
				} else {
					map[index] = Terrain.EMPTY;
				}
			}
		}
	}
	
	public boolean isPassable(int x, int y){
		return (Terrain.flags[map[y*width + x]] & Terrain.PASSABLE) != 0;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		int index;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				index = x + y * width;
				sb.append(CharMap.get(map[index]));
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
