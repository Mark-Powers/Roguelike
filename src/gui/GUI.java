package gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import model.Game;
import model.Log;
import model.LogEntry;

public class GUI extends Frame {
	public final static int TILE_WIDTH = 18;
	public final static int TILE_HEIGHT = 18;
	final int WIDTH = 800;
	final int HEIGHT = 680;
	final int FONT_SIZE = 20;
	final int LOG_COUNT = 10;

	private MyCanvas console;
	private Game game;

	public static void main(String[] args) {
		GUI g = new GUI();
	}

	public GUI() {
		game = new Game();

		setLayout(new BorderLayout());
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		setSize(WIDTH, HEIGHT);

		console = new MyCanvas(WIDTH, HEIGHT);
		console.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				// Required
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// Required
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (game.keyEvent(e.getKeyCode())) {
					game.tick();
				}
				console.repaint();
			}
		});
		add(console, BorderLayout.CENTER);

		setVisible(true);

	}

	class MyCanvas extends Canvas {
		int width;
		int height;

		public MyCanvas(int width, int height) {
			this.width = width;
			this.height = height;
			setSize(width, height);
			setBackground(new Color(224, 224, 224));
		}

		public void paint(Graphics g) {
			// Draws map
			game.draw(g);

			// Draws log
			g.setFont(new Font("FreeMono", Font.PLAIN, FONT_SIZE));
//			g.setColor(new Color(16,16,16));
			LogEntry[] entries = Log.getLastEntries(LOG_COUNT);
			for (int i = 0; i < entries.length; i++) {
				if (entries[i] != null) {
					EntryType.set(entries[i].entryType, g);
					System.out.println("Drawing log" + TILE_HEIGHT + " " + TILE_HEIGHT * (Game.HEIGHT + 1));
					g.drawString(entries[i].text, TILE_HEIGHT, TILE_HEIGHT * (Game.HEIGHT + 2 + i));
				}
			}

			// Log outline
			g.setColor(Color.BLACK);
			g.drawRect(TILE_WIDTH, TILE_HEIGHT * (Game.HEIGHT + 1), width - 30, FONT_SIZE * LOG_COUNT + 4);

		}
	}
}
