package model;

import java.util.*;

public class FlowStates {
    private Map<String,HashSet<String>> states = new HashMap<>();
    private ArrayList<String> statesOrder = new ArrayList<>();

    public void setState(String state) {
        this.states.put(state,new HashSet<>());
        this.statesOrder.add(state);
    }

    public void addRoleInState(String state, String role) {
        HashSet<String> roles;
        if (this.states.containsKey(state)) {
            roles = this.states.get(state);
        } else {
            roles = new HashSet<>();
        }
        roles.add(role);
        this.states.put(state,roles);
    }

    public void removeRoleInState(String state,String role) {
        if (this.states.containsKey(state)) {
            HashSet<String> fields = this.states.get(state);
            fields.remove(role);
        }
    }

    public Vector<TicketState> getTicketStates() {

        Vector<TicketState> ticketStates = new Vector<>();

        for (String state:this.statesOrder) {
            HashSet<String> roles = new HashSet<>();
            if(!this.statesOrder.get(this.statesOrder.size() - 1).equals(state)) {
                roles = this.states.get(state);
            }
            ticketStates.add(new TicketState(state,roles));
        }

        return ticketStates;
    }

    public void clear() {

        this.states.clear();
        this.statesOrder.clear();

    }

}
