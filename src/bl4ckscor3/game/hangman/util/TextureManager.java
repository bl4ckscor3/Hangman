package bl4ckscor3.game.hangman.util;

import java.awt.Image;
import java.util.HashMap;
import java.util.Random;

import javax.swing.ImageIcon;

/**
 * Contains useful methods for loading textures in different ways
 * @author bl4ckscor3
 */
public class TextureManager
{
	private static final Random r = new Random();
	private static final HashMap<String,Image> loadedTextures = new HashMap<String,Image>(); //resource location, image itself
	
	/**
	 * Loads a texture from the resources folder without subfolders
	 * @param fileName The name of the file
	 * @return The loaded image
	 */
	public static Image loadTexture(String fileName)
	{
		String loc = "resources/" + fileName + ".png";
		
		if(!loadedTextures.containsKey(loc))
			loadedTextures.put(loc, new ImageIcon(loc).getImage());

		return loadedTextures.get(loc);
	}
	
	/**
	 * Loads a texture from the resources folder with subfolders
	 * @param fileName The name of the file
	 * @param path The folder structure in which the file can be found
	 * @return The loaded image
	 */
	public static Image loadTextureFromPath(String fileName, String path)
	{
		String loc = "resources/" + path + fileName + ".png";
		
		if(!loadedTextures.containsKey(loc))
			loadedTextures.put(loc, new ImageIcon(loc).getImage());

		return loadedTextures.get(loc);
	}
	
	/**
	 * Loads a random texture of files with the same file name and appended number (e.g. tex_0, tex_1, tex_2 ...) from the resources folder without subfolders
	 * @param fileName The name of the file
	 * @param textureAmount How many texturefiles there are for the same Material
	 * @return The loaded image
	 */
	public static Image loadRandomTexture(String fileName, int textureAmount)
	{
		String loc = "resources/" + fileName + "_" + r.nextInt(textureAmount) + ".png";
		
		if(!loadedTextures.containsKey(loc))
			loadedTextures.put(loc, new ImageIcon(loc).getImage());

		return loadedTextures.get(loc);
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
		String loc = "resources/" + path + fileName + "_" + r.nextInt(textureAmount) + ".png";
		
		if(!loadedTextures.containsKey(loc))
			loadedTextures.put(loc, new ImageIcon(loc).getImage());

		return loadedTextures.get(loc);
	}
}
