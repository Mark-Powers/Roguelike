package model.items;

import java.awt.Graphics;

import model.Drawable;
import util.Point;

public class DroppedItem implements Drawable {
	public Item item;
	public Point pos;

	public DroppedItem(Item i, Point p) {
		item = i;
		pos = p;
	}

	@Override
	public void draw(Graphics g) {
		item.draw(g);
	}
}
