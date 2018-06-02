package model;

import java.util.Vector;

public class Ticket {

	private String title;
	private String description;
	private String type;
	private Vector<Comment> comments = new Vector<Comment>();
	private String currentState;
	private String ID;
	
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
		String label = "<html>";
		if (!title.isEmpty()) {
			label = label + "Title: " + title;
		}

		if (!description.isEmpty()) {
			if(!title.isEmpty()){
				label = label + "<br/>";
			}
			label = label + "Description: " + description;
		}

		if (!type.isEmpty()) {
			if(!(description.isEmpty() && title.isEmpty())){
				label = label + "<br/>";
			}
			label = label + "Type: " + type;
		}
		label = label + "</html>";

		return label;
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

	public void addComment(Comment comment)
	{
		comments.add(comment);
	}

	public Vector<Comment> getComments()
	{
		return this.comments;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getID() {
		return this.ID;
	}
}
