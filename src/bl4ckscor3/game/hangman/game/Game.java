package bl4ckscor3.game.hangman.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

import org.apache.commons.io.FileUtils;

import bl4ckscor3.game.hangman.Hangman;
import bl4ckscor3.game.hangman.listener.MouseActionsListener;
import bl4ckscor3.game.hangman.util.Shapes;
import bl4ckscor3.game.hangman.util.Shapes.ShapeItem;
import bl4ckscor3.game.hangman.util.TextureManager;

/**
 * Contains the game's interface and part of the logic
 * @author bl4ckscor3
 */
public class Game extends JPanel
{
	private final int width = Hangman.getWidth(); //width of the panel
	private final int height = Hangman.getHeight(); //height of the panel
	private final Image close = TextureManager.loadTexture("close"); //the close button texture
	private final Image reset = TextureManager.loadTexture("reset"); //the reset button texture
	private final Shapes shapes = new Shapes(); //contains the clickable areas
	private final ArrayList<String> usedUpLetters = new ArrayList<String>(); //contains which letters have already been clicked
	private final Font fHeader = new Font("Calibri", 1, 30); //the font used for the header
	private final Font fLetters = new Font("Calibri", 1, 50); //the font used for the rest of the letters
	private final Font fEnd = new Font("Calibri", 1, 100); //the font used for the won/lost text
	private final Color bg = new Color(0x98AFC7); //the color of the background
	private GameThread thread; //the game thread this game is running on
	private int fps = 0; //the current fps
	private int hangman = 0; //the state of the hangman (error counter)
	private boolean loaded = true; //wether or not the word database has been loaded in
	private String error = ""; //if the word database is not loaded in, this contains the error message
	private List<String> words; //the word database
	private String currentWord; //the word to guess
	private char[] charArray; //contains the already guessed, and correct, letters in the order how they are in the word
	private boolean won = false; //wether or not the player has won
	private boolean lost = false; //wether or not the player has lost
	private boolean cheat = false; //wether or not to show the word to guess
	
	/**
	 * @param t The GameThread (loop) this game is running on
	 */
	public Game(GameThread t)
	{
		thread = t;
		addMouseListener(new MouseActionsListener());

		try
		{
			words = FileUtils.readLines(new File("resources/words.txt"));
			currentWord = words.get(new Random().nextInt(words.size()));
			charArray = new char[currentWord.length()];
		}
		catch(IOException e)
		{
			e.printStackTrace();
			loaded = false;
			error = e.getMessage();
		}
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Font fDef = g.getFont();
		Color cDef = g.getColor();

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
		g.setFont(fHeader);
		
		if(loaded && cheat)
			g.drawString(currentWord, width / 2 - centerTextHorizontally(currentWord, fHeader), 25);
		else
			g.drawString("Hangman v" + Hangman.version, width / 2 - centerTextHorizontally("Hangman v" + Hangman.version, fHeader), 25);
		
		drawImage(g, close, width - 32, 0, 32, 32, "Close");
		drawImage(g, reset, width - 64, 0, 32, 32, "Reset");
		
		//game content
		if(!loaded)
		{
			g.drawString("The word database could not be loaded.", width / 2 - centerTextHorizontally("The word database could not be loaded.", fHeader), height / 2);
			g.drawString(error, width / 2 - centerTextHorizontally(error, fHeader), height / 2 + 40);
			return;
		}

		g.setFont(fLetters);
		
		for(int i = 0; i < currentWord.length(); i++)
		{
			g.drawString("_", width / 3 + width / 10 + i * 40, height / 3); //underscores for the word to guess
		}
		
		if(lost)
		{
			g.setColor(Color.RED);
			g.setFont(fEnd);
			g.drawString("YOU LOST!", width / 2 - centerTextHorizontally("YOU LOST!", fEnd), height / 2);
			g.setColor(cDef);
			g.setFont(fHeader);
			g.drawString("The word was: " + currentWord, width / 2 - centerTextHorizontally("The word was: " + currentWord, fHeader), height / 2 + 40);
			g.setFont(fLetters);
		}
		else if(won)
		{
			g.setColor(Color.GREEN);
			g.setFont(fEnd);
			g.drawString("YOU WON!", width / 2 - centerTextHorizontally("YOU WON!", fEnd), height / 2);
			g.setColor(cDef);
			g.setFont(fLetters);
		}
		
		for(int i = 0; i < charArray.length; i++)
		{
			char c = charArray[i];
			int w = width / 3 + width / 10 + i * 40;
			
			//used for correct spacing on the underscores
			if(c == 'm' || c == 'w')
				w -= 5;
			else if(c == 'i' || c == 'l')
				w += 5;
			
			g.drawString("" + charArray[i], w, height / 3); //already guessed letters
		}
		
		g.drawImage(TextureManager.loadTextureFromPath("hangman-" + hangman, "hangman/"), width / 10, height / 10, 96 * 4, 128 * 4, null); //hangman picture
		int add = 0;

		for(int i = 1; i <= 26; i++)
		{
			char c = (char)(96 + i);

			//used for correct spacing of the chars
			if(c == 'g' || c == 's' || c == 't' || c == 'u')
				add -= 5;
			else if(c == 'j' || c == 'k')
				add -= 10;
			else if(c == 'm')
				add -= 10;
			else if(c == 'w')
				add -= 5;
			else if(c == 'n' || c == 'x')
				add += 10;
			
			if(usedUpLetters.contains(("" + c).toLowerCase()))
				g.setColor(bg);

			drawString(g, "" + c, width / 4 + i * 35 + add, height - height / 6, "" + c);
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
		character = character.toLowerCase();
		usedUpLetters.add(character);
		
		if(currentWord.contains(character))
		{
			char[] cA = currentWord.toCharArray();
			
			for(int i = 0; i < cA.length; i++)
			{
				if(character.equals("" + cA[i]))
					charArray[i] = cA[i];
			}

			if(Arrays.toString(charArray).replace("[", "").replace("]", "").replace(",", "").replace(" ", "").equals(currentWord))
				won = true;
		}
		else if(!lost)
		{
			hangman++;
			
			if(hangman == 11)
				lost = true;
		}
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
		won = false;
		lost = false;

		try
		{
			words = FileUtils.readLines(new File("resources/words.txt"));
			currentWord = words.get(new Random().nextInt(words.size()));
			charArray = new char[currentWord.length()];
		}
		catch(IOException e)
		{
			e.printStackTrace();
			loaded = false;
			error = e.getMessage();
		}
	}
	
	/**
	 * @return true if the player has won, false otherwise
	 */
	public boolean hasWon()
	{
		return won;
	}
	
	/**
	 * @return true if the player has lost, false otherwise
	 */
	public boolean hasLost()
	{
		return lost;
	}
	
	/**
	 * Calculates the position to start drawing a String at if it should be centered in the screen
	 * @param text The text to display
	 * @param f The font to display the text with
	 * @return An integer to be used as an x position parameter
	 */
	public int centerTextHorizontally(String text, Font f)
	{
		return getFontMetrics(f).stringWidth(text) / 2;
	}
}
