package bl4ckscor3.game.hangman.util;

import java.awt.Point;
import java.awt.Shape;
import java.util.ArrayList;

/**
 * Handles clickable shapes
 * @author bl4ckscor3
 */
public class Shapes
{
	private final ArrayList<ShapeItem> shapes = new ArrayList<ShapeItem>(); //contains all existing shapes
	
	/**
	 * Adds a shape with a name to the list
	 * @param item The shape with a name to add
	 */
	public void add(ShapeItem item)
	{
		if(!shapes.contains(item))
			shapes.add(item);
	}
	
	/**
	 * Checks wether a shape has been clicked and returns the name of the shape, if so
	 * @param p The point that got clicked
	 * @return The name of the clicked shape, null if none has been clicked
	 */
	public String getContains(Point p)
	{
		for(ShapeItem item : shapes)
		{
			if(item.getShape().contains(p))
				return item.getName();
		}
		
		return null;
	}
	
	/**
	 * Removes all shapes. Used when resetting the game
	 */
	public void clear()
	{
		shapes.clear();
	}
	
	/**
	 * Models a shape with a name, that can be used to determine wether it got clicked on or not
	 * @author bl4ckscor3
	 */
	public static class ShapeItem
	{
		private Shape shape;
		private String name;
		
		/**
		 * @param s The shape
		 * @param n The name of the shape
		 */
		public ShapeItem(Shape s, String n)
		{
			shape = s;
			name = n;
		}
		
		/**
		 * @return The shape
		 */
		public Shape getShape()
		{
			return shape;
		}

		/**
		 * @return The name of the shape
		 */
		public String getName()
		{
			return name;
		}
	}
}
