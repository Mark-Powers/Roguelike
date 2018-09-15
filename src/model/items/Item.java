package model.items;

import java.awt.Graphics;

import model.Drawable;

public interface Item extends Drawable {
	public String getName();
	public void draw(Graphics g);
}
