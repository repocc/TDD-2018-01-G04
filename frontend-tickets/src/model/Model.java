package model;

import java.util.Observable;
import java.util.Vector;

import controller.AdminController;
import controller.ControllerContext;
import controller.GuestController;

public class Model extends Observable 
{
	private ControllerContext context;
	private User user;
	private Vector<Project> projects = new Vector<Project>();
	private TicketsSystemAdapter adapter;
	
	public Model(ControllerContext context)
	{
		this.adapter = new TicketsSystemAdapter();
		this.context = context;
	}
	
	public void notifyContextLogin()
	{
		if(user.isAdmin())
		{
			context.setController(new AdminController(this));
		}
		else
		{
			context.setController(new GuestController(this));
		}
		context.start();
	}
	
	public boolean authenticateUser(String username)
	{
		user = new User(username);
		return adapter.authenticateUser(user);
	}
}
