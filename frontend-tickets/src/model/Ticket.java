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
	@SerializedName("assigned")
	private String assignedUser;
	@SerializedName("project")
	private String project;
	@SerializedName("comments")
	private Vector<Comment> comments = new Vector<>();
	@SerializedName("state")
	private String currentState;
	@SerializedName("id")
	private String id;

	public Ticket(){

	}

	public boolean isCurrentState(String state)
	{
		return this.currentState.equals(state);
	}
	
	public String toString() {
		String label = "<html>";
		if (!title.isEmpty()) {
			label = label + "Title: " + title;
			label = label + "<br/>";
		}

		if (!description.isEmpty()) {
			label = label + "Description: " + description;
			label = label + "<br/>";
		}

		if (!type.isEmpty()) {
			label = label + "Type: " + type;
			label = label + "<br/>";
		}
		if (!assignedUser.isEmpty()) {
			label = label + "Assigned user: " + assignedUser;
		}
		label = label + "</html>";

		return label;
	}

	public String getCurrentState()
	{
		return this.currentState;
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

	public String getAssignedUser(){
		return this.assignedUser;
	}

	public void addComment(Comment comment)
	{
		comments.add(comment);
	}

	public Vector<Comment> getComments()
	{
		return this.comments;
	}

	public String getID() {
		return this.id;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public void setAssignedUser(String assignedUser) {
		this.assignedUser = assignedUser;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setType(String type) {
		this.type = type;
	}

}
