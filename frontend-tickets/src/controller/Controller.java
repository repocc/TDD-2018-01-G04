package controller;

import container.TicketsSystemContainer;
import model.Model;

public abstract class Controller{

    private TicketsSystemContainer container;

    public Controller(TicketsSystemContainer container)
    {
        this.container = container;
    }

    public TicketsSystemContainer getContainer()
    {
        return container;
    }
       
}
