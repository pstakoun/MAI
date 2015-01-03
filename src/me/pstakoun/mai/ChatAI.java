package me.pstakoun.mai;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChatAI extends MAI implements Module
{
	private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	public ChatAI()
	{
		registerModule(this);
		System.out.println("Hello, World!");
	}

	@Override
	public String getName() {
		return "ChatAI";
	}
	
}
