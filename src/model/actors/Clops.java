package model.actors;

import java.awt.Color;
import java.awt.Graphics;

import gui.GUI;
import model.DamageType;
import model.Game;
import util.Point;

public class Clops extends Actor {

	private boolean blink;

	public Clops(int x, int y) {
		super(x, y);
		blink = false;
	}

	@Override
	public void draw(Graphics g) {
		if (!blink) {
			g.setColor(Color.WHITE);
			g.fillOval(0, 0, GUI.TILE_WIDTH, GUI.TILE_HEIGHT);
			g.setColor(Color.BLACK);
			int xi = GUI.TILE_WIDTH / 3;
			int yi = GUI.TILE_HEIGHT / 3;
			g.fillOval(xi, yi, xi, yi);
		}
	}

	@Override
	public void act(Game game) {
		if (!blink) {
			Player p = game.getPlayer();
			if (Point.distance(p.pos, this.pos) <= 3) {
				blink = true;
				game.getCurrentFloor().addActor(new EyeBeam(pos.x, pos.y));;
			}
		} else {
			int x = (int) (Math.random() * game.WIDTH);
			int y = (int) (Math.random() * game.HEIGHT);
			while (!game.canMove(this, x, y)) {
				x = (int) (Math.random() * game.WIDTH);
				y = (int) (Math.random() * game.HEIGHT);
			}
			game.move(this, x, y);
			blink = false;
		}
	}

	@Override
	public double getDamage(Actor a, DamageType type) {
		return 0;
	}

	@Override
	public String getName() {
		return "clops";
	}

}
