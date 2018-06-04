package model;

import com.google.gson.annotations.SerializedName;

import java.util.HashSet;
import java.util.Vector;

public class TicketState {

	@SerializedName("state")
	private String name;
	@SerializedName("roles")
	private HashSet<String> roles;
	
	public TicketState(String name,  HashSet<String> roles)
	{
		this.name = name;
		this.roles = roles;
	}

	
	public String getName()
	{
		return this.name;
	}
	
	public boolean canChangeState(String role)
	{
		if (roles != null)
		{
			return roles.contains(role);
		}
		return false;
	}
}
