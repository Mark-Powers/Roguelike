package model.items;

import java.awt.Color;
import java.awt.Graphics;

import gui.GUI;

public class Potion implements Item {

	@Override
	public String getName() {
		return "a useless potion";
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(GUI.TILE_WIDTH / 3, 0, GUI.TILE_WIDTH / 3, GUI.TILE_HEIGHT);
		g.fillOval(0, GUI.TILE_WIDTH / 2, GUI.TILE_WIDTH, GUI.TILE_HEIGHT / 2);
	}

}
