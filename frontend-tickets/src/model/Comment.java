package model;

public class Comment {

	private User user;
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
