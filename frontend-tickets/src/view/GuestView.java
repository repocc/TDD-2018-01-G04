package view;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.GuestController;
import model.Model;

public class GuestView extends View {

	private JFrame window;
	private JButton viewProjectsButton = new JButton("Projects");
	
	public GuestView(Model model) {
		super(model);
	}
	
	public void showView()
	{
		window = new JFrame("Tickets System");
		window.setLayout(new GridLayout(1, 1));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(viewProjectsButton);
		
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
	
	public void initializeViewActionListeners(GuestController controller)
	{
		initViewProjectsButton(controller.getProjectsListener());
	}
	
	public void initViewProjectsButton(ActionListener listener)
	{
		viewProjectsButton.addActionListener(listener);
	}	
}
