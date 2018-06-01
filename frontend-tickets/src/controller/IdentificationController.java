package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Model;
import view.IdentificationView;

public class IdentificationController extends Controller {
	
	private IdentificationView view;

	public IdentificationController(Model model)
	{
		super(model);
		view = new IdentificationView(model);
		view.initializeViewActionListeners(this);
	}
	
    public void showView()
    {
    	view.showView();
    }
	
	public ActionListener getLoginListener()
	{
		class loginListener implements ActionListener
		{

			public void actionPerformed(ActionEvent arg0)
			{
				if(view.isFieldEmpty())
				{
					view.closeWindow();
					view.showView();
				}	
				else if (authenticateUser(view.getUsername()))
				{
					view.closeWindow();
					notifyContextLogin();
				}
				else
				{
					view.closeWindow();
					view.showView();
				}
			}
		}	
		return new loginListener();
	}
	
	public ActionListener getCancelListener()
	{
		class cancelListener implements ActionListener
		{

			public void actionPerformed(ActionEvent arg0)
			{
				view.closeWindow();
			}
		}	
		return new cancelListener();
	}
	
}
