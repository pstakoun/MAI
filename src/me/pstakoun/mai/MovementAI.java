package me.pstakoun.mai;

/**
 * The MovementAI module is a basic movement controller
 * that can be used to perform intelligent actions
 * in anything from computer games to robots.
 * @author Peter Stakoun
 */
public class MovementAI implements Module
{
	@Override
	public void onActivate()
	{
		
	}
	
	@Override
	public void onDeactivate()
	{
		
	}

	/**
	 * Returns name of module.
	 */
	@Override
	public String getName() {
		return "MovementAI";
	}
	
}
