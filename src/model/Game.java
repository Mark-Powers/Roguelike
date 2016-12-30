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
	private int lastKey;

	private Dungeon dungeon;
	private Floor currentFloor;

	public Game() {
		player = new Player("Mark", startingX, startingY);
		
		dungeon = new Dungeon(3);
		currentFloor = dungeon.getCurrentFloor();
		
		
		
		lastKey = -1;
	}

	public boolean isPassable(int x, int y) {
		return currentFloor.isPassable(x, y);
	}

	public void tick() {
		for (Actor a : currentFloor.getActors()) {
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
					if(currentFloor.isSolid(i, player.pos.y)){
						break;
					}
					target = currentFloor.getActorAt(i, player.pos.y);
					if (target != null) {
						break;
					}
				}
			} else if (keycode == KeyBind.RIGHT) {
				for (int i = player.pos.x + 1; i < WIDTH; i++) {
					if(currentFloor.isSolid(i, player.pos.y)){
						break;
					}
					target = currentFloor.getActorAt(i, player.pos.y);
					if (target != null) {
						break;
					}
				}
			} else if (keycode == KeyBind.UP) {
				for (int i = player.pos.y - 1; i >= 0; i--) {
					if(currentFloor.isSolid(player.pos.x, i)){
						break;
					}
					target = currentFloor.getActorAt(player.pos.x, i);
					if (target != null) {
						break;
					}
				}
			} else if (keycode == KeyBind.DOWN) {
				for (int i = player.pos.y + 1; i < HEIGHT; i++) {
					if(currentFloor.isSolid(player.pos.x, i)){
						break;
					}
					target = currentFloor.getActorAt(player.pos.x, i);
					if (target != null) {
						break;
					}
				}
			}
			if (target != null) {
				if (Math.abs(Point.distance(player.pos, target.pos)) <= player.rangeWeapon.getRange()) {
					boolean wasKilled = player.rangeAttack(target);
					if (wasKilled) {
						currentFloor.remove(target);
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
				target = currentFloor.getActorAt(player.pos.x - 1, player.pos.y);
			} else if (keycode == KeyBind.RIGHT) {
				target = currentFloor.getActorAt(player.pos.x + 1, player.pos.y);
			} else if (keycode == KeyBind.UP) {
				target = currentFloor.getActorAt(player.pos.x, player.pos.y - 1);
			} else if (keycode == KeyBind.DOWN) {
				target = currentFloor.getActorAt(player.pos.x, player.pos.y + 1);
			}
			if (target != null) {
				boolean wasKilled = player.attack(target);
				if (wasKilled) {
					currentFloor.remove(target);
					Log.add(String.format("%s killed %s with %s.", player.getName(), target.getName(),
							player.meleeWeapon.getName()));
				}
			} else {
				Log.add("No target");
				turnComplete = false;
			}
		} else {
			if (keycode == KeyBind.LEFT) {
				move(player, player.pos.x - 1, player.pos.y);
			} else if (keycode == KeyBind.RIGHT) {
				move(player, player.pos.x + 1, player.pos.y);
			} else if (keycode == KeyBind.UP) {
				move(player, player.pos.x, player.pos.y - 1);
			} else if (keycode == KeyBind.DOWN) {
				move(player, player.pos.x, player.pos.y + 1);
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
	
	public boolean move(Actor a, int x, int y){
		if(currentFloor.canMoveToSpace(x, y)){
			a.pos.x = x;
			a.pos.y = y;
			currentFloor.move(x, y);
			if(currentFloor.getTile(x, y) == Terrain.STAIR_DOWN){
				if(dungeon.nextFloor()){
					currentFloor = dungeon.getCurrentFloor();
					Log.add(String.format("You move down to floor %d", dungeon.getCurrentFloorNumber()));
				}
			} else if(currentFloor.getTile(x, y) == Terrain.STAIR_UP){
				if(dungeon.prevFloor()){
					currentFloor = dungeon.getCurrentFloor();
					Log.add(String.format("You move up to floor %d", dungeon.getCurrentFloorNumber()));
				}
			}
			return true;
		}
		return false;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(currentFloor.toString());
		
		int index = (WIDTH + 1) * player.pos.y + player.pos.x;
		sb.replace(index, index + 1, player.getChar());
		
		return sb.toString();
	}
}
