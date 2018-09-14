package model.items;

import java.awt.Graphics;

import util.Point;

public class DroppedItem {
	public Item item;
	public Point pos;

	public DroppedItem(Item i, Point p) {
		item = i;
		pos = p;
	}

	public void draw(Graphics g) {
		item.draw(g);
	}
}
