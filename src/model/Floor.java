package model;

import java.util.ArrayList;

import gui.CharMap;
import model.actors.Actor;
import model.actors.Slime;
import model.actors.Zombie;
import model.items.DroppedItem;
import model.items.Item;
import model.items.MeleeWeapon;
import model.items.Potion;
import util.Point;
import util.Roll;

public class Floor {
	private final int MIN_HEIGHT = 5;
	private final int MIN_WIDTH = 5;
	private int playerX;
	private int playerY;
	
	private int width;
	private int height;
	private int[] map;
	
	private ArrayList<Actor> actors;
	private ArrayList<DroppedItem> items;

	public Floor(int w, int h, int playerX, int playerY) {
		width = w;
		height = h;
		this.playerX = playerX;
		this.playerY = playerY;

		map = new int[w * h];
		actors = new ArrayList<Actor>();		
		items = new ArrayList<DroppedItem>();
		
		generateTestMap();
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

	/**
	 * A simple map to test mechanics
	 */
	private void generateTestMap() {
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
		
		for(int i = 0; i < 8; i++){
			map[5 + i + 12 * width] = Terrain.WALL;
		}
		map[5 + 11 * width] = Terrain.WALL;
		map[5 + 10 * width] = Terrain.DOOR;
		map[5 + 9 * width] = Terrain.WALL;
		for(int i = 0; i < 8; i++){
			map[5 + i + 8 * width] = Terrain.WALL;
		}
		map[12 + 11 * width] = Terrain.WALL;
		map[12 + 10 * width] = Terrain.DOOR;
		map[12 + 9 * width] = Terrain.WALL;
		
		map[10 + 5*width] = Terrain.STAIR_DOWN;
		map[12 + 5*width] = Terrain.STAIR_UP;
		
		actors.add(new Slime(10, 10));
		actors.add(new Slime(15, 10));
		actors.add(new Zombie(15, 8));
		
		items.add(new DroppedItem(new Potion(), new Point(20, 5)));
		items.add(new DroppedItem(new MeleeWeapon("a staff", 1, 3), new Point(5, 15)));
	}

	public void addItem(DroppedItem i){
		items.add(i);
	}
	
	public Item getItemAt(int x, int y){
		for (DroppedItem i : items) {
			if (i.pos.x == x && i.pos.y == y) {
				return i.item;
			}
		}
		return null;
	}
	
	public DroppedItem removeItem(int x, int y){
		for(int index = 0; index < items.size(); index++){
			DroppedItem i = items.get(index);
			if(i.pos.x == x && i.pos.y == y){
				return items.remove(index);
			}
		}
		return null;		
	}
	
	/**
	 * Updates map based on movement.
	 * i.e. Opens doors, sets traps
	 */
	public void move(int x, int y){
		int index = x+y*width;
		if(map[index] == Terrain.DOOR){
			map[index] = Terrain.OPEN_DOOR;
		}
	}
	
	public boolean isPassable(int x, int y) {
		return (Terrain.flags[map[y * width + x]] & Terrain.PASSABLE) != 0;
	}
	
	public boolean isSolid(int x, int y){
		return (Terrain.flags[map[y*width + x]] & Terrain.SOLID) != 0;
	}
	
	public boolean isAvoid(int x, int y){
		return (Terrain.flags[map[y*width + x]] & Terrain.AVOID) != 0;
	}
	
	public int getTile(int x, int y){
		return map[y*width + x];
	}

	public boolean canMoveToSpace(int x, int y) {
		return !isOccupied(x, y) && isPassable(x, y);
	}
	
	public boolean isOccupied(int x, int y) {
		return getActorAt(x, y) != null;
	}
	
	public Actor getActorAt(int x, int y) {
		for (Actor a : actors) {
			if (a.pos.x == x && a.pos.y == y) {
				return a;
			}
		}
		return null;
	}
	
	public void remove(Actor a){
		actors.remove(a);
	}
	
	public ArrayList<Actor> getActors(){
		return actors;
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
		for (Actor a : actors) {
			index = (width + 1) * a.pos.y + a.pos.x;
			sb.replace(index, index + 1, a.getChar());
		}
		for (DroppedItem i : items) {
			index = (width + 1) * i.pos.y + i.pos.x;
			sb.replace(index, index + 1, i.item.getChar());
		}
		return sb.toString();
	}
}
