package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import view.MainView;
import model.Model;
import model.Ticket;

public class MainController extends Controller {
	
	private MainView view;
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
		class projectsListListener implements MouseListener
		{
			public void mouseClicked(MouseEvent mouseEvent)
			{
				JList list = (JList)mouseEvent.getSource();
				if (mouseEvent.getClickCount() == 1) 
				{
					int index = list.locationToIndex(mouseEvent.getPoint());
					if (index >= 0) {
						String name = list.getModel().getElementAt(index).toString();
						view.showTicketsFromProject(name);
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

	public MouseListener getTicketLabelListener() {
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

	public ActionListener getChangeStateListener() {
		class changeStateListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0)
			{
				System.out.println(selectedTicket);
			}
		}	
		return new changeStateListener();
	}
    
    
}
