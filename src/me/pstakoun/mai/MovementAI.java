package me.pstakoun.mai;

public class MovementAI extends MAI implements Module
{
	public MovementAI()
	{
		registerModule(this);
	}
	
	@Override
	public String getName() {
		return "ChatAI";
	}

}
