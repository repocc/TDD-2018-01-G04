package model;

import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("ticket")
    private String ticketId;
	@SerializedName("author")
	private String username;
	@SerializedName("text")
	private String text;
	
	public Comment(User user, String text, Ticket ticket)
	{
		this.ticketId = ticket.getID();
	    this.username = user.getName();
		this.text = text;
	}

	public String toString()
	{
		return "<html>Posted by: "+ username + "<br/>" + text + "</html>";
	}
}
