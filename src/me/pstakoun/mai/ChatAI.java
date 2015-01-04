package me.pstakoun.mai;

import java.util.Random;

/**
 * The ChatAI module is a basic chatbot that can be used as a framework
 * for many different high level chat programs.
 * @author Peter Stakoun
 */
public class ChatAI implements Module
{
	/* Declare randomizer. */
	Random random;
	
	/* Declare randomizable outputs. */
	String greetings[];
	String goodbyes[];
	
	/**
	 * Sole constructor for ChatAI.
	 * Sets up declared outputs.
	 */
	public ChatAI()
	{
		/* Create randomizer. */
		random = new Random();
		
		/* Sets up randomizable outputs */
		greetings = new String[] {"Hello.","Hi.","Hey."};
		goodbyes = new String[] {"Goodbye.","Bye.","See you later."};
	}
	
	@Override
	public void onActivate()
	{
		String greeting = greetings[random(0, greetings.length-1)];
		System.out.println(greeting);
	}

	@Override
	public void onDeactivate()
	{
		String goodbye = goodbyes[random(0, greetings.length-1)];
		System.out.println(goodbye);
	}
	
	private int random(int min, int max)
	{
		int rand = random.nextInt((max - min) + 1) + min;
		return rand;
	}
	
	/**
	 * Returns name of module.
	 */
	@Override
	public String getName() {
		return "ChatAI";
	}
	
}
