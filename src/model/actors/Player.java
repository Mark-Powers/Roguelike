package model.actors;

import java.util.ArrayList;
import java.util.HashMap;

import model.Game;
import model.Log;
import model.Stat;
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
		
		stat = new HashMap<Stat, Double>();
		stat.put(Stat.ATTACK, (double) Roll.stat());
		stat.put(Stat.CONSTITUTION, (double) Roll.stat());
		stat.put(Stat.DEFENSE, (double) Roll.stat());
		stat.put(Stat.DEXTERITY, (double) Roll.stat());
		
		maxHealth = 20;
		currentHealth = maxHealth;
		
		inventory = new ArrayList<Item>();
		
		meleeWeapon = new MeleeWeapon("a sword", 0.9, 5);
		rangeWeapon = new RangeWeapon("a short bow", .7, 5, 3);
	}
	
	public String[] getInventory(){
		String[] items = new String[inventory.size()];
		for(int i =0; i < inventory.size(); i++){
			items[i] = inventory.get(i).getName();
		}
		return items;
	}
	
	public void addItem(Item i){
		inventory.add(i);
	}
	
	public Item dropItem(String s){
		for(int i = 0; i < inventory.size(); i++){
			if(inventory.get(i).getName() == s){
				Log.add(String.format("You dropped %s.", s));
				return inventory.remove(i);
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	@Override
	public String getChar() {
		return "@";
	}

	@Override
	public void act(Game game) {
		// Player passive effects here
	}

	public boolean attack(Actor target) {
		if (Roll.d(100) <= meleeWeapon.getAccuracy() * 100) {
			target.currentHealth-=meleeWeapon.getDamage();
			Log.add(String.format("Dealt %d damage to %s", meleeWeapon.getDamage(), target.getName()));
			if(target.currentHealth <= 0){
				return true;
			}
		} else {
			Log.add("Your attack misses");
		}
		return false;

	}

	public boolean rangeAttack(Actor target) {
		if (Roll.d(100) <= rangeWeapon.getAccuracy() * 100) {
			target.currentHealth-=rangeWeapon.getDamage();
			Log.add(String.format("Dealt %d damage to %s", rangeWeapon.getDamage(), target.getName()));
			if(target.currentHealth <= 0){
				return true;
			}
		} else {
			Log.add("Your attack misses");
		}
		return false;
	}
}