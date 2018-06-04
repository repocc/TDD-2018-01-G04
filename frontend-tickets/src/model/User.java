package model;

import com.google.gson.annotations.SerializedName;

public class User {

	@SerializedName("username")
	private String name;
	@SerializedName("noRole")
	private String role;
	@SerializedName("role")
	private Role roleReal;
	@SerializedName("id")
	private String ID;
	
	public User(String name)
	{
		this.name = name;
	}

	public User(String username, String role) {
		this.name = username;
		this.role = role;
	}

	public User(String username, Role role, String ID) {
		this.name = username;
		this.roleReal = role;
		this.ID = ID;
	}

	public void setRole(String role)
	{
		this.role = role.toLowerCase();
	}

	public void setRoleReal(Role role) {
		this.roleReal = role;
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

