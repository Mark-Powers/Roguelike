package model.actors;

import java.util.HashMap;

import gui.EntryType;
import model.DamageType;
import model.Drawable;
import model.Game;
import model.Log;
import model.Stat;
import util.Point;
import util.Roll;
import util.Utilities;

public abstract class Actor implements Drawable {
	public Point pos;
	protected HashMap<Stat, Double> stat;
	protected int maxHealth;
	protected int currentHealth;

	public Actor(int x, int y) {
		pos = new Point(x, y);
		stat = Roll.fullStats();
	}

	/**
	 * The actors behavior when the game ticks.
	 * 
	 * @param game
	 *            the current game state.
	 */
	public abstract void act(Game game);

	/**
	 * @param a
	 *            The actor that will be damaged.
	 * @return How much damage this Actor sends to the given actor.
	 */
	public abstract double getDamage(Actor a, DamageType type);
	
	/**
	 * Changes this Actor to take the given amount of damage
	 * 
	 * @param damage
	 * @return true if this Actor was killed.
	 */
	public boolean takeDamage(Actor a, double d) {
		int damage = (int) (d - stat.get(Stat.DEFENSE) / 50);
		Log.add(String.format("%s took %d damage from %s.", Utilities.capitalize(getName()), damage, a.getName()),
				EntryType.BAD);
		currentHealth -= damage;
		return false;
	}

	public int getMaxHealth() {
		return maxHealth;
	}
	
	public int getCurrentHealth() {
		return currentHealth;
	}
	
	/**
	 * @return a String that is whatever this is called.
	 */
	public abstract String getName();

		
}
