package me.pstakoun.mai;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public interface Module
{
	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	public Module activeModule = null;
	
	public String getName();
	
}
