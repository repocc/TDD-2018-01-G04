package container;

import controller.ProjectController;
import controller.UserController;
import model.Project;
import model.TicketState;
import model.User;


public class TicketsSystemContainer
{
    private UserController userController;
    private ProjectController projectController;

    private User currentUser;

    public TicketsSystemContainer()
    {

    }
    
    public void initialize()
    {
    	this.initializeUserController();
    }

    private void initializeUserController(){
        this.userController = new UserController(this);
        this.userController.showLoginView();
    }

    public void initializeProjectController(){
        this.projectController = new ProjectController(this);
        this.projectController.showProjectsListView();
    }

    public void setCurrentUser(User user) {

        currentUser = user;
    }

    public User getCurrentUser()
    {
        return this.currentUser;
    }

}
