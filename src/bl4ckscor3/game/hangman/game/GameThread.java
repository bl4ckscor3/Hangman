package bl4ckscor3.game.hangman.game;

import bl4ckscor3.game.hangman.Hangman;

/**
 * The game thread used to update and render the screen
 * @author bl4ckscor3
 */
public class GameThread extends Thread implements Runnable
{
	@Override
	public void run()
	{
		int tick = 0;
		int fps = 0;
		int targetTps = 60;
		double fpsTimer = System.currentTimeMillis();
		double secondsPerTick = 1.0D / targetTps; //how long to wait between each update
		double nanosecondsPerTick = secondsPerTick * 1_000_000_000.0D;
		double then = System.nanoTime();
		double now = System.nanoTime();
		double unprocessed = 0;
		
		while(true)
		{
			now = System.nanoTime();
			unprocessed += (now - then) / nanosecondsPerTick;
			then = now;
			
			while(unprocessed >= 1)
			{
				tick++;
				unprocessed--;
			}
			
			try
			{
				Thread.sleep(1);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			Hangman.getScreen().repaint();
			fps++;
			
			if(System.currentTimeMillis() - fpsTimer >= 1000)
			{
				Hangman.getScreen().setFps(fps);
				Hangman.getScreen().setTick(tick);
				fps = 0;
				tick = 0;
				fpsTimer += 1000;
			}
		}
	}
}
