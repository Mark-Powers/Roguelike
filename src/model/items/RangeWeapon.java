package model.items;

public abstract class RangeWeapon {
	protected double accuracy;
	protected int range;
	protected int damage;
	protected String name;
	
	public String getName(){
		return name;
	}
	
	public int getDamage(){
		return damage;
	}
	
	public int getRange(){
		return range;
	}
	
	public double getAccuracy(){
		return accuracy;
	}
}
