package model.actors;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import gui.EntryType;
import gui.GUI;
import model.DamageType;
import model.Game;
import model.Log;
import model.items.Item;
import model.items.MeleeWeapon;
import model.items.RangeWeapon;
import util.Roll;

public class Player extends Actor {
	private String name;

	private ArrayList<Item> inventory;

	public MeleeWeapon meleeWeapon;
	public RangeWeapon rangeWeapon;

	public Player(String name, int x, int y) {
		super(x, y);
		this.name = name;

		maxHealth = 20;
		currentHealth = maxHealth;

		inventory = new ArrayList<Item>();

		meleeWeapon = new MeleeWeapon("a sword", 0.9, 5);
		rangeWeapon = new RangeWeapon("a short bow", .7, 5, 3);
	}

	public String[] getInventory() {
		String[] items = new String[inventory.size()];
		for (int i = 0; i < inventory.size(); i++) {
			items[i] = inventory.get(i).getName();
		}
		return items;
	}
	
	public ArrayList<Item> getInventoryItems() {
		return new ArrayList<Item>(inventory);
	}

	public void addItem(Item i) {
		inventory.add(i);
	}

	public Item dropItem(String s) {
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).getName().equals(s)) {
				Log.add(String.format("You dropped %s.", s), EntryType.INFO);
				return inventory.remove(i);
			}
		}
		return null;
	}
	
	public Item dropItem(int s) {
		Log.add(String.format("You dropped %s.", inventory.get(s).getName()), EntryType.INFO);
		return inventory.remove(s);
	}

	public String getName() {
		return name;
	}

	@Override
	public void act(Game game) {
		// Player passive effects here
	}

	public boolean attack(Actor target) {
		if (Roll.d(100) <= meleeWeapon.getAccuracy() * 100) {
			target.currentHealth -= meleeWeapon.getDamage();
			target.takeDamage(this, getDamage(target, DamageType.MELEE));			
			Log.add(String.format("Dealt %d damage to %s", meleeWeapon.getDamage(), target.getName()));
			if (target.currentHealth <= 0) {
				return true;
			}
		} else {
			Log.add("Your attack misses");
		}
		return false;
	}

	public boolean rangeAttack(Actor target) {
		if (Roll.d(100) <= rangeWeapon.getAccuracy() * 100) {
			target.currentHealth -= rangeWeapon.getDamage();
			Log.add(String.format("Dealt %d damage to %s", rangeWeapon.getDamage(), target.getName()));
			if (target.currentHealth <= 0) {
				return true;
			}
		} else {
			Log.add("Your attack misses");
		}
		return false;
	}

	@Override
	public double getDamage(Actor a, DamageType type) {
		switch(type){
		case MELEE:
			return meleeWeapon.getDamage();
		case RANGE:
			return rangeWeapon.getDamage();
		default:
			return 0;
		}
	}

	@Override
	public void draw(Graphics g) {
		g.translate(GUI.TILE_WIDTH * pos.x, GUI.TILE_HEIGHT * pos.y);
		g.setColor(Color.RED);
		g.fillRect(0, 0, GUI.TILE_WIDTH, GUI.TILE_HEIGHT);
		g.translate(-GUI.TILE_WIDTH * pos.x, -GUI.TILE_HEIGHT * pos.y);
	}
}
