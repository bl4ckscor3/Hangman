package bl4ckscor3.game.hangman.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

import org.apache.commons.io.FileUtils;

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
	private final Image reset = TextureManager.loadTexture("reset");
	private final Shapes shapes = new Shapes();
	private final ArrayList<String> usedUpLetters = new ArrayList<String>();
	private int hangman = 0;		
	private boolean loaded = true;
	private String error = "";
	private List<String> words;
	private String currentWord;
	
	/**
	 * @param t The GameThread (loop) this game is running on
	 */
	public Screen(GameThread t)
	{
		thread = t;
		addMouseListener(new MouseActionsListener());

		try
		{
			words = FileUtils.readLines(new File("resources/words.txt"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
			loaded = false;
			error = e.getMessage();
		}
		
		currentWord = words.get(new Random().nextInt(words.size()));
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Font fDef = g.getFont();
		Font header = new Font("Calibri", 1, 30);
		Font letters = new Font("Calibri", 1, 50);
		Color cDef = g.getColor();
		Color bg = new Color(0x98AFC7);

		//resetting the screen so there is no overlapping
		g.setColor(bg);
		g.fillRect(0, 0, width, height);
		g.setColor(cDef);
		//setting font
		//lines as seperators of the header
		g.drawLine(0, 32, width, 32); //horizontal line across the screen
		g.drawLine(60, 0, 60, 32); //line right to fps
		g.drawLine(width - 32, 0, width - 32, 32); //line left to close
		g.drawLine(width - 64, 0, width - 64, 32); //line left to reset
		//header content
		g.drawString("FPS: " + fps, 5, 21);
		g.setFont(header);
		g.drawString("Hangman v" + Hangman.version, width / 2 - g.getFontMetrics(header).stringWidth("Hangman v" + Hangman.version) / 2, 25);
		drawImage(g, close, width - 32, 0, 32, 32, "Close");
		drawImage(g, reset, width - 64, 0, 32, 32, "Reset");
		//game content
		if(!loaded)
		{
			g.drawString("The word database could not be loaded.", width / 2 - getFontMetrics(header).stringWidth("The word database could not be loaded.") / 2, height / 2);
			g.drawString(error, width / 2 - getFontMetrics(header).stringWidth(error) / 2, height / 2 + 40);
			return;
		}

		g.drawImage(TextureManager.loadTextureFromPath("hangman-" + hangman, "hangman/"), width / 10, height / 10, 96 * 4, 128 * 4, null);
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
		hangman++;

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

	/**
	 * Resets the game so it can be started again
	 */
	public void reset()
	{
		shapes.clear();
		usedUpLetters.clear();
		hangman = 0;
		loaded = true;
		error = "";

		try
		{
			words = FileUtils.readLines(new File("resources/words.txt"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
			loaded = false;
			error = e.getMessage();
		}
		
		currentWord = words.get(new Random().nextInt(words.size()));
	}
}
