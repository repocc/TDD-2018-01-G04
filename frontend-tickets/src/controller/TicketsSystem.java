package controller;

import model.Model;


public class TicketsSystem 
{	
    private ControllerContext context;

    public TicketsSystem() 
    {
    	
    }
    
    public void initialize()
    {
    	context = new ControllerContext();
    	Model model = new Model(context);
    	Controller controller = new UserController(model);
		
		context.setController(controller); 
		context.start();

    }

}
