package view;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.AdminController;
import model.Model;

public class AdminView extends View {

	private JFrame window;
	private JButton viewProjectsButton = new JButton("Projects");
	private JButton newProjectButton = new JButton("New Project");
	private JButton usersButton = new JButton("Users");
	
	public AdminView(Model model)
	{
		super(model);
	}
	
	public void showView()
	{
		window = new JFrame("Tickets System");
		window.setLayout(new GridLayout(1, 1));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(viewProjectsButton);
		buttonPanel.add(newProjectButton);
		buttonPanel.add(usersButton);
		
		window.add(buttonPanel);
		
		window.pack();
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
	
	public void closeWindow()
	{
		window.dispose();
	}
	
	public void initializeViewActionListeners(AdminController controller)
	{
		initViewProjectsButton(controller.getProjectsListener());
		initNewProjectButton(controller.getNewProjectListener());
		initUsersButton(controller.getUsersListener());
	}
	
	public void initViewProjectsButton(ActionListener listener)
	{
		viewProjectsButton.addActionListener(listener);
	}
	
	public void initNewProjectButton(ActionListener listener)
	{
		newProjectButton.addActionListener(listener);
	}
	
	public void initUsersButton(ActionListener listener)
	{
		usersButton.addActionListener(listener);
	}

}
