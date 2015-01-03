package me.pstakoun.mai;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChatAI extends MAI implements Module
{
	private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	public ChatAI()
	{
		
	}

	@Override
	public String getName() {
		return "ChatAI";
	}
	
}
