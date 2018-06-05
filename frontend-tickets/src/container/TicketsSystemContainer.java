package container;

import controller.Controller;
import controller.MainController;
import controller.UserController;
import model.Model;


public class TicketsSystemContainer
{	
    private Controller currentController;
    private Model currentModel;

    public TicketsSystemContainer()
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
