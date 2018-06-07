package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.*;

import container.TicketsSystemContainer;
import model.*;
import service.ProjectService;
import service.TicketService;
import service.UserService;
import view.CreateProjectView;
import view.ProjectListView;

public class ProjectController extends Controller {

    private int SINGLE_CLICK = 1;
    private int DOUBLE_CLICK = 2;

    private ProjectListView projectListView;
    private CreateProjectView createProjectView;

	private Project selectedProject;
	private Ticket selectedTicket;

	private TicketController ticketController;

	private Vector<Project> projects;

	private TicketService ticketService = new TicketService();
	private UserService userService = new UserService();
	private ProjectService projectService = new ProjectService();

	public ProjectController(TicketsSystemContainer container) {
		super(container);

		projectListView = new ProjectListView();
		projectListView.initializeViewActionListeners(this);

		refreshProjectsList();

		ticketController = new TicketController(container);
	}

	public Vector<Project> loadProjects(){
		Vector<Project> projects = null;
		try {
			projects = projectService.getProjects();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return projects;
	}

	public Project loadProjectById(Project selectedProject){
		Project project = null;
		try {
			project = projectService.getProjectById(selectedProject);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return project;
	}

	public Vector<TicketType> loadTicketTypes(){
		Vector<TicketType> ticketsTypes = null;
		try {
			ticketsTypes = ticketService.getTypes();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ticketsTypes;
	}

	public Vector<User> loadUsers(){
		Vector<User> users = null;
		try {
			users = userService.getUsers();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return users;
	}

	public Vector<Role> loadRoles(){
		Vector<Role> roles = null;
		try {
			roles = userService.getRoles();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return roles;
	}

	public void showProjectDetailView(){
		Project project = loadProjectById(selectedProject);
		projectListView.setSelectedProject(project);
		projectListView.showProjectDetail();
	}

	public void refreshProjectsList(){
		projects = loadProjects();
		projectListView.setProjects(projects);
		projectListView.showProjectsList();
	}

	public void showProjectsListView()
    {
    	projectListView.show();
    }


	public boolean canUserChangeToState(Project project, TicketState state)
	{
		String role = project.getUserRole(getContainer().getCurrentUser().getName());
		return state.canChangeState(role);
	}
    
    public MouseListener getProjectsListSelectionListener() {
		ProjectController controller = this;

		class projectsListListener implements MouseListener
		{
			public void mouseClicked(MouseEvent mouseEvent)
			{
				JList list = (JList)mouseEvent.getSource();
				if (mouseEvent.getClickCount() == 1) 
				{
					int index = list.locationToIndex(mouseEvent.getPoint());
					if (index >= 0) {
						Project project = projects.elementAt(index);

						selectedProject = project;
						showProjectDetailView();
						selectedTicket = null;
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		}	
		return new projectsListListener();
	}

	public ActionListener getNewProjectListener() {

		ProjectController controller = this;

		class newProjectListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0)
			{
				Vector<TicketType> ticketsTypes = loadTicketTypes();
				Vector<User> users = loadUsers();
				Vector<Role> roles = loadRoles();

				createProjectView = new CreateProjectView(roles, users, ticketsTypes);
				createProjectView.initializeViewActionListeners(controller);
				createProjectView.showNewProjectForm();
			}
		}	
		return new newProjectListener();
	}

	public ActionListener getLogOutListener() {

		class newProjectListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0) {
				projectListView.closeWindow();
				getContainer().initialize();
			}
		}
		return new newProjectListener();
	}

    public ActionListener getShowProjectDetailListener() {

        ProjectController controller = this;

        class showProjectDetailListener implements ActionListener
        {
            public void actionPerformed(ActionEvent arg0)
            {
            	showProjectDetailView();
            }
        }
        return new showProjectDetailListener();
    }

	public ActionListener getNewTicketListener() {

		ProjectController controller = this;

		class newTicketListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0)
			{
                ticketController.setSelectedProject(selectedProject);
                ticketController.initializeViewActionListeners(controller);
			    ticketController.showTicketForm();
			}
		}
		return new newTicketListener();
	}

	public MouseListener getTicketClickedListener() {
		ProjectController controller = this;

		class ticketClickedListener implements MouseListener
		{
			@Override
			public void mouseClicked(MouseEvent mouseEvent)
			{
				JList list = (JList)mouseEvent.getSource();
				if (mouseEvent.getClickCount() == SINGLE_CLICK)
				{
					int index = list.locationToIndex(mouseEvent.getPoint());
					if (index >= 0) {
						selectedTicket = (Ticket)list.getModel().getElementAt(index);
					}
				}
				else if (mouseEvent.getClickCount() == DOUBLE_CLICK)
				{
					int index = list.locationToIndex(mouseEvent.getPoint());
					if (index >= 0) {
						selectedTicket = (Ticket)list.getModel().getElementAt(index);
						ticketController.setSelectedTicket(selectedTicket);
						ticketController.showTicketDetail();
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}
		}
		return new ticketClickedListener();
	}

	public ActionListener getChangeTicketStateListener(Project project, TicketState ticketState) {
		ProjectController controller = this;

		class changeTicketStateListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0)
			{

				if((selectedTicket != null) && (selectedTicket.isCurrentState(ticketState.getName()))) {

					TicketState nextState = project.getNextTicketState(selectedTicket.getCurrentState());
					TicketService ticketService = new TicketService();
					try {
						ticketService.putChangeState(selectedTicket.getID(),nextState);
					} catch (IOException e) {
						e.printStackTrace();
					}

					showProjectDetailView();
				}
			}
		}	
		return new changeTicketStateListener();
	}

	public ActionListener getCreateProjectListener() {
		ProjectController controller = this;

		class createProjectListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0) {

				if (this.validateNameProject()) {

					this.createProject();

				} else {

					createProjectView.confirmDialog();

				}


			}

			private boolean validateNameProject(){

				String nameProject = createProjectView.getName();

				if (nameProject.equals("")) {
					createProjectView.setErrorName(" Insert name");
				}

				return !nameProject.equals("");

			}

			private void createProject() {

				ProjectService projectService = new ProjectService();

				Project project = new Project();

				String nameProject = createProjectView.getName();

				project.setName(nameProject);

				String owner = getContainer().getCurrentUser().getName();
				project.setOwner(owner);

				Vector<TicketType> ticketTypesList = createProjectView.getTicketTypesRequiredFields();

				project.setTicketTypes(ticketTypesList);

				Vector<TicketState> ticketStates = createProjectView.getTicketStates();
				project.setTicketStates(ticketStates);

				Vector<User> selectedUsers = createProjectView.getSelectedUsers();

				project.setUsers(selectedUsers);

				try {
					projectService.postProject(project);
				} catch (IOException e) {
					e.printStackTrace();
				}

				refreshProjectsList();

			}

		}
		return new createProjectListener();
	}

}
