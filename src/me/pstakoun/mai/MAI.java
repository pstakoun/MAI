package me.pstakoun.mai;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;

import org.reflections.Reflections;

/**
 * The MAI class locates, initiates, and controls all modules.
 * @author Peter Stakoun
 */
public class MAI
{
	/* Contains all modules located by MAI. */
	private static ArrayList<Module> modules = new ArrayList<Module>();
	/* Creates instance of Reflections and is used to located modules. */
	private static Reflections reflections = new Reflections("me.pstakoun.mai");
	/* Declares logger used to log events. */
	public static PrintWriter logger;
	
	/**
	 * Called when application executes.
	 * Locates, sets up, and stores modules.
	 */
	public static void main(String[] args)
	{
		/* Turns off Reflections logging. */
		Reflections.log = null;
		
		/* Creates log file for current session. */
		try {
			createLog();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/* Locates and stores modules. */
		try {
			/* Finds classes implementing Module. */
			Set<Class<? extends Module>> mods = reflections.getSubTypesOf(Module.class);
			/* Creates iterator for found modules. */
			Iterator<Class<? extends Module>> iterator = mods.iterator();
			
			/* Iterates through modules and stores them. */
			while (iterator.hasNext()) {
				Class<? extends Module> mod = iterator.next();
				String cls = mod.getName();
				Object instance = Class.forName(cls).newInstance();
				/* Stores the module if it's not a duplicate. */
				if (!modules.contains((Module)instance)) {
					modules.add((Module)instance);
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		/* Display installed modules. */
		showModules();
	}
	
	/**
	 * Creates log file for current session
	 * and sets up the logger.
	 * @throws IOException
	 */
	private static void createLog() throws IOException
	{
		/* Sets up date for log file name. */
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-");
		String date = sdf.format(cal.getTime());
		
		/* Declares variables for file creation. */
		File dir = new File("logs");
		dir.mkdir();
		String logName;
		File log;
		
		/* Looks for first available file name and creates log file. */
		int i = 1;
		while (true) {
			logName = (date + i + ".txt");
			log = new File(dir, logName);
			if (!log.exists()) {
				log.createNewFile();
				break;
			}
			i++;
		}
		/* Creates logger for given file. */
		logger = new PrintWriter(log.getAbsoluteFile());
	}
	
	/**
	 * Prints list of installed modules.
	 */
	private static void showModules()
	{
		String mods = "Installed modules:";
		/* Adds modules to list. */
		for (Module mod : modules) {
			mods += ("\n" + mod.getName());
		}
		/* Prints out completed list. */
		System.out.println(mods);
	}
	
}
