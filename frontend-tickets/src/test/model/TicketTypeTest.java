package test.model;

import model.TicketType;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Vector;

import static org.junit.Assert.*;

public class TicketTypeTest {

    private TicketType ticketTypes;

    @Before
    public void setUp(){

        HashSet<String> fields = new HashSet<>();
        fields.add("name");
        fields.add("description");

        ticketTypes = new TicketType();

        ticketTypes.setType("bug");
        ticketTypes.setFields(fields);

    }

    @Test
    public void validField() {

        String fieldName = "name";
        String fieldDescription = "description";

        assertTrue(ticketTypes.validField(fieldName,"name-text"));
        assertTrue(ticketTypes.validField(fieldDescription,"description-text"));

    }

    @Test
    public void invalidField() {

        String fieldName = "name";
        String fieldDescription = "description";

        assertFalse(ticketTypes.validField(fieldName,""));
        assertFalse(ticketTypes.validField(fieldDescription,""));

    }

    @Test
    public void ifNotHaveRequiredFieldsTheValidationIsTrue() {

        String fieldText = "text";

        assertTrue(ticketTypes.validField(fieldText,"text-text"));
        assertTrue(ticketTypes.validField(fieldText,""));

    }


}