package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class EntryType {
	public static final int NORMAL = 0;
	public static final int WARN = 1;
	public static final int INFO = 2;
	public static final int BAD = 3;
	public static final int GOOD = 4;

	public static void set(int entryType, Graphics g){
		Font oldFont = g.getFont();
		switch(entryType){
		case NORMAL:
			g.setColor(Color.BLACK);
			g.setFont(oldFont.deriveFont(Font.PLAIN));
			break;
		case WARN:
			g.setColor(Color.YELLOW);
			g.setFont(oldFont.deriveFont(Font.ITALIC));
			break;
		case INFO:
			g.setColor(Color.BLACK);
			g.setFont(oldFont.deriveFont(Font.ITALIC));
			break;
		case BAD:
			g.setColor(Color.RED);
			g.setFont(oldFont.deriveFont(Font.BOLD));
			break;
		case GOOD:
			g.setColor(Color.GREEN);
			g.setFont(oldFont.deriveFont(Font.PLAIN));
			break;
		}
	}
}
