package model;

import java.util.Vector;

public class Ticket {

	private String title;
	private String description;
	private String type;
	private Vector<Comment> comments = new Vector<Comment>();
	private String currentState;
	
	public Ticket(String title, String description, String type, String initialState)
	{
		this.title = title;
		this.description = description;
		this.type = type;
		this.currentState = initialState;
	}

	public boolean isCurrentState(String state)
	{
		return this.currentState.equals(state);
	}
	
	public String toString() {
		return "<html>Title: "+ title + "<br/>Description: " + description + "<br/>Type: " + type + "</html>";
	}

	public String getCurrentState()
	{
		return this.currentState;
	}

	public void changeState(String state)
	{
		this.currentState = state;
	}

	public String getName()
	{
		return this.title;
	}

	public String getDescription()
	{
		return this.description;
	}

	public String getType()
	{
		return this.type;
	}

}
