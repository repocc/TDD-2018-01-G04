package controller;

import model.Model;
import model.User;
import service.UserService;

import java.io.IOException;

public abstract class Controller{

    private Model model;
    private TicketsSystem container;

    public Controller(Model model, TicketsSystem container)
    {
        this.model = model;
        this.container = container;
    }

    public abstract void showView();

    public Model getModel()
    {
    	return model;
    }

    public TicketsSystem getContainer()
    {
        return container;
    }
       
}
