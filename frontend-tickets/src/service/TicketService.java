package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Ticket;
import model.TicketState;
import model.TicketTypes;

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

    public Vector<TicketTypes> getTypes() throws IOException {

        String ticketTypesJson = this.baseService.get(Consts.URI_GET_TYPES_TICKETS);
        Type listType = new TypeToken<Vector<TicketTypes>>(){}.getType();
        Vector<TicketTypes> ticketTypes = gson.fromJson(ticketTypesJson,listType);

        return ticketTypes;

    }

}
