package controller;

public class ControllerContext
{

	private Controller controller;
	
	public void setController(Controller controller)
	{
		this.controller = controller;
	}
	
	public void start()
	{
		controller.showView();
	}
	
}
