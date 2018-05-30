package model;

import java.util.Vector;

public class TicketState {

	private String name;
	private Vector<String> roles = new Vector<String>();
	
	public TicketState(String name,  Vector<String> roles)
	{
		this.name = name;
		this.roles = roles;
	}
	
	public String getName()
	{
		return this.name;
	}
}
