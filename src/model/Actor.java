package model;
import java.util.HashMap;
import java.util.Map;

import util.Point;
import util.Roll;

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
	public abstract String getName();
	public abstract String getChar();
}
