package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Comment;
import model.Ticket;
import model.TicketState;
import model.TicketType;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Vector;

public class TicketService {

    private BaseService baseService;
    private Gson gson;

    public TicketService() {
        this.baseService = new BaseService();
        this.gson = new Gson();
    }

    public String postTicket(Ticket ticket) throws IOException {

        String json = this.gson.toJson(ticket);

        String response = this.baseService.post(Consts.URI_POST_TICKETS,json);

        return response;

    }

    public String putChangeState(String id,TicketState ticketState) throws IOException {

        String json = this.gson.toJson(ticketState);
        String uri = Consts.URI_PUT_TICKETS + "/" + id;

        String response = this.baseService.put(uri, json);

        return response;

    }

    public Vector<TicketType> getTypes() throws IOException {

        String ticketTypesJson = this.baseService.get(Consts.URI_GET_TYPES_TICKETS);
        Type listType = new TypeToken<Vector<TicketType>>(){}.getType();
        Vector<TicketType> ticketTypes = gson.fromJson(ticketTypesJson,listType);

        return ticketTypes;

    }

    public String postComment(Comment comment) throws IOException {

        String json = this.gson.toJson(comment);

        String response = this.baseService.post(Consts.URI_POST_COMMENT,json);

        return response;

    }

    public Ticket getTicket(Ticket ticket) throws IOException {

        String ID = ticket.getID();
        String uri = Consts.URI_GET_TICKET + "/" + ID;

        String response = this.baseService.get(uri);
        Ticket ticketResponse = gson.fromJson(response,Ticket.class);

        return ticketResponse;
    }
}
