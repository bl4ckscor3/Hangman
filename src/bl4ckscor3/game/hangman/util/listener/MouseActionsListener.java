package bl4ckscor3.game.hangman.util.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import bl4ckscor3.game.hangman.Hangman;
import bl4ckscor3.game.hangman.game.Screen;
import bl4ckscor3.game.hangman.util.Shapes;

public class MouseActionsListener implements MouseListener
{
	@Override
	public void mouseClicked(MouseEvent e)
	{
		Shapes shapes = Hangman.getScreen().getShapes();
		String name = shapes.getContains(e.getPoint());
		
		if(name != null)
		{
			switch(name)
			{
				case "Close":
					System.exit(0);
					break;
				case "Reset":
					Hangman.getScreen().reset();
					break;
				case "A": case "B": case "C": case "D": case "E": case "F": case "G": case "H": case "I":
				case "J": case "K": case "L": case "M": case "N": case "O": case "P": case "Q": case "R":
				case "S": case "T": case "U": case "V": case "W": case "X": case "Y": case "Z":
					Screen s = Hangman.getScreen();
					
					if(!s.getUsedUpLetters().contains(name))
						s.useUp(name);
					
					break;
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e){}

	@Override
	public void mouseReleased(MouseEvent e){}

	@Override
	public void mouseEntered(MouseEvent e){}

	@Override
	public void mouseExited(MouseEvent e){}
}
