package me.pstakoun.mai;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
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
	/* Declares variable to store currently active module. */
	private static Module activeModule;
	/* Creates instance of Reflections and is used to located modules. */
	private Reflections reflections = new Reflections("me.pstakoun.mai");
	/* Reads user input. */
	private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	/* Writes to console. */
	private PrintStream out = System.out;
	/* Declares logger used to log events. */
	private static PrintWriter logger;
	
	/**
	 * Called when application executes.
	 * Creates instance of MAI.
	 */
	public static void main(String[] args)
	{
		/* Creates new instance of MAI. */
		new MAI(args);
	}
	
	/**
	 * Main constructor for MAI.
	 * Locates, sets up, and stores modules.
	 * @param args 
	 */
	public MAI(String[] args)
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
		
		/* Activates chosen module. */
		if (args.length > 1) {
			for (Module mod : modules) {
				if (args[1].equalsIgnoreCase(mod.getName())) {
					activeModule = mod;
					activeModule.onActivate();
					break;
				}
			}
			if (activeModule == null) {
				/* Display installed modules. */
				showModules();
				while (activeModule == null) {
					GetInput();
				}
			}
		} else {
			/* Display installed modules. */
			showModules();
			/* Get module to activate. */
			while (activeModule == null) {
				GetInput();
			}
		}
	}
	
	/**
	 * Secondary constructor for MAI.
	 * Set up for module use.
	 */
	public MAI()
	{
		
	}
	
	/**
	 * Creates log file for current session
	 * and sets up the logger.
	 * @throws IOException
	 */
	private void createLog() throws IOException
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
	private void showModules()
	{
		String mods = "Installed modules:";
		/* Adds modules to list. */
		for (Module mod : modules) {
			mods += ("\n" + mod.getName());
		}
		/* Prints out completed list. */
		System.out.println(mods);
	}
	
	/**
	 * Checks whether or not user input is a command.
	 * @param input
	 * @return Whether or not input is a command.
	 */
	private boolean handleInput(String input)
	{
		/* If input if a command, send it to be executed. */
		if (isCommand(input)) {
			executeCommand(input);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if input is a command.
	 * @param input
	 * @return Whether or not input is a command.
	 */
	private boolean isCommand(String input)
	{
		if (input.startsWith("./")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Executes given command.
	 * If command invalid, show help dialog.
	 * @param cmd
	 */
	private void executeCommand(String cmd)
	{
		String cmdArgs[] = cmd.split("\\s+");
		if (cmdArgs[0].equalsIgnoreCase("./help") || cmdArgs[0].equalsIgnoreCase("./?")) {
			displayCommandHelp();
		} else if (cmdArgs[0].equalsIgnoreCase("./module")) {
			if (cmdArgs.length > 1) {
				for (Module mod : modules) {
					if (cmdArgs[1].equalsIgnoreCase(mod.getName()) && activeModule != mod) {
						if (activeModule != null) {
							activeModule.onDeactivate();
						}
						activeModule = mod;
						activeModule.onActivate();
					}
				}
			} else {
				out.println("Usage: ./module <moduleName>");
			}
		} else {
			out.println("Invalid command!");
			displayCommandHelp();
		}
	}
	
	/**
	 * Displays help dialog.
	 */
	private void displayCommandHelp()
	{
		String help = ("Commands:\n"
				+ "./help - Displays help dialog.\n"
				+ "./module <module> - Activates given module.");
		out.println(help);
	}

	/**
	 * Gets user input and sends it to be handled.
	 * If input is not a command, sends it to module.
	 * @return User input.
	 */
	public String GetInput()
	{
		String input = null;
		try {
			input = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (input != null) {
			log(input, "User");
		}
		if (!handleInput(input)) {
			return input;
		}
		return null;
	}
	
	/**
	 * Returns the currently active module.
	 * @return Currently active module.
	 */
	public Module GetActiveModule()
	{
		return activeModule;
	}
	
	/**
	 * Deactivates given module.
	 * @param mod
	 */
	public void Deactivate(Module mod)
	{
		/* If given module is active, deactivates it. */
		if (activeModule == mod) {
			activeModule = null;
			mod.onDeactivate();
		}
	}
	
	/**
	 * Logs given message.
	 * @param msg
	 * @param sender
	 */
	public void log(String msg, String sender)
	{
		Calendar cal = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("[HH:mm:ss]");
    	String timestamp = sdf.format(cal.getTime());
		
		logger.println(timestamp + " [" + sender + "]: " + msg);
		logger.flush();
	}
	
}
