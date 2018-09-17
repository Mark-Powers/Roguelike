package model.actors;

import java.awt.Color;
import java.awt.Graphics;

import gui.GUI;
import model.DamageType;
import model.Game;
import util.Point;

public class EyeBeam extends Actor {

	boolean timeToMove;
	public EyeBeam(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
		timeToMove = false;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		int xi = GUI.TILE_WIDTH / 3;
		int yi = GUI.TILE_HEIGHT / 3;
		g.fillOval(xi, yi, xi, yi);
	}

	@Override
	public void act(Game game) {
		Player p = game.getPlayer();

		if (Point.distance(p.pos, this.pos) <= 1) {
			p.takeDamage(this, getDamage(p, DamageType.RANGE));
			game.getCurrentFloor().getActors().remove(this);
		} else if(timeToMove){
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
		timeToMove = !timeToMove;
	}

	@Override
	public double getDamage(Actor a, DamageType type) {
		return 7;
	}

	@Override
	public String getName() {
		return "an eye beam";
	}

}
