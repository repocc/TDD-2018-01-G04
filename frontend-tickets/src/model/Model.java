package model;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Observable;
import java.util.Vector;

import controller.ControllerContext;
import controller.MainController;
import service.ProjectService;

public class Model extends Observable 
{
	private ControllerContext context;
	private User currentUser;
	private Vector<Project> projects = new Vector<>();
	private TicketsSystemAdapter adapter;
	
	public Model(ControllerContext context)
	{
		this.adapter = new TicketsSystemAdapter();
		this.context = context;
	}
	
	public void notifyContextLogin()
	{
		context.setController(new MainController(this));
		context.start();
	}
	
	public void authenticateUser(User user) {

		currentUser = user;

	}
	
	public Vector<Project> getProjects()
	{
		ProjectService projectService = new ProjectService();

		try {
			projects = projectService.getProjects();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return projects;
	}
	
	public Vector<Ticket> getTicketsFromProject(String projectName)
	{
		Iterator i = this.projects.iterator();
	
		while(i.hasNext())
		{
			Project project = (Project)i.next();
			if(project.toString().equals(projectName))
			{
				ProjectService projectService = new ProjectService();
				try {
					project = projectService.getProjet(project);
				} catch (IOException e) {
					e.printStackTrace();
				}

				return project.getTickets();
			}
		}
		return null;
	}
	
	public Vector<TicketState> getStatesFromProject(String projectName)
	{
		Iterator i = this.projects.iterator();
	
		while(i.hasNext())
		{
			Project project = (Project)i.next();
			if(project.toString().equals(projectName))
			{
				return project.getStates();
			}
		}
		return null;
	}

	public void changeTicketState(Ticket selectedTicket, Project selectedProject)
	{
		selectedProject.changeTicketState(currentUser, selectedTicket);
		//TODO: Update ticket state in API
	}

	public boolean canUserChangeToState(Project project, TicketState state)
	{
		String role = project.getUserRole(currentUser.getName());
		return state.canChangeState(role);
	}

	public User getCurrentUser()
	{
		return this.currentUser;
	}

	public Project getProjet(String name) {

		for (Project project: this.projects) {
			if(project.toString().equals(name))
			{
				return project;
			}
		}

		return null;
	}

}
