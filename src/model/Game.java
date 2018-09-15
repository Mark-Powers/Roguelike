package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import gui.EntryType;
import gui.GUI;
import model.actors.Actor;
import model.actors.Player;
import model.items.DroppedItem;
import model.items.Item;
import model.items.MeleeWeapon;
import model.items.Potion;
import util.Point;
import util.Utilities;

public class Game {
	public final static int WIDTH = 30;
	public final static int HEIGHT = 20;
	final int startingX = 5;
	final int startingY = 5;

	private Player player;
	private int lastKey;

	private Dungeon dungeon;
	private Floor currentFloor;

	public Game() {
		player = new Player("Mark", startingX, startingY);
		player.addItem(new Potion());
		player.addItem(new Potion());
		player.addItem(new MeleeWeapon("a rusty dagger", 0.2, 2));

		dungeon = new Dungeon(3, WIDTH, HEIGHT);
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
			lastKey = -1;
			return rangeAttack(keycode);
		} else if (lastKey == KeyBind.ATTACK) {
			lastKey = -1;
			return attack(keycode);
		} else if (lastKey == KeyBind.DROP_ITEM) {
			lastKey = -1;
			return dropItem(keycode);
		} else if (lastKey == KeyBind.LOOK) {
			lastKey = -1;
			inspect(Direction.keyToDirection(keycode));
			return false;
		} else {
			switch (keycode) {
			case KeyBind.LEFT:
				if (canMove(player, player.pos.x - 1, player.pos.y)) {
					move(player, player.pos.x - 1, player.pos.y);
					checkForStairs();
				}
				break;
			case KeyBind.RIGHT:
				if (canMove(player, player.pos.x + 1, player.pos.y)) {
					move(player, player.pos.x + 1, player.pos.y);
					checkForStairs();
				}
				break;
			case KeyBind.UP:
				if (canMove(player, player.pos.x, player.pos.y - 1)) {
					move(player, player.pos.x, player.pos.y - 1);
					checkForStairs();
				}
				break;
			case KeyBind.DOWN:
				if (canMove(player, player.pos.x, player.pos.y + 1)) {
					move(player, player.pos.x, player.pos.y + 1);
					checkForStairs();
				}
				break;
			case KeyBind.ATTACK:
				if (player.meleeWeapon != null) {
					Log.add("Choose a direction to attack");
					turnComplete = false;
				} else {
					Log.add("You have no melee weapon equipped!");
					lastKey = -1;
					return false;
				}
				break;
			case KeyBind.RANGE:
				if (player.rangeWeapon != null) {
					Log.add("Choose a direction to shoot");
					turnComplete = false;
				} else {
					Log.add("You have no ranged weapon equipped!");
					lastKey = -1;
					return false;
				}
				break;
			case KeyBind.INVENTORY:
				logInventory();
				turnComplete = false;
				break;
			case KeyBind.DROP_ITEM:
				Log.add("Choose item to drop");
				turnComplete = false;
				break;
			case KeyBind.PICK_UP_ITEM:
				DroppedItem pickedUpItem = currentFloor.removeItem(player.pos.x, player.pos.y);
				if (pickedUpItem != null) {
					Log.add(String.format("Picked up a %s.", pickedUpItem.item.getName()), EntryType.INFO);
					player.addItem(pickedUpItem.item);
				} else {
					Log.add("Nothing to pick up.");
					turnComplete = false;
				}
				break;
			case KeyBind.LOOK:
				Item atFeet = currentFloor.getItemAt(player.pos.x, player.pos.y);
				if (atFeet != null) {
					Log.add(String.format("At your feet lies %s.", atFeet.getName()));
				}
				Log.add("Choose a direction to look");
				turnComplete = false;
				break;
			}
		}
		lastKey = keycode;
		return turnComplete;
	}

	private boolean dropItem(int keycode) {
		int index = KeyBind.getNumberKeyValue(keycode) - 1; // - 1 since index
															// starts at 1
		String[] inventory = player.getInventory();
		if (index >= 0 && index < inventory.length) {
			Item dropped = player.dropItem(inventory[index]);
			currentFloor.addItem(new DroppedItem(dropped, new Point(player.pos.x, player.pos.y)));
			return true;
		}
		Log.add("Invalid item number");
		return false;
	}

	private void logInventory() {
		String[] items = player.getInventory();
		for (int i = items.length - 1; i >= 0; i--) {
			Log.add(String.format("%d: %s", i + 1, Utilities.capitalize(items[i])));
		}
		if (items.length == 0) {
			Log.add("Your inventory is empty.");
		}
	}

	private void inspect(int direction) {
		switch (direction) {
		case Direction.NORTH:
			for (int i = player.pos.y - 1; i >= 0; i--) {
				if (look(player.pos.x, i)) {
					return;
				}
				if (currentFloor.isSolid(player.pos.x, i)) {
					break;
				}
			}
			break;
		case Direction.EAST:
			for (int i = player.pos.x + 1; i < WIDTH; i++) {
				if (look(i, player.pos.y)) {
					return;
				}
				if (currentFloor.isSolid(i, player.pos.y)) {
					break;
				}
			}
			break;
		case Direction.SOUTH:
			for (int i = player.pos.y + 1; i < WIDTH; i++) {
				if (look(player.pos.x, i)) {
					return;
				}
				if (currentFloor.isSolid(player.pos.x, i)) {
					break;
				}
			}
			break;
		case Direction.WEST:
			for (int i = player.pos.x - 1; i >= 0; i--) {
				if (look(i, player.pos.y)) {
					return;
				}
				if (currentFloor.isSolid(i, player.pos.y)) {
					break;
				}
			}
			break;
		}
		Log.add("You find see nothing interesting.", EntryType.INFO);
	}

	/**
	 * Looks at tile (x, y) and logs if something is there.
	 * 
	 * @param x
	 * @param y
	 * @return true if something was found
	 */
	private boolean look(int x, int y) {
		Actor actorInSight = currentFloor.getActorAt(x, y);
		if (actorInSight != null) {
			Log.add(String.format("You see %s.", actorInSight.getName()), EntryType.INFO);
			return true;
		}
		Item itemInSight = currentFloor.getItemAt(x, y);
		if (itemInSight != null) {
			Log.add(String.format("You see %s.", itemInSight.getName()), EntryType.INFO);
			return true;
		}
		return false;
	}

	private boolean rangeAttack(int keycode) {
		boolean turnComplete = true;
		Actor target = null;
		if (keycode == KeyBind.LEFT) {
			for (int i = player.pos.x - 1; i >= 0; i--) {
				if (currentFloor.isSolid(i, player.pos.y)) {
					break;
				}
				target = currentFloor.getActorAt(i, player.pos.y);
				if (target != null) {
					break;
				}
			}
		} else if (keycode == KeyBind.RIGHT) {
			for (int i = player.pos.x + 1; i < WIDTH; i++) {
				if (currentFloor.isSolid(i, player.pos.y)) {
					break;
				}
				target = currentFloor.getActorAt(i, player.pos.y);
				if (target != null) {
					break;
				}
			}
		} else if (keycode == KeyBind.UP) {
			for (int i = player.pos.y - 1; i >= 0; i--) {
				if (currentFloor.isSolid(player.pos.x, i)) {
					break;
				}
				target = currentFloor.getActorAt(player.pos.x, i);
				if (target != null) {
					break;
				}
			}
		} else if (keycode == KeyBind.DOWN) {
			for (int i = player.pos.y + 1; i < HEIGHT; i++) {
				if (currentFloor.isSolid(player.pos.x, i)) {
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
		return turnComplete;
	}

	private boolean attack(int keycode) {
		boolean turnComplete = true;
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
		return turnComplete;
	}

	public boolean canMove(Actor a, int x, int y) {
		if (currentFloor.canMoveToSpace(x, y) && !(x == player.pos.x && y == player.pos.y)) {
			return true;
		}
		return false;
	}

	public boolean move(Actor a, int x, int y) {
		a.pos.x = x;
		a.pos.y = y;

		currentFloor.move(x, y);

		return true;
	}

	public void checkForStairs() {
		if (currentFloor.getTile(player.pos.x, player.pos.y) == Terrain.STAIR_DOWN) {
			if (dungeon.nextFloor()) {
				currentFloor = dungeon.getCurrentFloor();
				Log.add(String.format("You move down to floor %d", dungeon.getCurrentFloorNumber()));
			}
		} else if (currentFloor.getTile(player.pos.x, player.pos.y) == Terrain.STAIR_UP) {
			if (dungeon.prevFloor()) {
				currentFloor = dungeon.getCurrentFloor();
				Log.add(String.format("You move up to floor %d", dungeon.getCurrentFloorNumber()));
			}
		}

	}

	public Player getPlayer() {
		return player;
	}

	public void draw(Graphics g) {
		currentFloor.draw(g);
		player.draw(g);

		// Draw HUD
		int hudX = GUI.TILE_WIDTH * (Game.WIDTH + 1);
		int hudY = 0;
		g.setColor(Color.GRAY);
		// TOP
		g.fillRect(hudX, hudY, GUI.WIDTH - (GUI.TILE_WIDTH * (WIDTH + 1)), GUI.TILE_HEIGHT);
		// BOTTOM
		g.fillRect(hudX, hudY + GUI.TILE_HEIGHT * (HEIGHT - 1), GUI.WIDTH - (GUI.TILE_WIDTH * (WIDTH + 1)),
				GUI.TILE_HEIGHT);
		// RIGHT
		g.fillRect(hudX + GUI.WIDTH - (GUI.TILE_WIDTH * (WIDTH + 2)), hudY, GUI.TILE_WIDTH, GUI.TILE_HEIGHT * HEIGHT);
		// LEFT
		g.fillRect(hudX, hudY, GUI.TILE_WIDTH, GUI.TILE_HEIGHT * HEIGHT);

		hudX += GUI.TILE_WIDTH + 1;
		hudY += 2 * GUI.TILE_HEIGHT;
		g.setFont(new Font("FreeMono", Font.PLAIN, GUI.TILE_HEIGHT));
		g.drawString(player.getName(), hudX, hudY);
		hudY += GUI.TILE_HEIGHT;
		g.drawString("HP: " + player.getCurrentHealth() + "/" + player.getMaxHealth(), hudX, hudY);

		hudY += GUI.TILE_HEIGHT;
		g.drawString("Equipment:", hudX, hudY);
		// Melee
		g.translate(hudX, hudY);
		player.meleeWeapon.draw(g);
		g.translate(-(hudX), -hudY);
		g.setColor(Color.GRAY);
		hudY += GUI.TILE_HEIGHT;
		g.drawString(player.meleeWeapon.getName(), hudX + GUI.TILE_WIDTH, hudY);
		hudY += GUI.TILE_HEIGHT;
		g.drawString(player.meleeWeapon.getDamage() + "/" + (player.meleeWeapon.getAccuracy()*100) + "%", hudX + GUI.TILE_WIDTH,
				hudY);
		// Range
		g.translate(hudX, hudY);
		player.rangeWeapon.draw(g);
		g.translate(-(hudX), -hudY);
		g.setColor(Color.GRAY);
		hudY += GUI.TILE_HEIGHT;
		g.drawString(player.rangeWeapon.getName(), hudX + GUI.TILE_WIDTH, hudY);
		hudY += GUI.TILE_HEIGHT;
		g.drawString(player.rangeWeapon.getDamage() + "/" + (player.rangeWeapon.getAccuracy()*100) + "%" + "/"
				+ player.rangeWeapon.getRange(), hudX + GUI.TILE_WIDTH, hudY);

		hudY += GUI.TILE_HEIGHT;
		g.drawString("Inventory:", hudX, hudY);
		int index = 1;
		for (Item i : player.getInventoryItems()) {
			g.translate(hudX + GUI.TILE_WIDTH, hudY);
			i.draw(g);
			g.translate(-(hudX + GUI.TILE_WIDTH), -hudY);
			g.setColor(Color.GRAY);
			hudY += GUI.TILE_HEIGHT;
			g.drawString(index++ + ":", hudX, hudY);
			g.drawString(i.getName(), hudX + GUI.TILE_WIDTH * 2, hudY);
		}
	}
}
