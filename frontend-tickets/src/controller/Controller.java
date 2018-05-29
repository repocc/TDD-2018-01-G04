package controller;

import model.Model;

public abstract class Controller{

    private Model model;

    public Controller(Model model)
    {
        this.model = model;
    }

    public abstract void showView();
    
    public boolean authenticateUser(String username)
	{
    	return model.authenticateUser(username);
	}
	
    public void notifyContextLogin()
    {
    	model.notifyContextLogin();
    }
    
    public Model getModel()
    {
    	return model;
    }
       
}
