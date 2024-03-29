package model;

import com.google.gson.annotations.SerializedName;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

public class Project {

	@SerializedName("name")
	private String name;
	@SerializedName("owner")
	private String owner;
	@SerializedName("ticket-types")
	private Vector<TicketType> ticketTypes = new Vector<>();
	@SerializedName("states")
	private Vector<TicketState> ticketStates = new Vector<>();
	@SerializedName("users")
	private Vector<User> users = new Vector<>();
	@SerializedName("tickets")
	private Vector<Ticket> tickets = new Vector<>();
	@SerializedName("id")
	private String id;

	public Project(){

	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String toString()
	{
		return name;
	}

	public Vector<Ticket> getTickets()
	{
		return tickets;
	}
	
	public Vector<TicketState> getStates() {
		return ticketStates;
	}

	public TicketState getNextTicketState(String name) {

		Iterator<TicketState> iterator = this.ticketStates.iterator();

		while (iterator.hasNext()) {
			TicketState ticketState = iterator.next();
			if (ticketState.getName().equals(name) && iterator.hasNext()) {
				return iterator.next();
			}
		}

		return null;
	}

	public String getID() {
		return this.id;
	}

	public void setTicketTypes(Vector<TicketType> ticketTypes) {
		this.ticketTypes = ticketTypes;
	}

	public void setTicketStates(Vector<TicketState> ticketStates) {
		this.ticketStates = ticketStates;
	}

	public void setUsers(Vector<User> users) {
		this.users = users;
	}

	public Vector<User> getUsers() {
		return users;
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

    public boolean validateTicketType(String field, String text, String type) {

		for (TicketType ticketType: ticketTypes) {

			if(ticketType.getType().equals(type)) {

				return ticketType.validField(field,text);

			}

		}

		return false;
	}
}
