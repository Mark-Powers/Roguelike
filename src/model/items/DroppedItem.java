package model.items;

import util.Point;

public class DroppedItem {
	public Item item;
	public Point pos;
	
	public DroppedItem(Item i, Point p){
		item = i;
		pos = p;
	}
}
