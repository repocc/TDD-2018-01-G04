package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import container.TicketsSystemContainer;
import model.User;
import service.UserService;
import view.LoginView;

public class UserController extends Controller {
	
	private LoginView loginView;
	private UserService userService;

	public UserController(TicketsSystemContainer container) {
		super(container);
		userService = new UserService();
		loginView = new LoginView();
		loginView.initializeViewActionListeners(this);
	}
	
    public void showLoginView() {

    	loginView.show();
    }

    public boolean authenticateUser(String username) {
        User user = new User(username);
        try {
            user = userService.postLogin(user);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.getContainer().setCurrentUser(user);

        return user != null;
    }

    public void notifyContextLogin() {

    	this.getContainer().initializeProjectController();
    }
	
	public ActionListener getLoginListener() {
		class loginListener implements ActionListener
		{

			public void actionPerformed(ActionEvent arg0)
			{
				if(loginView.isFieldEmpty())
				{
					loginView.setError("Insert username");
				}
				else if (authenticateUser(loginView.getUsername()))
				{
                    loginView.closeWindow();
					notifyContextLogin();
				}
				else
				{
					loginView.setError("Insert correct username");
				}
			}
		}	
		return new loginListener();
	}
	
	public ActionListener getCancelListener() {
		class cancelListener implements ActionListener
		{

			public void actionPerformed(ActionEvent arg0)
			{
				loginView.closeWindow();
			}
		}	
		return new cancelListener();
	}
	
}
