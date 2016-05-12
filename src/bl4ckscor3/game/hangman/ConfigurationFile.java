package bl4ckscor3.game.hangman;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * Use the -update command to let a new configuration take place
 */
public class ConfigurationFile
{
	public File file;
	public HashMap<String,String> values = new HashMap<String,String>();
	private String[] defaultContent = new String[]{
			"################################",
			"#			Properties			#",
			"################################",
			"username=null",
			"password=null",
			"loginpage=null"
	};

	/**
	 * Sets up the configuration file, if not already done
	 */
	public ConfigurationFile()
	{
		try
		{
			file = new File(getJarLocation() + "/config.txt");

			if(!file.exists())
			{
				file.createNewFile();
				writeDefaultValues();
				populateHashMap();
			}
			else
			{
				populateHashMap();

				int i = 0;
				List<String> currentContent = FileUtils.readLines(file);
				List<String> newContent = new ArrayList<String>();
				List<String> restoredContent = new ArrayList<String>();

				for(String s : defaultContent)
				{
					if(i >= currentContent.size()) //if the line is null, just add the new line
						newContent.add(s);
					else if(currentContent.get(i).split("=").length != 0) //if it's a config value
					{
						if(s.startsWith(currentContent.get(i).split("=")[0]) && (!currentContent.get(i).equals("") || s.equals(""))) //if the config values are equal add current one to keep settings
							newContent.add(currentContent.get(i));
						else //if not, add the new default value
							newContent.add(s);
					}
					else
						newContent.add(s);

					i++;
				}

				i = 0;

				//to prevent a ConcurrentModificationException
				for(String s : newContent)
				{
					restoredContent.add(s);
				}

				//checking for anything that got reset
				for(String s : newContent)
				{
					if(s.split("=").length != 0 && values.containsKey(s.split("=")[0]))
					{
						if(values.get(s.split("=")[0]).equals(s.split("=")[1]))
						{
							i++;
							continue;
						}
						else
						{
							restoredContent.remove(i);
							restoredContent.add(i, s.split("=")[0] + "=" + values.get(s.split("=")[0]));
						}
					}

					i++;
				}

				clear();
				FileUtils.writeLines(file, restoredContent);
				populateHashMap();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Gets a String value from the config file using the given option
	 * @param option The option to get the value from
	 * @return The value associated with the given option
	 */
	public String get(String option)
	{
		return values.get(option);
	}

	/**
	 * Sets the given option to the specified value
	 * @param option The option to set the value of
	 * @param value The value to set the option to
	 */
	public void set(String option, String value)
	{
		try
		{
			List<String> lines = FileUtils.readLines(file);

			for(int i = 0; i < lines.size(); i++)
			{
				if(lines.get(i).startsWith(option + "="))
					lines.set(i, option + "=" + value);
			}

			FileUtils.writeLines(file, lines);
			values.put(option, value);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Writes the default values to the file
	 */
	public void writeDefaultValues()
	{
		try
		{
			FileUtils.writeLines(file, Arrays.asList(defaultContent));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Writes the current values into the 'values' HashMap
	 */
	public void populateHashMap()
	{		
		try
		{
			List<String> content = FileUtils.readLines(file);

			values.clear();

			for(String s : content)
			{
				if(s.contains("="))
				{
					String value = "";
					int i = 0;

					for(String eq : s.split("="))
					{
						if(i++ == 0)
							continue;

						value += eq + "=";
					}

					values.put(s.split("=")[0], value.substring(0, value.lastIndexOf('=')));
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Clears the file
	 */
	public void clear()
	{
		try
		{
			RandomAccessFile raf = new RandomAccessFile(file, "rw");

			raf.setLength(0);
			raf.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Gets the path of the running jar file
	 */
	public String getJarLocation() throws URISyntaxException
	{
		String path = Hangman.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();

		if(path.endsWith(".jar"))
			path = path.substring(0, path.lastIndexOf("/"));

		return path;
	}
}
