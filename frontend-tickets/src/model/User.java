package model;

public class User {

	private String name;
	private String role;
	private String ID;
	
	public User(String name)
	{
		this.name = name;
	}

	public User(String username, String role) {
		this.name = username;
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

	public String getRole()
	{
		return role;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getID() {
		return this.ID;
	}
}

