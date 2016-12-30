package model;

/**
 * Inspired by the Pixel Dungeon Terrain class
 * https://github.com/watabou/pixel-dungeon
 */
public class Terrain {
	public static final int CHASM = 0;
	public static final int EMPTY = 1;
	public static final int WALL = 2;
	public static final int DOOR = 3;
	public static final int OPEN_DOOR = 4;

	public static final int PASSABLE = 0x01;
	public static final int AVOID = 0x02;
	public static final int PIT = 0x04;
	public static final int SOLID = 0x08;

	public static final int[] flags = new int[256];
	static {
		flags[CHASM] = AVOID | PIT;
		flags[EMPTY] = PASSABLE;
		flags[WALL] = SOLID;
		flags[DOOR] = PASSABLE | SOLID;
		flags[OPEN_DOOR] = PASSABLE;
	}
}
