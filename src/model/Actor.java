package model;
import java.util.HashMap;
import java.util.Map;

import util.Point;

public abstract class Actor {
	public Point pos;
	protected Map<Stat, Double> stat;
	protected int maxHealth;
	protected int currentHealth;
	
	public Actor(int x, int y){
		pos = new Point(x, y);
		stat = new HashMap<Stat, Double>();
	}
	
	public abstract void act(Game game);
	public abstract boolean attack(Actor target);
	public abstract String getName();
	
	public abstract String getChar();
}
