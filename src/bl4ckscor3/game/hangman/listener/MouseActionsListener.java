package bl4ckscor3.game.hangman.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import bl4ckscor3.game.hangman.Hangman;
import bl4ckscor3.game.hangman.game.Game;
import bl4ckscor3.game.hangman.util.Shapes;

/**
 * Models what happens with specific mouse actions. Used for clicking
 * @author bl4ckscor3
 */
public class MouseActionsListener implements MouseListener
{
	@Override
	public void mouseClicked(MouseEvent e){}

	@Override
	public void mousePressed(MouseEvent e){}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		Shapes shapes = Hangman.getGame().getShapes();
		String name = shapes.getContains(e.getPoint());
		
		if(name != null)
		{
			switch(name)
			{
				case "Close":
					System.exit(0);
					break;
				case "Reset":
					Hangman.getGame().reset();
					break;
				case "Cheat":
					Hangman.getGame().toggleCheat();
					break;
				case "a": case "b": case "c": case "d": case "e": case "f": case "g": case "h": case "i":
				case "j": case "k": case "l": case "m": case "n": case "o": case "p": case "q": case "r":
				case "s": case "t": case "u": case "v": case "w": case "x": case "y": case "z":
					Game g = Hangman.getGame();

					if(!g.hasLost() && !g.hasWon())
					{
						if(!g.getUsedUpLetters().contains(name))
							g.useUp(name);
					}
					
					break;
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e){}

	@Override
	public void mouseExited(MouseEvent e){}
}
