package model.items;

import java.awt.Color;
import java.awt.Graphics;

import gui.GUI;

public class RangeWeapon implements Item {
	protected double accuracy;
	protected int range;
	protected int damage;
	protected String name;

	public RangeWeapon(String n, double a, int r, int d) {
		name = n;
		accuracy = a;
		range = r;
		damage = d;
	}

	@Override
	public String getName() {
		return name;
	}

	public int getDamage() {
		return damage;
	}

	public int getRange() {
		return range;
	}

	public double getAccuracy() {
		return accuracy;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(new Color(139, 69, 19));
		g.fillArc(0, 0, GUI.TILE_WIDTH, GUI.TILE_HEIGHT, 90, 180);
	}
}
