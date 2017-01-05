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

import javax.swing.text.JTextComponent.KeyBinding;

import model.Game;
import model.KeyBind;
import model.Log;
import model.LogEntry;

public class GUI extends Frame{
	final int WIDTH = 800;
	final int HEIGHT = 680;
	final int FONT_SIZE = 20;
	final int LOG_COUNT = 10;
	
	private MyCanvas console;
	private Game game;
	
	public static void main(String[] args) {
		GUI g = new GUI();
	}
	
	public GUI(){
		game = new Game();
		
		setLayout(new BorderLayout());
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		setSize(WIDTH,HEIGHT);
		
		
		console = new MyCanvas(WIDTH,HEIGHT);
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
				if(game.keyEvent(e.getKeyCode())){
					game.tick();
				}
				console.setText(game.toString());
			}
		});
		console.setText(game.toString());
		add(console, BorderLayout.CENTER);
		
		setVisible(true);
				 
	}
	
	class MyCanvas extends Canvas {
		int width;
		int height;
		String text;

		public MyCanvas(int width, int height) {
			this.width = width;
			this.height = height;
			setSize(width, height);
			setBackground(new Color(224,224,224));
			text = "";
		}

		public void setText(String s){
			text = s;
			repaint();
		}

		public void paint(Graphics g) {
			g.setFont(new Font("Courier New", Font.PLAIN, FONT_SIZE));
			g.setColor(new Color(16,16,16));
			//Draws map
			String[] lines = text.split("\n");
			for(int i = 0; i < lines.length; i++){
				g.drawString(lines[i], 10, 15+i*FONT_SIZE);
			}
			
			// Draws log
			LogEntry[] entries = Log.getLastEntries(LOG_COUNT);
			for(int i = 0; i < entries.length; i++){
				if(entries[i] != null){
					EntryType.set(entries[i].entryType, g);
					g.drawString(entries[i].text, 10, 30+lines.length*FONT_SIZE + i*FONT_SIZE);
				}
			}
			// Log outline
			g.setColor(Color.BLACK);
			g.drawRect(8, 10+lines.length*FONT_SIZE, width-30, FONT_SIZE*LOG_COUNT+4);
			
		}
	}
}
