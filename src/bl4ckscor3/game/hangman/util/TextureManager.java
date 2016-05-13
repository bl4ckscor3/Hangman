package bl4ckscor3.game.hangman.util;

import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

/**
 * Contains useful methods for loading textures in different ways
 * @author bl4ckscor3
 */
public class TextureManager
{
	private static final Random r = new Random();
	
	/**
	 * Loads a texture from the resources folder without subfolders
	 * @param fileName The name of the file
	 * @return The loaded image
	 */
	public static Image loadTexture(String fileName)
	{
		return new ImageIcon("resources/" + fileName + ".png").getImage();
	}
	
	/**
	 * Loads a texture from the resources folder with subfolders
	 * @param fileName The name of the file
	 * @param path The folder structure in which the file can be found
	 * @return The loaded image
	 */
	public static Image loadTextureFromPath(String fileName, String path)
	{
		return new ImageIcon("resources/" + path + fileName + ".png").getImage();
	}
	
	/**
	 * Loads a random texture of files with the same file name and appended number (e.g. tex_0, tex_1, tex_2 ...) from the resources folder without subfolders
	 * @param fileName The name of the file
	 * @param textureAmount How many texturefiles there are for the same Material
	 * @return The loaded image
	 */
	public static Image loadRandomTexture(String fileName, int textureAmount)
	{
		return new ImageIcon("resources/" + fileName + "_" + r.nextInt(textureAmount) + ".png").getImage();
	}
	
	/**
	 * Loads a random texture of files with the same file name and appended number (e.g. tex_0, tex_1, tex_2 ...) from the resources folder with subfolders
	 * @param fileName The name of the file
	 * @param path The folder structure in which the file can be found
	 * @param textureAmount How many texturefiles there are for the same Material
	 * @return The loaded image
	 */
	public static Image loadRandomTextureFromPath(String fileName, String path, int textureAmount)
	{
		return new ImageIcon("resources/" + path + fileName + "_" + r.nextInt(textureAmount) + ".png").getImage();
	}
}
