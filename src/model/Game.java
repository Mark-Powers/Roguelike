package model;

import java.util.ArrayList;
import java.util.List;

public class Game {
	final int WIDTH = 60;
	final int HEIGHT = 20;
	final int startingX = 3;
	final int startingY = 3;

	private Player player;
	private List<Actor> actors;
	private int lastKey;

	private Map map;

	public Game() {
		player = new Player("Mark", startingX, startingY);
		map = new Map(WIDTH, HEIGHT, startingX, startingY);
		actors = new ArrayList<Actor>();
		actors.add(player);
		actors.add(new Slime(10, 10));
		actors.add(new Slime(15, 10));
		lastKey = -1;
	}
	
	public boolean isPassable(int x, int y) {
		return map.isPassable(x, y);
	}

	public boolean isOccupied(int x, int y) {
		return getActorAt(x, y) != null;
	}

	public Actor getActorAt(int x, int y){
		for (Actor a : actors) {
			if (a.pos.x == x && a.pos.y == y) {
				return a;
			}
		}
		return null;
	}
	
	public boolean canMoveToSpace(int x, int y){
		return !isOccupied(x, y) && isPassable(x, y);
	}
	
	public void tick() {
		for (Actor a : actors) {
			a.act(this);
		}
	}

	public boolean keyEvent(int keycode) {
		boolean turnComplete = true;
		
		if(lastKey == KeyBind.ATTACK){
			Actor target = null;
			if (keycode == KeyBind.LEFT) {
				target = getActorAt(player.pos.x-1, player.pos.y);
			} else if (keycode == KeyBind.RIGHT) {
				target = getActorAt(player.pos.x+1, player.pos.y);
			} else if (keycode == KeyBind.UP) {
				target = getActorAt(player.pos.x, player.pos.y-1);
			} else if (keycode == KeyBind.DOWN) {
				target = getActorAt(player.pos.x, player.pos.y+1);
			}
			if(target != null){
				boolean wasKilled = player.attack(target);
				if(wasKilled){
					actors.remove(target);
					Log.add(String.format("%s killed %s.", player.getName(), target.getName()));
				}
			} else {
				Log.add("No target");
				turnComplete = false;
			}
		} else {
			if (keycode == KeyBind.LEFT) {
				if(canMoveToSpace(player.pos.x-1, player.pos.y)){
					player.pos.x--;
				}
			} else if (keycode == KeyBind.RIGHT) {
				if(canMoveToSpace(player.pos.x+1, player.pos.y)){
					player.pos.x++;
				}
			} else if (keycode == KeyBind.UP) {
				if(canMoveToSpace(player.pos.x, player.pos.y-1)){
					player.pos.y--;
				}
			} else if (keycode == KeyBind.DOWN) {
				if(canMoveToSpace(player.pos.x, player.pos.y+1)){
					player.pos.y++;
				}
			} else if (keycode == KeyBind.ATTACK) {
				Log.add("Choose a direction to attack");
				turnComplete = false;
			} else if (keycode == KeyBind.CANCEL) {
				Log.add("Cancelled");
				turnComplete = false;
			}
		}
		lastKey = keycode;
		return turnComplete;
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
