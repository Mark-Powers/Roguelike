package model;

import java.util.ArrayList;
import java.util.List;

import util.Point;
import util.Roll;

public class Game {
	final int WIDTH = 60;
	final int HEIGHT = 20;
	final int startingX = 5;
	final int startingY = 5;

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

	public Actor getActorAt(int x, int y) {
		for (Actor a : actors) {
			if (a.pos.x == x && a.pos.y == y) {
				return a;
			}
		}
		return null;
	}

	public boolean canMoveToSpace(int x, int y) {
		return !isOccupied(x, y) && isPassable(x, y);
	}

	public void tick() {
		for (Actor a : actors) {
			a.act(this);
		}
	}

	public boolean keyEvent(int keycode) {
		boolean turnComplete = true;

		if (keycode == KeyBind.CANCEL) {
			Log.add("Cancelled");
			turnComplete = false;
		} else if (lastKey == KeyBind.RANGE) {
			Actor target = null;
			if (keycode == KeyBind.LEFT) {
				for (int i = player.pos.x - 1; i >= 0; i--) {
					if(map.isSolid(i, player.pos.y)){
						break;
					}
					target = getActorAt(i, player.pos.y);
					if (target != null) {
						break;
					}
				}
			} else if (keycode == KeyBind.RIGHT) {
				for (int i = player.pos.x + 1; i < WIDTH; i++) {
					if(map.isSolid(i, player.pos.y)){
						break;
					}
					target = getActorAt(i, player.pos.y);
					if (target != null) {
						break;
					}
				}
			} else if (keycode == KeyBind.UP) {
				for (int i = player.pos.y - 1; i >= 0; i--) {
					if(map.isSolid(player.pos.x, i)){
						break;
					}
					target = getActorAt(player.pos.x, i);
					if (target != null) {
						break;
					}
				}
			} else if (keycode == KeyBind.DOWN) {
				for (int i = player.pos.y + 1; i < HEIGHT; i++) {
					if(map.isSolid(player.pos.x, i)){
						break;
					}
					target = getActorAt(player.pos.x, i);
					if (target != null) {
						break;
					}
				}
			}
			if (target != null) {
				if (Math.abs(Point.distance(player.pos, target.pos)) <= player.rangeWeapon.getRange()) {
					boolean wasKilled = player.rangeAttack(target);
					if (wasKilled) {
						actors.remove(target);
						Log.add(String.format("%s shot %s with %s.", player.getName(), target.getName(),
								player.rangeWeapon.getName()));
					}
				} else {
					Log.add("Your target is too far away.");
					turnComplete = false;
				}
			} else {
				Log.add("No target");
				turnComplete = false;
			}
		} else if (lastKey == KeyBind.ATTACK) {
			Actor target = null;
			if (keycode == KeyBind.LEFT) {
				target = getActorAt(player.pos.x - 1, player.pos.y);
			} else if (keycode == KeyBind.RIGHT) {
				target = getActorAt(player.pos.x + 1, player.pos.y);
			} else if (keycode == KeyBind.UP) {
				target = getActorAt(player.pos.x, player.pos.y - 1);
			} else if (keycode == KeyBind.DOWN) {
				target = getActorAt(player.pos.x, player.pos.y + 1);
			}
			if (target != null) {
				boolean wasKilled = player.attack(target);
				if (wasKilled) {
					actors.remove(target);
					Log.add(String.format("%s killed %s with %s.", player.getName(), target.getName(),
							player.meleeWeapon.getName()));
				}
			} else {
				Log.add("No target");
				turnComplete = false;
			}
		} else {
			if (keycode == KeyBind.LEFT) {
				if (canMoveToSpace(player.pos.x - 1, player.pos.y)) {
					player.pos.x--;
				}
			} else if (keycode == KeyBind.RIGHT) {
				if (canMoveToSpace(player.pos.x + 1, player.pos.y)) {
					player.pos.x++;
				}
			} else if (keycode == KeyBind.UP) {
				if (canMoveToSpace(player.pos.x, player.pos.y - 1)) {
					player.pos.y--;
				}
			} else if (keycode == KeyBind.DOWN) {
				if (canMoveToSpace(player.pos.x, player.pos.y + 1)) {
					player.pos.y++;
				}
			} else if (keycode == KeyBind.ATTACK) {
				if (player.meleeWeapon != null) {
					Log.add("Choose a direction to attack");
					turnComplete = false;
				} else {
					Log.add("You have no melee weapon equipped!");
					lastKey = -1;
					return false;
				}
			} else if (keycode == KeyBind.RANGE) {
				if (player.rangeWeapon != null) {
					Log.add("Choose a direction to shoot");
					turnComplete = false;
				} else {
					Log.add("You have no ranged weapon equipped!");
					lastKey = -1;
					return false;
				}
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
