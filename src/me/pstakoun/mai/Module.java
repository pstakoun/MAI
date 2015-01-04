package me.pstakoun.mai;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * All modules must implement this interface in order to be recognized.
 * This provides them with methods to communicate with MAI, the user, and other modules.
 * @author Peter Stakoun
 */
public interface Module
{
	/**
	 * Reads user input.
	 */
	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	/**
	 * The currently active module.
	 */
	public Module activeModule = null;
	
	/**
	 * @return The name of the module.
	 */
	public String getName();
	
}
