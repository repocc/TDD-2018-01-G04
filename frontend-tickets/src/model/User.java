package model;

public class User {

	private String name;
	private String role;
	
	public User(String name)
	{
		this.name = name;
	}

	public void setRole(String role)
	{
		this.role = role.toLowerCase();
	}

	public boolean isAdmin()
	{
		return (role.equals("admin"));
	}
	
	public String getName()
	{
		return this.name;
	}
}
