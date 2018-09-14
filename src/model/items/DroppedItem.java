package model.items;

import java.awt.Color;
import java.awt.Graphics;

import gui.GUI;
import util.Point;

public class DroppedItem {
	public Item item;
	public Point pos;
	
	public DroppedItem(Item i, Point p){
		item = i;
		pos = p;
	}

	public void draw(Graphics g) {
		g.setColor(Color.MAGENTA);
		g.fillOval(0, 0, GUI.TILE_WIDTH, GUI.TILE_HEIGHT);
	}
}
