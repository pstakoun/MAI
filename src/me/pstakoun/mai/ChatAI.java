package me.pstakoun.mai;

import java.util.Random;

/**
 * The ChatAI module is a basic chatbot that can be used as a base
 * for many different high level chat programs.
 * @author Peter Stakoun
 */
public class ChatAI implements Module
{
	/* Declares randomizer. */
	Random random;
	/* Stores input. */
	String input;
	/* Stores last output. */
	String prevOutput;
	
	/* Declare randomizable outputs. */
	String greeting[];
	String goodbye[];
	String question[];
	String statement[];
	
	/* Declare used outputs. */
	boolean questionAsked[];
	boolean statementMade[];
	
	/**
	 * Sole constructor for ChatAI.
	 * Sets up declared outputs.
	 */
	public ChatAI()
	{
		/* Creates randomizer. */
		random = new Random();
		
		// TODO Make external dict
		/* Sets up randomizable outputs. */
		greeting = new String[] {"Hello.","Hi.","Hey."};
		goodbye = new String[] {"Goodbye.","Bye.","See you later."};
		question = new String[] {"0","1","2","3","4","5","6","7"};
		statement = new String[] {"0","1","2","3","4","5","6","7"};
		
		/* Sets up used outputs. */
		questionAsked = new boolean[63];
		for (int i = 0; i < questionAsked.length; i++) {
			questionAsked[i] = false;
		}
		statementMade = new boolean[63];
		for (int i = 0; i < statementMade.length; i++) {
			statementMade[i] = false;
		}
	}
	
	@Override
	public void onActivate()
	{
		/* Greets user. */
		String greet = greeting[random(0, greeting.length-1)];
		say(greet);
		prevOutput = greet;
		/* Begins chatting. */
		chat();
	}

	@Override
	public void onDeactivate()
	{
		/* Says goodbye to user. */
		String bye = goodbye[random(0, greeting.length-1)];
		say(bye);
		prevOutput = bye;
		/* Clears information from current session. */
		clearInformation();
	}
	
	/**
	 * Chats with user.
	 */
	private void chat()
	{
		/* Handles user input. */
		while (activeModule == this)
		{
			/* Gets user input. */
			input = ai.getInput();
			/* Responds to user. */
			respondToInput();
		}
	}
	
	/**
	 * Responds to current input.
	 */
	private void respondToInput()
	{
		/* Checks if input is a greeting. */
		if (isGreeting(input)) {
			/* Checks if last output was a greeting. */
			if (isGreeting(prevOutput)) {
				int r = random(0,1);
				if (r == 0) {
					/* Asks the user a question. */
					askQuestion(0);
				} else if (r == 1) {
					/* Makes a statement to the user. */
					makeStatement(0);
				}
			}
		/* Checks if input is a goodbye. */
		} else if (isGoodbye(input)) {
			/* Deactivates module. */
			onDeactivate();
		/* Default case. */
		} else {
			say("Google it!");
		}
	}
	
	/**
	 * Checks if given string is a greeting.
	 * @param str
	 * @return Whether or not given string is a greeting.
	 */
	private boolean isGreeting(String str)
	{
		/* Checks if greeting array contains given string. */
		for (int i = 0; i < greeting.length; i++)
		{
			if (str.equalsIgnoreCase(greeting[i])) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if given string is a goodbye.
	 * @param str
	 * @return Whether or not given string is a goodbye.
	 */
	private boolean isGoodbye(String str)
	{
		/* Checks if goodbye array contains given string. */
		for (int i = 0; i < goodbye.length; i++)
		{
			if (str.equalsIgnoreCase(goodbye[i])) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Asks user a personal question.
	 * @param complexity
	 */
	private void askQuestion(int complexity)
	{
		/* Creates variables for use in method. */
		int comp = complexity;
		String q = null;
		int rand;
		
		/* Goes through complexity levels to find question. */
		if (comp == 0) {
			int i = 0;
			/* Tries to find an unused question. */
			while (true) {
				rand = random(0, 7);
				i++;
				/* If found, sets question. */
				if (!questionAsked[rand]) {
					q = question[rand];
					break;
				}
				/* After 64 unsuccessful attempts, goes to next complexity level. */
				if (i == 63) {
					comp++;
					break;
				}
			}
		} if (comp == 1) {
			int i = 0;
			/* Tries to find an unused question. */
			while (true) {
				rand = random(8, 15);
				i++;
				/* If found, sets question. */
				if (!questionAsked[rand]) {
					q = question[rand];
					break;
				}
				/* After 64 unsuccessful attempts, goes to next complexity level. */
				if (i == 63) {
					comp++;
					break;
				}
			} 
		} if (comp == 2) {
			int i = 0;
			/* Tries to find an unused question. */
			while (true) {
				rand = random(16, 23);
				i++;
				/* If found, sets question. */
				if (!questionAsked[rand]) {
					q = question[rand];
					break;
				}
//				/* After 64 unsuccessful attempts, goes to next complexity level. */
				if (i == 63) {
//					comp++;
					break;
				}
			}
		}
		say(q);
		prevOutput = q;
	}

	/**
	 * Makes a statement to the user.
	 * @param complexity
	 */
	private void makeStatement(int complexity)
	{
		/* Creates variables for use in method. */
		int comp = complexity;
		String s = null;
		int rand;
		
		/* Goes through complexity levels to find statement. */
		if (comp == 0) {
			int i = 0;
			/* Tries to find an unused statement. */
			while (true) {
				rand = random(0, 7);
				i++;
				/* If found, sets statement. */
				if (!statementMade[rand]) {
					s = statement[rand];
					break;
				}
				/* After 64 unsuccessful attempts, goes to next complexity level. */
				if (i == 63) {
					comp++;
					break;
				}
			}
		} if (comp == 1) {
			int i = 0;
			/* Tries to find an unused statement. */
			while (true) {
				rand = random(8, 15);
				i++;
				/* If found, sets statement. */
				if (!statementMade[rand]) {
					s = statement[rand];
					break;
				}
				/* After 64 unsuccessful attempts, goes to next complexity level. */
				if (i == 63) {
					comp++;
					break;
				}
			} 
		} if (comp == 2) {
			int i = 0;
			/* Tries to find an unused statement. */
			while (true) {
				rand = random(16, 23);
				i++;
				/* If found, sets statement. */
				if (!statementMade[rand]) {
					s = statement[rand];
					break;
				}
//				/* After 64 unsuccessful attempts, goes to next complexity level. */
				if (i == 63) {
//					comp++;
					break;
				}
			}
		}
		say(s);
		prevOutput = s;
	}

	/**
	 * Clears information from current session.
	 */
	private void clearInformation()
	{
		/* Clears previous inputs and outputs. */
		input = null;
		prevOutput = null;
		
		/* Clears asked questions. */
		for (int i = 0; i < questionAsked.length; i++)
		{
			questionAsked[i] = false;
		}
		
		/* Clears made statements. */
		for (int i = 0; i < statementMade.length; i++)
		{
			statementMade[i] = false;
		}
	}
	
	/**
	 * Prints given string.
	 * @param str
	 */
	private void say(String str)
	{
		/* Prints given string. */
		System.out.println(str);
	}
	
	/**
	 * Returns random integer from min to max.
	 * @param min
	 * @param max
	 * @return random integer from min to max
	 */
	private int random(int min, int max)
	{
		/* Gets and returns random integer. */
		int rand = random.nextInt((max - min) + 1) + min;
		return rand;
	}
	
	/**
	 * Returns name of module.
	 */
	@Override
	public String getName()
	{
		/* Returns module name. */
		return "ChatAI";
	}
	
}
