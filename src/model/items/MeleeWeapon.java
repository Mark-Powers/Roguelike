package model.items;

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
}
