package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Comment;
import model.Project;
import model.Ticket;
import model.TicketTypes;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Vector;

public class TicketService {

    private HttpService httpService;
    private Gson gson;

    public TicketService() {
        this.httpService = new HttpService();
        this.gson = new Gson();
    }

    public String postTicket(Ticket ticket) throws IOException {

        String json = this.gson.toJson(ticket);

        String response = this.httpService.post(Consts.URI_POST_TICKETS,json);

        return response;

    }

    public String postChangeState(Ticket ticket) throws IOException {

        String json = this.gson.toJson(ticket);
        String ID = ticket.getID();
        String uri = Consts.URI_POST_TICKETS + "/" + ID + "/state";

        String response = this.httpService.post(uri, json);

        return response;

    }

    public Vector<TicketTypes> getTypes() throws IOException {

        String ticketTypesJson = this.httpService.get(Consts.URI_GET_TYPES_TICKETS);
        Type listType = new TypeToken<Vector<TicketTypes>>(){}.getType();
        Vector<TicketTypes> ticketTypes = gson.fromJson(ticketTypesJson,listType);

        return ticketTypes;

    }

    public String postComment(Comment comment) throws IOException {

        String json = this.gson.toJson(comment);
        System.out.println(json);

        String response = this.httpService.post(Consts.URI_POST_COMMENT,json);

        return response;

    }

    public Ticket getTicket(Ticket ticket) throws IOException {

        String ID = ticket.getID();
        String uri = Consts.URI_GET_TICKET + "/" + ID;

        String response = this.httpService.get(uri);
        Ticket ticketResponse = gson.fromJson(response,Ticket.class);

        return ticketResponse;
    }
}
