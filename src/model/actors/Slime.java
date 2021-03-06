package model.actors;

import java.awt.Color;
import java.awt.Graphics;

import gui.GUI;
import model.DamageType;
import model.Direction;
import model.Game;
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
	public void act(Game game) {
		// 33% chance to move randomly
		// 33% chance to move last direction again
		// 33% chance to stay still
		int roll = Roll.d(12)-1;
		if (roll >= 8) {
			roll = lastRoll;
		}
		switch (roll) {
		case Direction.NORTH:
			if (game.canMove(this, pos.x, pos.y - 1)) {
				game.move(this, pos.x, pos.y - 1);
			}
			break;
		case Direction.EAST:
			if (game.canMove(this, pos.x + 1, pos.y)) {
				game.move(this, pos.x + 1, pos.y);
			}
			break;
		case Direction.SOUTH:
			if (game.canMove(this, pos.x, pos.y + 1)) {
				game.move(this, pos.x, pos.y + 1);
			}
			break;
		case Direction.WEST:
			if (game.canMove(this, pos.x - 1, pos.y)) {
				game.move(this, pos.x - 1, pos.y);
			}
			break;
		}
		lastRoll = roll;
	}

	@Override
	public String getName() {
		return "a slime";
	}

	@Override
	public double getDamage(Actor a, DamageType type) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(new Color(0, 255, 0));
		g.fillRect(0, 0, GUI.TILE_WIDTH, GUI.TILE_HEIGHT);
	}
}
