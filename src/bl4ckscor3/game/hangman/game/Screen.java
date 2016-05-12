package bl4ckscor3.game.hangman.game;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.JPanel;

import bl4ckscor3.game.hangman.Hangman;
import bl4ckscor3.game.hangman.util.Shapes;
import bl4ckscor3.game.hangman.util.TextureManager;
import bl4ckscor3.game.hangman.util.listener.MouseListener;
import bl4ckscor3.game.hangman.util.Shapes.ShapeItem;

public class Screen extends JPanel
{
	private GameThread thread;
	private final int width = Hangman.getWidth();
	private final int height = Hangman.getHeight();
	private int fps = 0;
	private int tick = 0;
	private final Image close = TextureManager.loadTexture("close");
	private final Shapes shapes = new Shapes();
	
	/**
	 * @param t The GameThread (loop) this game is running on
	 */
	public Screen(GameThread t)
	{
		thread = t;
		addMouseListener(new MouseListener());
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		Font prev = g.getFont();
		Font font = new Font("Calibri", 1, 30);
		
		//clearing the screen so there is no overlapping
		g.clearRect(0, 0, width, height);
		//setting font
		//lines as seperators of the header
		g.drawLine(0, 32, width, 32);
		g.drawLine(60, 0, 60, 32);
		g.drawLine(width - 32, 0, width - 32, 32);
		//header content
		g.drawString("FPS: " + fps, 5, 15);
		g.drawString("Tick: " + tick, 5, 30);
		g.setFont(font);
		g.drawString("Hangman v" + Hangman.version, width / 2 - g.getFontMetrics(font).stringWidth("Hangman v" + Hangman.version) / 2, 25);
		g.setFont(prev);
		drawImage(g, close, width - 32, 0, 32, 32, "Close");
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
	 * Sets the fps in order to paint the value on the screen
	 * @param f The fps to set
	 */
	public void setFps(int f)
	{
		fps = f;
	}
	
	/**
	 * Sets the tick in order to paint the value on the screen
	 * @param f The tick to set
	 */
	public void setTick(int t)
	{
		tick = t;
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
}
