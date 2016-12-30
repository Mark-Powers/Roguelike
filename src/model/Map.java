package model;

import gui.CharMap;
import util.Roll;

public class Map {
	private final int MIN_HEIGHT = 5;
	private final int MIN_WIDTH = 5;
	private int playerX;
	private int playerY;
	
	private int width;
	private int height;
	private int[] map;

	public Map(int w, int h, int playerX, int playerY) {
		width = w;
		height = h;
		this.playerX = playerX;
		this.playerY = playerY;

		map = new int[w * h];

		generateDungeon();
		// generateSquareMap();
	}

	private void generateDungeon() {
		// Place main rooms
		int roomCount = (int) Math.sqrt(width * height);
		for (int i = 0; i < roomCount; i++) {
			int x = Roll.between(0, width - MIN_WIDTH);
			int y = Roll.between(0, height - MIN_HEIGHT);
			setSquare(x, y, Roll.between(MIN_WIDTH, width - x), Roll.between(MIN_HEIGHT, height - y));
		}

		// Place starting room
		int index;
		if(playerX <= MIN_WIDTH/2){
			playerX = MIN_WIDTH/2;
		}
		if(playerY <= MIN_HEIGHT/2){
			playerY = MIN_HEIGHT/2;
		}
		for (int i = 0; i < MIN_WIDTH; i++) {
			for (int u = 0; u < MIN_HEIGHT; u++) {
				index = playerX - MIN_HEIGHT/2 + i + (playerY + u - MIN_HEIGHT/2) * width;
				if (i == 0 || u == 0 || i == MIN_WIDTH - 1 || u == MIN_HEIGHT - 1) {
					map[index] = Terrain.WALL;
				} else {
					map[index] = Terrain.EMPTY;
				}
			}
		}
		
		// Make sure stuff is ok
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				index = x + y * width;
				if (map[index] == Terrain.WALL) {
					int emptyCount = 0;
					int wallCount = 0;
					int chasmCount = 0;
					if (x > 0) {
						if (map[(x - 1) + y * width] == Terrain.EMPTY) {
							emptyCount++;
						} else if (map[(x - 1) + y * width] == Terrain.WALL) {
							wallCount++;
						} else if (map[(x - 1) + y * width] == Terrain.CHASM) {
							chasmCount++;
						}
					}
					if (y > 0) {
						if (map[x + (y - 1) * width] == Terrain.EMPTY) {
							emptyCount++;
						} else if (map[x + (y - 1) * width] == Terrain.WALL) {
							wallCount++;
						} else if (map[x + (y - 1) * width] == Terrain.CHASM) {
							chasmCount++;
						}
					}
					if (x < width - 1) {
						if (map[(x + 1) + y * width] == Terrain.EMPTY) {
							emptyCount++;
						} else if (map[(x + 1) + y * width] == Terrain.WALL) {
							wallCount++;
						} else if (map[(x + 1) + y * width] == Terrain.CHASM) {
							chasmCount++;
						}
					}
					if (y < height - 1) {
						if (map[x + (y + 1) * width] == Terrain.EMPTY) {
							emptyCount++;
						} else if (map[x + (y + 1) * width] == Terrain.WALL) {
							wallCount++;
						} else if (map[x + (y + 1) * width] == Terrain.CHASM) {
							chasmCount++;
						}
					}
					if (emptyCount >= 2) {
						map[index] = Terrain.EMPTY;
					}
					if (wallCount > 2) {
						map[index] = Terrain.EMPTY;
					}
					if (chasmCount >= 1) {
						map[index] = Terrain.WALL;
					}
				}
			}
		}

		
	}

	private void setSquare(int x, int y, int w, int h) {
		int index;
		for (int i = 0; i < w; i++) {
			for (int u = 0; u < h; u++) {
				index = x + i + (y + u) * width;
				if (i == 0 || u == 0 || i == w - 1 || u == h - 1) {
					map[index] = Terrain.WALL;
				} else {
					map[index] = Terrain.EMPTY;
				}
			}
		}
	}

	private void generateSquareMap() {
		int index;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				index = x + y * width;
				if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
					map[index] = Terrain.WALL;
				} else {
					map[index] = Terrain.EMPTY;
				}
			}
		}
	}

	public boolean isPassable(int x, int y) {
		return (Terrain.flags[map[y * width + x]] & Terrain.PASSABLE) != 0;
	}
	
	public boolean isSolid(int x, int y){
		return (Terrain.flags[map[y*width + x]] & Terrain.SOLID) != 0;
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
