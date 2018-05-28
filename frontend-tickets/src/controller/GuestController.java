package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.GuestView;
import model.Model;

public class GuestController extends Controller {
	
	private GuestView view;

	public GuestController(Model model)
	{
		super(model);
		view = new GuestView(model);
		view.initializeViewActionListeners(this);
	}
	
	public void showView()
    {
    	view.showView();
    }
	
    public ActionListener getProjectsListener()
	{
		class projectsListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0)
			{
				System.out.println("Projects!");
			}
		}	
		return new projectsListener();
	}


}
