package model;

import java.util.Iterator;
import java.util.Observable;
import java.util.Vector;

import controller.ControllerContext;
import controller.MainController;

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
		
		//TODO: Use adapter to get projects
		Vector<User> users = new Vector<User>();
		users.add(new User("Pepe"));
		users.add(new User("Dylan"));
		users.add(new User("Tom"));
		
		//Model example
		Vector<TicketState> states1 = new Vector<TicketState>();
		states1.add(new TicketState("OPEN", null));
		Project p1 = new Project("Hello Project", new User("Pepe"), users, states1);
		p1.addTicket(new Ticket("Titulo1", "Descripcion1", "Tipo1", "OPEN"));
		projects.add(p1);
		
		Vector<TicketState> states2 = new Vector<TicketState>();
		states2.add(new TicketState("OPEN", null));
		states2.add(new TicketState("IN PROGRESS", null));
		p1 = new Project("Hello Project2", new User("Pepe"), users, states2);
		p1.addTicket(new Ticket("Titulo1", "Descripcion1", "Tipo1", "OPEN"));
		p1.addTicket(new Ticket("Titulo2", "Descripcion2", "Tipo2", "IN PROGRESS"));
		projects.add(p1);
		
		Vector<TicketState> states3 = new Vector<TicketState>();
		states3.add(new TicketState("OPEN", null));
		states3.add(new TicketState("IN PROGRESS", null));
		states3.add(new TicketState("CLOSED", null));
		p1 = new Project("Hello Project3", new User("Pepe"), users, states3);
		p1.addTicket(new Ticket("Titulo1", "Descripcion1", "Tipo1", "OPEN"));
		p1.addTicket(new Ticket("Titulo2", "Descripcion2", "Tipo2", "IN PROGRESS"));
		p1.addTicket(new Ticket("Titulo3", "Descripcion3", "Tipo3", "CLOSED"));
		projects.add(p1);
		
	}
	
	public void notifyContextLogin()
	{
		context.setController(new MainController(this));
		context.start();
	}
	
	public boolean authenticateUser(String username)
	{
		//TODO: Use adapter to connect to API
		user = new User(username, "admin");
		user.setRole("admin");
		//return adapter.authenticateUser(user);
		return true;
	}
	
	public Vector<Project> getProjects()
	{
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
}
