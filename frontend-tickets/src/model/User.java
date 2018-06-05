package model;

import com.google.gson.annotations.SerializedName;

public class User {

	@SerializedName("username")
	private String name;
	@SerializedName("role")
	private Role role;
	@SerializedName("id")
	private String id;
	
	public User(String name)
	{
		this.name = name;
	}

	public User(String username, Role role, String id) {
		this.name = username;
		this.role = role;
		this.id = id;
	}
	public String getName()
	{
		return this.name;
	}

	public String getRole()
	{
		return this.role.getId();
	}
}

