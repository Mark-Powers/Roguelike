package model;
import util.Roll;

public class Slime extends Actor {
	private int lastRoll;
	
	public Slime(int x, int y) {
		super(x, y);
		lastRoll = -1;
		
		maxHealth = 10;
		currentHealth = maxHealth;
	}

	@Override
	public String getChar() {
		return "s";
	}

	@Override
	public void act(Game game) {
		// 33% chance to move randomly
		// 33% chance to move last direction again
		// 33% chance to stay still
		int roll = Roll.d(12);
		if(roll > 8){
			roll = lastRoll;
		}
		switch (roll) {
			case 1:
				game.move(this, pos.x + 1, pos.y);
				break;
			case 2:
				game.move(this, pos.x - 1, pos.y);
				break;
			case 3:
				game.move(this, pos.x, pos.y + 1);
				break;
			case 4:
				game.move(this, pos.x, pos.y - 1);
				break;
		}
		lastRoll = roll;
	}

	@Override
	public String getName() {
		return "a slime";
	}
}
