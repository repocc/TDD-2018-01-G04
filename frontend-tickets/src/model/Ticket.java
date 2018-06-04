package model;

import com.google.gson.annotations.SerializedName;

import java.util.Vector;

public class Ticket {

	@SerializedName("title")
	private String title;
	@SerializedName("description")
	private String description;
	@SerializedName("type")
	private String type;
	@SerializedName("asigned")
	private String userAsigned;
	@SerializedName("project")
	private String projectAsigned;
	private Vector<Comment> comments = new Vector<>();
	private String currentState;
	private String ID;

	public Ticket(){

	}

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

	public void setProjectAsigned(String projectAsigned) {
		this.projectAsigned = projectAsigned;
	}

	public void setUserAsigned(String userAsigned) {
		this.userAsigned = userAsigned;
	}

	public void setTitle(String tittle) {
		this.title = tittle;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setType(String type) {
		this.type = type;
	}
}
