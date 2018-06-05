package controller;

import container.TicketsSystemContainer;
import model.Model;

public abstract class Controller{

    private Model model;
    private TicketsSystemContainer container;

    public Controller(Model model, TicketsSystemContainer container)
    {
        this.model = model;
        this.container = container;
    }

    public abstract void showView();

    public Model getModel() { return model; }

    public TicketsSystemContainer getContainer()
    {
        return container;
    }
       
}
