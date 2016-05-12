package bl4ckscor3.game.hangman;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import bl4ckscor3.game.hangman.game.GameThread;
import bl4ckscor3.game.hangman.game.Screen;

/**
 * Main file used to set up and start the game
 * @author bl4ckscor3
 */
public class Hangman
{
	public static final String version = "0.1";
	private static Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	private static Screen screen;
	private static final JFrame frame = new JFrame();
	
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(() -> {
			start();
		});
	}
	
	/**
	 * Sets up and starts the game
	 */
	private static void start()
	{
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setSize(size);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		screen = new Screen(new GameThread());
		frame.add(screen);
		frame.setVisible(true);
		screen.getThread().start();
	}
	
	/**
	 * @return The Screen
	 */
	public static Screen getScreen()
	{
		return screen;
	}
	
	/**
	 * @return The width of the paintable screen
	 */
	public static int getWidth()
	{
		return size.width;
	}
	
	/**
	 * @return The height of the paintable screen
	 */
	public static int getHeight()
	{
		return size.height;
	}
	
	/**
	 * @return The window
	 */
	public static JFrame getFrame()
	{
		return frame;
	}
}
