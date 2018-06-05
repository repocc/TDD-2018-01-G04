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
		
		//TODO: Use adapter to get projects
		/*Vector<User> users = new Vector<User>();
		users.add(new User("Pepe"));
		users.add(new User("Dylan"));
		users.add(new User("Tom"));
		
		//Model example
		HashSet<String> roles = new HashSet<String>();
		roles.add("admin");
		roles.add("guest");
		Vector<TicketState> states1 = new Vector<TicketState>();
		states1.add(new TicketState("OPEN", roles));
		Project p1 = new Project("Hello Project", new User("Pepe"), users, states1);
		p1.addTicket(new Ticket("Titulo1", "Descripcion1", "Tipo1", "OPEN"));
		projects.add(p1);
		
		Vector<TicketState> states2 = new Vector<TicketState>();
		states2.add(new TicketState("OPEN", roles));
		states2.add(new TicketState("IN PROGRESS", roles));
		p1 = new Project("Hello Project2", new User("Pepe"), users, states2);
		p1.addTicket(new Ticket("Titulo1", "Descripcion1", "Tipo1", "OPEN"));
		p1.addTicket(new Ticket("Titulo2", "Descripcion2", "Tipo2", "IN PROGRESS"));
		p1.addTicket(new Ticket("Titulo3", "Descripcion3", "Tipo3", "IN PROGRESS"));
		p1.addTicket(new Ticket("Titulo3", "Descripcion3", "Tipo3", "IN PROGRESS"));
		p1.addTicket(new Ticket("Titulo3", "Descripcion3", "Tipo3", "IN PROGRESS"));
		p1.addTicket(new Ticket("Titulo3", "Descripcion3", "Tipo3", "IN PROGRESS"));
		p1.addTicket(new Ticket("Titulo3", "Descripcion3", "Tipo3", "IN PROGRESS"));
		p1.addTicket(new Ticket("Titulo3", "Descripcion3", "Tipo3", "IN PROGRESS"));
		p1.addTicket(new Ticket("Titulo3", "Descripcion3", "Tipo3", "IN PROGRESS"));
		p1.addTicket(new Ticket("Titulo3", "Descripcion3", "Tipo3", "IN PROGRESS"));
		projects.add(p1);
		
		Vector<TicketState> states3 = new Vector<TicketState>();
		HashSet<String> roles3 = new HashSet<String>();
		roles3.add("guest");
		states3.add(new TicketState("OPEN", roles));
		states3.add(new TicketState("IN PROGRESS", roles));
		states3.add(new TicketState("QA", roles3));
		states3.add(new TicketState("CLOSED", null));
		p1 = new Project("Hello Project3", new User("Pepe"), users, states3);
		p1.addTicket(new Ticket("", "Descripcion1", "Tipo1", "OPEN"));
		p1.addTicket(new Ticket("Titulo2", "", "Tipo2", "IN PROGRESS"));
		p1.addTicket(new Ticket("Titulo3", "Descripcion3", "Tipo3", "CLOSED"));
		projects.add(p1);*/
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
