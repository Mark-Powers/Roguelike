package model.items;

public abstract class MeleeWeapon {
	protected double accuracy;
	protected int damage;
	protected String name;
	
	public String getName(){
		return name;
	}
	
	public int getDamage(){
		return damage;
	}
	
	public double getAccuracy(){
		return accuracy;
	}
}
