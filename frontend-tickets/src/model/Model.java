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
		Project p1 = new Project("Hello Project", new User("Pepe"), users, null);
		p1.addTicket(new Ticket("Titulo1", "Descripcion1", "Tipo1"));
		projects.add(p1);
		
		p1 = new Project("Hello Project2", new User("Pepe"), users, null);
		p1.addTicket(new Ticket("Titulo1", "Descripcion1", "Tipo1"));
		p1.addTicket(new Ticket("Titulo2", "Descripcion2", "Tipo2"));
		projects.add(p1);
		
		p1 = new Project("Hello Project3", new User("Pepe"), users, null);
		p1.addTicket(new Ticket("Titulo1", "Descripcion1", "Tipo1"));
		p1.addTicket(new Ticket("Titulo2", "Descripcion2", "Tipo2"));
		p1.addTicket(new Ticket("Titulo3", "Descripcion3", "Tipo3"));
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
		Vector<String> names = new Vector<String>();
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
	
	
}
