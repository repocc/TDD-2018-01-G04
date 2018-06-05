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
    
    public boolean authenticateUser(String username)
	{

        UserService service = new UserService();
        User user = new User(username);
        try {
            user = service.postLogin(user);
        } catch (IOException e) {
            e.printStackTrace();
        }

        model.authenticateUser(user);

    	return user != null;
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
