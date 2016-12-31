package model;

import java.sql.Array;
import java.util.Arrays;

public class KeyBind {
	public static final int LEFT = 37;
	public static final int UP = 38;
	public static final int RIGHT = 39;
	public static final int DOWN = 40;
	
	public static final int ATTACK = 65; // a
	public static final int RANGE = 82; // r
	
	public static final int INVENTORY = 73; // i
	public static final int DROP_ITEM = 68; // d
	public static final int PICK_UP_ITEM = 80; // p
	
	public static final int[] NUMBER_KEY = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57};
	
	public static int getNumberKeyValue(int key){
		return Arrays.binarySearch(NUMBER_KEY, key);
	}
	
	public static final int CANCEL = 27; // esc
}
