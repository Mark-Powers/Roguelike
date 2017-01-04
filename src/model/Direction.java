package model;

public class Direction {
	public final static int NORTH = 0;
	public final static int EAST = 1;
	public final static int SOUTH = 2;
	public final static int WEST = 3;
	
	public static int keyToDirection(int keyCode){
		switch(keyCode){
		case KeyBind.UP:
			return Direction.NORTH;
		case KeyBind.RIGHT:
			return Direction.EAST;
		case KeyBind.DOWN:
			return Direction.SOUTH;
		case KeyBind.LEFT:
			return Direction.WEST;
		}
		return -1;
	}
}
