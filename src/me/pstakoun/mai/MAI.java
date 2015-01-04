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

public class MAI
{
	private static ArrayList<Module> modules = new ArrayList<Module>();
	private static Reflections reflections = new Reflections("me.pstakoun.mai");
	public static PrintWriter logger;
	
	public static void main(String[] args)
	{
		Reflections.log = null;
		try {
			createLog();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Set<Class<? extends Module>> mods = reflections.getSubTypesOf(Module.class);
			Iterator<Class<? extends Module>> iterator = mods.iterator();
			while (iterator.hasNext()) {
				Class mod = iterator.next();
				String cls = mod.getName();
				Object instance = Class.forName(cls).newInstance();
				if (!modules.contains((Module)instance)) {
					modules.add((Module)instance);
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		showModules();
	}
	
	private static void createLog() throws IOException
	{
		Calendar cal = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-");
    	String date = sdf.format(cal.getTime());
    	
    	File dir = new File("logs");
    	dir.mkdir();
    	String logName;
    	File log;
    	
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
    	
		logger = new PrintWriter(log.getAbsoluteFile());
	}
	
	private static void showModules()
	{
		String mods = "Installed modules:";
		for (Module mod : modules) {
			mods += ("\n" + mod.getName());
		}
		System.out.println(mods);
	}
	
}
