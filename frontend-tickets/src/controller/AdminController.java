package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.AdminView;
import model.Model;

public class AdminController extends Controller {

	private AdminView view;
	
	public AdminController(Model model)
	{
		super(model);
		view = new AdminView(model);
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

	public ActionListener getNewProjectListener()
	{
		class newProjectListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0)
			{
				System.out.println("New Project!");
			}
		}	
		return new newProjectListener();	
	}

	public ActionListener getUsersListener()
	{
		class usersListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0)
			{
				System.out.println("Users!");
			}
		}	
		return new usersListener();
	}
	
	
}
