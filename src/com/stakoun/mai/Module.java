package com.stakoun.mai;

/**
 * All modules must implement this interface in order to be recognized.
 * This provides them with methods to communicate with MAI, the user, and other modules.
 * @author Peter Stakoun
 */
public abstract class Module
{
	MAI mai = new MAI();
	
	/**
	 * Called when module activated.
	 */
	abstract void onActivate();
	
	/**
	 * Called when module deactivated.
	 */
	abstract void onDeactivate();
	
	/**
	 * @return Name of module.
	 */
	abstract String getName();
	
}
