package service;

import com.google.gson.Gson;
import model.Ticket;

import java.io.IOException;

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

    public String getTypes() throws IOException {

        String response = this.httpService.get(Consts.URI_GET_TYPES_TICKETS);

        return response;

    }

}
