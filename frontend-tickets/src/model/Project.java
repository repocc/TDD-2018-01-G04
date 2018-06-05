package model;

import com.google.gson.annotations.SerializedName;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Vector;

import javax.swing.ComboBoxModel;

public class Project {

	@SerializedName("name")
	private String name;
	@SerializedName("UserOwner")
	private User owner;
	@SerializedName("owner")
	private String nameOwner;
	@SerializedName("ticket-types")
	private Vector<TicketTypes> ticketTypes = new Vector<>();
	@SerializedName("states")
	private Vector<TicketState> ticketStates = new Vector<>();
	@SerializedName("users")
	private Vector<User> users = new Vector<>();
	@SerializedName("tickets")
	private Vector<Ticket> tickets = new Vector<>();
	private Vector<String> roles = new Vector<>();
	@SerializedName("id")
	private String id;

	public Project(){

	}

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

	public void setName(String name) {
		this.name = name;
	}

	public void setOwner(String owner) {
		this.nameOwner = owner;
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

	public TicketState getNextTicketState(String name) {

		Iterator<TicketState> iterator = this.ticketStates.iterator();

		while (iterator.hasNext()) {
			TicketState ticketState = iterator.next();
			if (ticketState.getName().equals(name)) {
				return iterator.next();
			}
		}

		return null;
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

	public void setID(String id) {
		this.id = id;
	}

	public String getID() {
		return this.id;
	}

	public void setTicketTypes(Vector<TicketTypes> ticketTypes) {
		this.ticketTypes = ticketTypes;
	}

	public void setTicketStates(Vector<TicketState> ticketStates) {
		this.ticketStates = ticketStates;
	}

	public void setUsers(Vector<User> users) {
		this.users = users;
	}

    public String getUserRole(String name) {

		String role = "";
		if (this.users != null){
			for (User user:this.users) {
				if (user.getName().equals(name)) {
					role = user.getRole();
				}
			}
		}
		return  role;
    }
}
