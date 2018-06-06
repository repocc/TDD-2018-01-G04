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

	private TicketService ticketService = new TicketService();
	private UserService userService = new UserService();

	public ProjectController(Model model, TicketsSystemContainer container) {
		super(model, container);
		projectListView = new ProjectListView(model);
		projectListView.initializeViewActionListeners(this);

		ticketController = new TicketController(getModel(), container);
	}
	
	public void showView()
    {
    	projectListView.showView();
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
						Project project = (Project)list.getModel().getElementAt(index);
						String name = project.toString();
						selectedProject = project;
						projectListView.showProjectDetail(name, controller);
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
				Vector<TicketType> ticketsTypes = null;
				try {
					ticketsTypes = ticketService.getTypes();
				} catch (IOException e) {
					e.printStackTrace();
				}

				Vector<User> users = null;
				try {
					users = userService.getUsers();
				} catch (IOException e) {
					e.printStackTrace();
				}

				Vector<Role> roles = null;
				try {
					roles = userService.getRoles();
				} catch (IOException e) {
					e.printStackTrace();
				}

				createProjectView = new CreateProjectView(getModel(), roles, users, ticketsTypes);
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
                projectListView.showProjectDetail(selectedProject.toString(),controller);
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

					System.out.println(selectedTicket.getCurrentState());
					TicketState nextState = project.getNextTicketState(selectedTicket.getCurrentState());
					TicketService ticketService = new TicketService();
					try {
						ticketService.putChangeState(selectedTicket.getID(),nextState);
					} catch (IOException e) {
						e.printStackTrace();
					}

					projectListView.showProjectDetail(selectedProject.getName(), controller);

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
				ProjectService projectService = new ProjectService();

				Project project = new Project();

				String nameProject = createProjectView.getName();

				project.setName(nameProject);

				String owner = getModel().getCurrentUser().getName();
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

				projectListView.showProjectsList();
			}
		}
		return new createProjectListener();
	}

}
