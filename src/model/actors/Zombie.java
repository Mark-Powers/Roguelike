package model.actors;

import gui.EntryType;
import model.DamageType;
import model.Game;
import model.Log;
import util.Point;
import util.Roll;
import util.Utilities;

public class Zombie extends Actor {
	private final String[] names = { "a decaying corpse", "a zombie", "a rotting zombie" };
	
	String name;

	public Zombie(int x, int y) {
		super(x, y);

		name = randomName();

		maxHealth = 15;
		currentHealth = maxHealth;
	}

	@Override
	public void act(Game game) {
		Player p = game.getPlayer();

		if (Point.distance(p.pos, this.pos) <= 1) {
			
			p.takeDamage(this, getDamage(p, DamageType.MELEE));
			//Log.add(String.format("%s does 2 damage to you.", Utilities.capitalize(name)), EntryType.WARN);
		} else {
			if (p.pos.x < pos.x && game.canMove(this, pos.x - 1, pos.y)) {
				game.move(this, pos.x - 1, pos.y);
			} else if (p.pos.x > pos.x && game.canMove(this, pos.x + 1, pos.y)) {
				game.move(this, pos.x + 1, pos.y);
			} else if (p.pos.y < pos.y && game.canMove(this, pos.x, pos.y - 1)) {
				game.move(this, pos.x, pos.y - 1);
			} else if (p.pos.y > pos.y && game.canMove(this, pos.x, pos.y + 1)) {
				game.move(this, pos.x, pos.y + 1);
			}
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getChar() {
		return "z";
	}

	private String randomName() {
		return names[Roll.d(names.length) - 1];
	}

	@Override
	public double getDamage(Actor a, DamageType type) {
		return 3;
	}
}
