package model;
import java.util.HashMap;
import java.util.Map;

import util.Roll;

public class Player extends Actor {
	private int x;
	private int y;
	private String name;
	private Map<Stat, Double> stat;
	
	public Player(String name, int x, int y){
		super(x, y);
		this.name = name;
		
		stat = new HashMap<Stat, Double>();
		stat.put(Stat.ATTACK, (double) Roll.stat());
		stat.put(Stat.CONSTITUTION, (double) Roll.stat());
		stat.put(Stat.DEFENSE, (double) Roll.stat());
		stat.put(Stat.DEXTERITY, (double) Roll.stat());
	}

	public String getName(){
		return name;
	}
	
	@Override
	public String getChar() {
		return "@";
	}

	@Override
	public void act(Game game) {
		// TODO Auto-generated method stub
		
	}
}
