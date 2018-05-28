package model;

import java.util.Vector;

public class Ticket {

	private String title;
	private String description;
	private String type;
	private Vector<Comment> comments = new Vector<Comment>();
	
	public Ticket(String title, String description, String type)
	{
		this.title = title;
		this.description = description;
		this.type = type;
	}
}
