package me.pstakoun.mai;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;

import org.reflections.Reflections;

public class MAI
{
	private static ArrayList<Object> modules = new ArrayList<Object>();
	private static Reflections reflections = new Reflections("me.pstakoun.mai");
	public static PrintWriter logger;
	
	public static void main(String[] args)
	{
/*		try {
			createLog();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		if (args.length > 1) {
			try {
				Set<Class<? extends MAI>> mods = reflections.getSubTypesOf(me.pstakoun.mai.MAI.class);
				Iterator<Class<? extends MAI>> iterator = mods.iterator();
				while (iterator.hasNext()) {
					Object mod = iterator.next();
					String cls = mod.getClass().getName();
					Class.forName(cls).newInstance();
				}
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		showModules();
	}
	
	private static void createLog() throws IOException
	{
		Calendar cal = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-");
    	String date = sdf.format(cal.getTime());
    	
    	File log;
    	int i = 1;
    	while (true) {
    		log = new File("logs/" + date + i + ".txt");
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
		for (Object mod : modules) {
			mods += ("\n" + ((Module) mod).getName());
		}
		System.out.println(mods);
	}
	
	public static void registerModule(Object mod)
	{
		modules.add(mod);
	}
	
}
