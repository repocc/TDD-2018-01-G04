package controller;

import model.Model;


public class TicketsSystem 
{	
    private Controller currentController;
    private Model currentModel;

    public TicketsSystem() 
    {
        this.currentModel = new Model();
    }
    
    public void initialize()
    {
    	this.initializeUserController();
    }

    private void initializeUserController(){
        this.currentController = new UserController(this.currentModel, this);
        this.currentController.showView();
    }

    public void initializeProjectController(){
        this.currentController = new MainController(this.currentModel, this);
        this.currentController.showView();
    }

}
