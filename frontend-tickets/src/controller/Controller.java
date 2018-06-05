package controller;

import model.Model;
import model.User;
import service.UserService;

import java.io.IOException;

public abstract class Controller{

    private Model model;

    public Controller(Model model)
    {
        this.model = model;
    }

    public abstract void showView();

    public Model getModel()
    {
    	return model;
    }
       
}
