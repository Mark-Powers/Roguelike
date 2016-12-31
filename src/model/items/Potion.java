package model.items;

public class Potion implements Item {

	@Override
	public String getName() {
		return "a useless potion";
	}

	@Override
	public String getChar() {
		return "P";
	}

}
