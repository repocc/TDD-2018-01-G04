package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import model.Model;
import model.User;
import service.UserService;
import view.LoginView;

public class UserController extends Controller {
	
	private LoginView view;
	private UserService userService;

	public UserController(Model model)
	{
		super(model);
		userService = new UserService();
		view = new LoginView(model);
		view.initializeViewActionListeners(this);
	}
	
    public void showView()
    {
    	view.showView();
    }

    public boolean authenticateUser(String username)
    {
        User user = new User(username);
        try {
            user = userService.postLogin(user);
        } catch (IOException e) {
            e.printStackTrace();
        }

        getModel().authenticateUser(user);

        return user != null;
    }

    public void notifyContextLogin()
    {
        getModel().notifyContextLogin();
    }
	
	public ActionListener getLoginListener()
	{
		class loginListener implements ActionListener
		{

			public void actionPerformed(ActionEvent arg0)
			{
				if(view.isFieldEmpty())
				{
					view.setError("Insert username");
				}
				else if (authenticateUser(view.getUsername()))
				{
					notifyContextLogin();
				}
				else
				{
					view.setError("Insert correct username");
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
