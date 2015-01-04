package me.pstakoun.mai;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
