package bl4ckscor3.game.hangman.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;

import bl4ckscor3.game.hangman.Hangman;
import bl4ckscor3.game.hangman.util.Shapes;
import bl4ckscor3.game.hangman.util.Shapes.ShapeItem;
import bl4ckscor3.game.hangman.util.TextureManager;
import bl4ckscor3.game.hangman.util.listener.MouseActionsListener;

public class Screen extends JPanel
{
	private GameThread thread;
	private final int width = Hangman.getWidth();
	private final int height = Hangman.getHeight();
	private int fps = 0;
	private final Image close = TextureManager.loadTexture("close");
	private final Shapes shapes = new Shapes();
	private final ArrayList<String> usedUpLetters = new ArrayList<String>();
	
	/**
	 * @param t The GameThread (loop) this game is running on
	 */
	public Screen(GameThread t)
	{
		thread = t;
		addMouseListener(new MouseActionsListener());
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Font fDef = g.getFont();
		Font font = new Font("Calibri", 1, 30);
		Font letters = new Font("Calibri", 1, 50);
		Color cDef = g.getColor();
		Color bg = new Color(0x98AFC7);
		
		//resetting the screen so there is no overlapping
		g.setColor(bg);
		g.fillRect(0, 0, width, height);
		g.setColor(cDef);
		//setting font
		//lines as seperators of the header
		g.drawLine(0, 32, width, 32);
		g.drawLine(60, 0, 60, 32);
		g.drawLine(width - 32, 0, width - 32, 32);
		//header content
		g.drawString("FPS: " + fps, 5, 21);
		g.setFont(font);
		g.drawString("Hangman v" + Hangman.version, width / 2 - g.getFontMetrics(font).stringWidth("Hangman v" + Hangman.version) / 2, 25);
		g.setFont(fDef);
		drawImage(g, close, width - 32, 0, 32, 32, "Close");
		g.setFont(letters);
		int add = 0;

		for(int i = 1; i <= 26; i++)
		{
			char c = (char)(64 + i);

			//used for correct spacing of the chars
			if(c == 'G')
				add -= 5;
			else if(c == 'J' || c == 'K')
				add -= 10;
			else if(c == 'N' || c == 'X')
				add += 15;
			else if(c == 'I' || c == 'R')
				add += 10;

			if(usedUpLetters.contains("" + c))
				g.setColor(bg);
			
			drawString(g, "" + c, width / 5 + i * 35 + add, height - height / 6, "" + c);
			g.setColor(cDef);
		}

		g.setFont(fDef);
	}

	/**
	 * Draws an image and adds it to the shapes list
	 * @param g The Graphics to draw with
	 * @param img The image to draw
	 * @param x The x position of the image
	 * @param y The y position of the image
	 * @param width The width of the image
	 * @param height The height of the image
	 * @param name The name of the image used to determine wether it has been clicked or not
	 */
	private void drawImage(Graphics g, Image img, int x, int y, int width, int height, String name)
	{
		g.drawImage(img, x, y, width, height, null);
		shapes.add(new ShapeItem(new Rectangle(x, y, width, height), name));
	}

	/**
	 * Draws a String and adds it to the shapes list
	 * @param g The Graphics to draw with
	 * @param str The String to draw
	 * @param x The x position of the String
	 * @param y The y position of the String
	 * @param name The name of the String used to determine wether it has been clicked or not
	 */
	private void drawString(Graphics g, String str, int x, int y, String name)
	{
		int fontSize = g.getFont().getSize();
		
		g.drawString(str, x, y);
		shapes.add(new ShapeItem(new Rectangle(x, y - fontSize, g.getFontMetrics(g.getFont()).charWidth(str.charAt(0)), fontSize), name));
	}

	/**
	 * Sets the fps in order to paint the value on the screen
	 * @param f The fps to set
	 */
	public void setFps(int f)
	{
		fps = f;
	}

	/**
	 * @return The GameThread (loop) this game is running on
	 */
	public GameThread getThread()
	{
		return thread;
	}

	/**
	 * @return The shapes on this screen
	 */
	public Shapes getShapes()
	{
		return shapes;
	}
	
	/**
	 * Disabled a character so it can't be clicked anymore
	 * @param character The character to disable (A-Z)
	 */
	public void useUp(String character)
	{
		if(!usedUpLetters.contains(character))
			usedUpLetters.add(character);
	}
	
	/**
	 * @return The letters that have already been clicked
	 */
	public ArrayList<String> getUsedUpLetters()
	{
		return usedUpLetters;
	}
}
