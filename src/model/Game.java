package model;
import java.awt.RenderingHints.Key;
import java.util.ArrayList;
import java.util.List;

public class Game {
	final int WIDTH = 30;
	final int HEIGHT = 20;

	private Player player;
	private List<Actor> actors;

	private Map map;

	public Game() {
		player = new Player("Mark", 3, 3);
		map = new Map(WIDTH, HEIGHT);
		actors = new ArrayList<Actor>();
		actors.add(player);
		actors.add(new Slime(10, 10));
		actors.add(new Slime(15, 10));
	}

	public void movePlayerLeft() {
		if (isPassable(player.pos.x - 1, player.pos.y)) {
			player.pos.x--;
			Log.add(String.format("%s moved left.", player.getName()));
		}
	}

	public void movePlayerRight() {
		if (isPassable(player.pos.x + 1, player.pos.y)) {
			player.pos.x++;
			Log.add(String.format("%s moved right.", player.getName()));
		}
	}

	public void movePlayerUp() {
		if (isPassable(player.pos.x, player.pos.y-1)) {
			player.pos.y--;
			Log.add(String.format("%s moved up.", player.getName()));
		}
	}

	public void movePlayerDown() {
		if (isPassable(player.pos.x, player.pos.y+1)) {
			player.pos.y++;
			Log.add(String.format("%s moved down.", player.getName()));
		}
	}

	public boolean isPassable(int x, int y) {
		return map.isPassable(x, y);
	}

	public void tick() {
		for (Actor a : actors) {
			a.act(this);
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(map.toString());
		for (Actor a : actors) {
			int index = (WIDTH + 1) * a.pos.y + a.pos.x;
			sb.replace(index, index + 1, a.getChar());
		}
		return sb.toString();
	}
}
