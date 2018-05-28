package controller;

import model.Model;

public class Controller{

    private Model model;

    public Controller(Model model)
    {
        this.model = model;
    }

    public void showView()
    {
    	
    }
    
    public boolean authenticateUser(String username)
	{
    	return model.authenticateUser(username);
	}
	
    public void notifyContextLogin()
    {
    	model.notifyContextLogin();
    }
       
}
