package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

}
