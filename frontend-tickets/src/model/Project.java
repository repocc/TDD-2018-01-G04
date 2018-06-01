package model;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Vector;

import javax.swing.ComboBoxModel;

public class Project {

	private String name;
	private User owner;
	private Vector<Ticket> tickets = new Vector<Ticket>();
	private Vector<User> users = new Vector<User>();
	private Vector<String> roles = new Vector<String>();
	private Vector<TicketState> ticketStates = new Vector<TicketState>();
	
	public Project(String name, User owner, Vector<User> users, Vector<TicketState> ticketStates)
	{
		this.name = name;
		this.owner = owner;
		this.users = users;
		this.ticketStates = ticketStates;
	}
	
	public String getName()
	{
		return name;
	}

	public String toString()
	{
		return name;
	}

	public Vector<Ticket> getTickets()
	{
		return tickets;
	}
	
	public void addTicket(Ticket ticket)
	{
		tickets.add(ticket);
	}

	public Vector<User> getUsers()
	{
		return users;
	}

	public Vector<String> getUsersNames()
	{
		Vector<String> names = new Vector<String>();
		Iterator i = this.users.iterator();
	
		while(i.hasNext())
		{
			User user = (User)i.next();
			names.add(user.getName());
		}
		return names;
	}

	public Vector<TicketState> getStates() {
		return ticketStates;
	}

	public void changeTicketState(User user, Ticket ticket)
	{
		String currentState = ticket.getCurrentState();
		Iterator i = this.ticketStates.iterator();
		
		while(i.hasNext())
		{
			TicketState state = (TicketState)i.next();
			if(state.getName().equals(currentState))
			{
				if(state.canChangeState(user.getRole()))
				{
					try {
						ticket.changeState(((TicketState) i.next()).getName());
					}
					catch (NoSuchElementException e) {
						return;
					}
				}
			}
		}

	}

}
