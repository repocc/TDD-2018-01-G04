package model;

public class User {

	private String name;
	private String role;
	
	public User(String name)
	{
		this.name = name;
	}

	public User(String username, String role) {
		this.name = name;
		this.role = role;
	}

	public void setRole(String role)
	{
		this.role = role.toLowerCase();
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String toString()
	{
		return this.name;
	}
}

