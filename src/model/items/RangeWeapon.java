package model.items;

public class RangeWeapon implements Item {
	protected double accuracy;
	protected int range;
	protected int damage;
	protected String name;

	public RangeWeapon(String n, double a, int r, int d) {
		name = n;
		accuracy = a;
		range = r;
		damage = d;
	}

	@Override
	public String getName() {
		return name;
	}

	public int getDamage() {
		return damage;
	}

	public int getRange() {
		return range;
	}

	public double getAccuracy() {
		return accuracy;
	}
}
