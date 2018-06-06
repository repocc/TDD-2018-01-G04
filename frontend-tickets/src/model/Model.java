package model;

import java.util.Observable;
import java.util.Vector;

public class Model extends Observable 
{
	private User currentUser;
	private Vector<Project> projects = new Vector<>();

	public Model()
	{
		super();
	}
	
	public void authenticateUser(User user) {

		currentUser = user;
	}

	public User getCurrentUser()
	{
		return this.currentUser;
	}

	public boolean canUserChangeToState(Project project, TicketState state)
	{
		String role = project.getUserRole(currentUser.getName());
		return state.canChangeState(role);
	}

}
