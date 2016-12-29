package model;
import util.Roll;

public class Slime extends Actor {

	public Slime(int x, int y) {
		super(x, y);
	}

	@Override
	public String getChar() {
		return "s";
	}

	@Override
	public void act(Game game) {
		switch (Roll.d(8)) {
		case 1:
			if (game.isPassable(pos.x + 1, pos.y)) {
				pos.x++;
			}
			break;
		case 2:
			if (game.isPassable(pos.x - 1, pos.y)) {
				pos.x--;
			}
			break;
		case 3:
			if (game.isPassable(pos.x, pos.y + 1)) {
				pos.y++;
			}
			break;
		case 4:
			if (game.isPassable(pos.x, pos.y - 1)) {
				pos.y--;
			}
			break;
		}
	}
}
