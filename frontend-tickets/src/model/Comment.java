package model;

import com.google.gson.annotations.SerializedName;

public class Comment {

	@SerializedName("author")
	private User user;
	@SerializedName("text")
	private String text;
	
	public Comment(User user, String text)
	{
		this.user = user;
		this.text = text;
	}

	public String toString()
	{
		return "<html>Posted by: "+ user.getName() + "<br/>" + text + "</html>";
	}
}
