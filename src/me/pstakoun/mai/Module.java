package me.pstakoun.mai;

import java.io.PrintWriter;

/**
 * All modules must implement this interface in order to be recognized.
 * This provides them with methods to communicate with MAI, the user, and other modules.
 * @author Peter Stakoun
 */
public interface Module
{
	/* Creates now instance of MAI. */
	MAI ai = new MAI();
	
	/* Writes to log file. */
	PrintWriter logger = MAI.logger;
	
	/**
	 * Called when module activated.
	 */
	void onActivate();
	
	/**
	 * Called when module deactivated.
	 */
	void onDeactivate();
	
	/**
	 * @return The name of the module.
	 */
	String getName();
	
}
