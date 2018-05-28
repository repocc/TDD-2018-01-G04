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
    	Controller controller = new IdentificationController(model);
		
		context.setController(controller); 
		context.start();

    }

}
