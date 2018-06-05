package controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import model.*;
import service.ProjectService;
import service.TicketService;
import view.MainView;

public class MainController extends Controller {
	
	private MainView view;
	private Project selectedProject;
	private Ticket selectedTicket;

	public MainController(Model model, TicketsSystem container)
	{
		super(model, container);
		view = new MainView(model);
		view.initializeViewActionListeners(this);
	}
	
	public void showView()
    {
    	view.showView();
    }
    
    public MouseListener getProjectsListSelectionListener()
	{
		MainController controller = this;

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
						view.showTicketsFromProject(name, controller);
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

		MainController controller = this;

		class newProjectListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0)
			{
				view.showNewProjectMenu(controller);
			}
		}	
		return new newProjectListener();
	}

	public ActionListener getLogOutListener() {

		class newProjectListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0) {
				view.closeWindow();
				//TODO: check better way
				TicketsSystem ticketsSystem = new TicketsSystem();
				ticketsSystem.initialize();
			}
		}
		return new newProjectListener();
	}


	public ActionListener getAssignUserTicket() {

		class newProjectListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0) {

				JRadioButton radioButton = (JRadioButton) arg0.getSource();

				view.assignUser(radioButton.getText());
			}
		}
		return new newProjectListener();
	}

	public ActionListener getAssignTypeTicket() {

		class newProjectListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0) {

				JRadioButton radioButton = (JRadioButton) arg0.getSource();

				view.assignType(radioButton.getText());
			}
		}
		return new newProjectListener();
	}

	public ActionListener getNewTicketListener() {

		MainController controller = this;

		class newTicketListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0)
			{
				view.showNewTicketMenu(selectedProject,controller);
			}
		}
		return new newTicketListener();
	}

	public MouseListener getTicketLabelListener()
	{
		MainController controller = this;

		class ticketLabelListener implements MouseListener
		{
			@Override
			public void mouseClicked(MouseEvent mouseEvent)
			{
				JList list = (JList)mouseEvent.getSource();
				if (mouseEvent.getClickCount() == 1) 
				{
					int index = list.locationToIndex(mouseEvent.getPoint());
					if (index >= 0) {
						selectedTicket = (Ticket)list.getModel().getElementAt(index);
					}
				}
				else if (mouseEvent.getClickCount() == 2)
				{
					int index = list.locationToIndex(mouseEvent.getPoint());
					if (index >= 0) {
						selectedTicket = (Ticket)list.getModel().getElementAt(index);
						view.showTicketDetails(selectedTicket, controller);
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
		return new ticketLabelListener();
	}

	public ActionListener getChangeStateListener(Project project, TicketState ticketState)
	{
		MainController controller = this;

		class changeStateListener implements ActionListener
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

					view.showTicketsFromProject(selectedProject.getName(), controller);

				}
			}
		}	
		return new changeStateListener();
	}

	public ActionListener getPostCommentListener(JTextArea commentArea,JList commentsList)
	{
		MainController controller = this;

		class postCommentListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg)
			{
				if(commentArea.getText() != null)
				{
					Comment comment = new Comment(getModel().getCurrentUser(), commentArea.getText(), selectedTicket);
					commentArea.setText(null);
					selectedTicket.addComment(comment);

					TicketService service = new TicketService();
					try {
						service.postComment(comment);
					} catch (IOException e) {
						e.printStackTrace();
					}

					view.putCommentsInList(commentsList, view.getTicketComments(selectedTicket));
				}
			}
		}
		return new postCommentListener();
	}

	public ActionListener getRoleSelectedListener(JCheckBox check,JComboBox comboBox){

		class postRoleSelectedListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg){

				String userName = check.getText();
				if (check.isSelected()) {
					String role = (String) comboBox.getSelectedItem();
					view.putUserSelect(userName,role);
				} else {
					view.removeUserSelect(userName);
				}
			}
		}
		return new postRoleSelectedListener();
	}

	public ActionListener getFieldRequiredListener(String type) {

		class postFieldRequiredListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg){
				JCheckBox checkBox = (JCheckBox) arg.getSource();
				String field = checkBox.getText();
				if (checkBox.isSelected()) {
					view.putFieldRequired(type,field);
				} else {
					view.removeFieldRequired(type,field);
				}
			}
		}
		return new postFieldRequiredListener();
	}

	public ActionListener getRolesChangeStateListener(String state) {

		class postRolesChangeStateListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg){
				JCheckBox check = (JCheckBox) arg.getSource();
				String role = check.getText();
				if (check.isSelected()) {
					view.putRolesChangeState(state,role);
				} else {
					view.removeRolesChangeState(state,role);
				}
			}
		}
		return new postRolesChangeStateListener();
	}


	public ActionListener getAddStateListener(JTextField nameText, FlowStates flowStates, JPanel panel){

		MainController controller = this;

		class postNewStateListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg){
				if (!nameText.getText().equals("")){
					flowStates.setState(nameText.getText());
					view.addPanelNewState(controller,panel,nameText.getText());
					nameText.setText("");
				}
			}
		}
		return new postNewStateListener();

	}

}
