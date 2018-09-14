package model.items;

import java.awt.Color;
import java.awt.Graphics;

import gui.GUI;

public class MeleeWeapon implements Item {
	protected double accuracy;
	protected int damage;
	protected String name;
	
	public MeleeWeapon(String n, double a, int d) {
		name = n;
		accuracy = a;
		damage =d;
	}
	
	@Override
	public String getName(){
		return name;
	}
	
	public int getDamage(){
		return damage;
	}
	
	public double getAccuracy(){
		return accuracy;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillRect(GUI.TILE_WIDTH/3, 0, GUI.TILE_WIDTH/3, GUI.TILE_HEIGHT);
	}
}
