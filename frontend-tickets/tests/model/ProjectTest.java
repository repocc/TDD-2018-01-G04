package model;

import org.junit.Before;

import java.util.HashSet;
import java.util.Vector;

import static org.junit.Assert.*;

public class ProjectTest {

    private Project project;

    @Before
    public void setUp(){

        project = new Project();
        HashSet<String> role = new HashSet<>();
        role.add("pl");
        role.add("dev");

        HashSet<String> fields = new HashSet<>();
        fields.add("name");
        fields.add("description");

        Vector<TicketState> ticketState = new Vector<>();
        ticketState.add(new TicketState("open",role));
        ticketState.add(new TicketState("in-progress",role));
        ticketState.add(new TicketState("close",role));

        Vector<TicketType> ticketTypes = new Vector<>();
        ticketTypes.add(new TicketType("bug",fields));
        ticketTypes.add(new TicketType("feature",new HashSet<String>()));

        project.setTicketStates(ticketState);
        project.setTicketTypes(ticketTypes);

    }

    @org.junit.Test
    public void getNextTicketState() {

        assertTrue(project.getNextTicketState("open").getName().equals("in-progress"));

    }

    @org.junit.Test
    public void theLastStateHasNoNext() {

        assertEquals(null,project.getNextTicketState("close"));

    }

    @org.junit.Test
    public void validFields() {

        assertTrue(project.validateTicketType("name","name-text","bug"));
        assertTrue(project.validateTicketType("description","description-text","bug"));

    }

    @org.junit.Test
    public void invalidFields() {

        assertFalse(project.validateTicketType("name","","bug"));
        assertFalse(project.validateTicketType("description","","bug"));

    }

    @org.junit.Test
    public void ifNotHaveRequiredFieldsTheValidationIsTrue() {

        assertTrue(project.validateTicketType("name","","feature"));
        assertTrue(project.validateTicketType("description","","feature"));
        assertTrue(project.validateTicketType("name","name-text","feature"));
        assertTrue(project.validateTicketType("description","description-text","feature"));

    }

}