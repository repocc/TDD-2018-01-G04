package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import model.*;
import view.MainView;

public class MainController extends Controller {
	
	private MainView view;
	private Project selectedProject;
	private Ticket selectedTicket;

	public MainController(Model model)
	{
		super(model);
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
		class newProjectListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0)
			{
				view.showNewProjectMenu();
			}
		}	
		return new newProjectListener();
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

	public ActionListener getChangeStateListener(TicketState ticketState)
	{
		MainController controller = this;

		class changeStateListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if((selectedTicket != null) && (selectedTicket.isCurrentState(ticketState.getName())))
				{
					getModel().changeTicketState(selectedTicket, selectedProject);
					view.showTicketsFromProject(selectedProject.getName(), controller);
				}
			}
		}	
		return new changeStateListener();
	}

	public ActionListener getPostCommentListener(JTextArea comment,JList commentsList)
	{
		MainController controller = this;

		class postCommentListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg)
			{
				if(comment.getText() != null)
				{
					Comment c = new Comment(getModel().getCurrentUser(), comment.getText());
					comment.setText(null);
					selectedTicket.addComment(c);
					view.putCommentsInList(commentsList, selectedTicket.getComments());
					//TODO: Post comment c
				}
			}
		}
		return new postCommentListener();
	}
}
