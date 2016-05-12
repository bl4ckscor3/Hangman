package bl4ckscor3.game.hangman.util.listener;

import java.awt.event.MouseEvent;

import bl4ckscor3.game.hangman.Hangman;
import bl4ckscor3.game.hangman.util.Shapes;

public class MouseListener implements java.awt.event.MouseListener
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
