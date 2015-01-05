package me.pstakoun.mai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * The ChatAI module is a basic chatbot that can be used as a base
 * for many different high level chat programs.
 * @author Peter Stakoun
 */
public class ChatAI implements Module
{
	/* Declares file reader. */
	BufferedReader fileReader;
	/* Declares randomizer. */
	Random random;
	/* Stores input. */
	String input;
	/* Stores last output. */
	String prevOutput;
	
	/* Declare randomizable outputs. */
	ArrayList<String> greetings;
	ArrayList<String> goodbyes;
	ArrayList<String> questions;
	ArrayList<String> statements;
	
	/**
	 * Sole constructor for ChatAI.
	 * Sets up declared outputs.
	 */
	public ChatAI()
	{
		/* Creates randomizer. */
		random = new Random();
		
		/* Sets up randomizable outputs. */
		greetings = new ArrayList<String>();
		goodbyes = new ArrayList<String>();
		questions = new ArrayList<String>();
		statements = new ArrayList<String>();
		try {
			getDict();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onActivate()
	{
		/* Greets user. */
		int r = random(0, greetings.size()-1);
		String greet = correct(greetings.get(r), '.');
		say(greet);
		prevOutput = greet;
		/* Begins chatting. */
		chat();
	}

	@Override
	public void onDeactivate()
	{
		
		/* Says goodbye to user. */
		int r = random(0, goodbyes.size()-1);
		String bye = goodbyes.get(r) + ".";
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
		while (ai.GetActiveModule() == this)
		{
			/* Gets user input. */
			input = ai.GetInput();
			/* Responds to user. */
			if (input != null) {
				respondToInput();
			}
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
			ai.Deactivate(this);
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
		return greetings.contains(str.toLowerCase());
	}
	
	/**
	 * Checks if given string is a goodbye.
	 * @param str
	 * @return Whether or not given string is a goodbye.
	 */
	private boolean isGoodbye(String str)
	{
		/* Checks if goodbye array contains given string. */
		return goodbyes.contains(str.toLowerCase());
	}
	
	/**
	 * Asks user a personal question.
	 * @param complexity
	 */
	private void askQuestion(int complexity)
	{
		/* Creates variable to store question in. */
		String question = null;
		
		/* Goes through complexity levels to find question. */
		if (complexity == 0) {
			int r = random(0, questions.size()/4-1);
			question = questions.get(r);
		} else if (complexity == 1) {
			int r = random(questions.size()/4, questions.size()/2-1);
			question = questions.get(r);
		} else if (complexity == 2) {
			int r = random(questions.size()/2, questions.size()/2+questions.size()/4-1);
			question = questions.get(r);
		} else if (complexity == 3) {
			int r = random(questions.size()/2+questions.size()/4, questions.size()-1);
			question = questions.get(r);
		}
		say(question);
		prevOutput = question;
	}

	/**
	 * Makes a statement to the user.
	 * @param complexity
	 */
	private void makeStatement(int complexity)
	{
		/* Creates variable to store statement in. */
		String statement = null;
		
		/* Goes through complexity levels to find statement. */
		if (complexity == 0) {
			int r = random(0, statements.size()/4-1);
			statement = statements.get(r);
		} else if (complexity == 1) {
			int r = random(statements.size()/4, statements.size()/2-1);
			statement = statements.get(r);
		} else if (complexity == 2) {
			int r = random(statements.size()/2, statements.size()/2+statements.size()/4-1);
			statement = statements.get(r);
		} else if (complexity == 3) {
			int r = random(statements.size()/2+statements.size()/4, statements.size()-1);
			statement = statements.get(r);
		}
		say(statement);
		prevOutput = statement;
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
	
	private String correct(String str, char punct)
	{
		return Character.toUpperCase(str.charAt(0)) + str.substring(1) + punct;
	}
	
	private void getDict() throws IOException
	{
		fileReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("dict/greetings.txt")));
		while (fileReader.ready())
		{
			greetings.add(fileReader.readLine());
		}
		
		fileReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("dict/goodbyes.txt")));
		while (fileReader.ready())
		{
			goodbyes.add(fileReader.readLine());
		}
		
		fileReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("dict/questions.txt")));
		while (fileReader.ready())
		{
			questions.add(fileReader.readLine());
		}
		
		fileReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("dict/statements.txt")));
		while (fileReader.ready())
		{
			statements.add(fileReader.readLine());
		}
	}
	
	/**
	 * Clears information from current session.
	 */
	private void clearInformation()
	{
		/* Clears previous inputs and outputs. */
		input = null;
		prevOutput = null;
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
