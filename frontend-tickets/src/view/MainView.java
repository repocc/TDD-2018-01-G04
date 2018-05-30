package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import controller.MainController;
import model.Model;
import model.Project;
import model.Ticket;

public class MainView extends View {

	private JFrame window;
	private JButton newProjectButton = new JButton("New Project");
	private JList projectsList = new JList();
	private JList ticketsList = new JList();
	private JScrollPane projectsListScroller;
	private JScrollPane ticketsListScroller;

	public MainView(Model model) {
		super(model);
	}
	
	public void showView()
	{
		window = new JFrame("Tickets System");
		window.setPreferredSize(new Dimension(800, 600));
		
		window.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 0.5;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(newProjectButton, c);
		
		window.add(buttonPanel);
		
		projectsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		projectsList.setLayoutOrientation(JList.VERTICAL);
		projectsList.setVisibleRowCount(-1);
		showProjectsList();

		projectsListScroller = new JScrollPane(projectsList);
		c.gridx = 1;
		c.gridwidth = 1;
		c.gridheight = 5;
		c.gridy = 0;
		c.weighty = 1.0;
        c.weightx = 0.25;
		window.add(projectsListScroller, c);
		
		//TODO: Change the way tickets display according to state
		ticketsListScroller = new JScrollPane(ticketsList);
		c.gridx = 2;
		c.gridwidth = 3;
		c.gridy = 0;
		c.weighty = 1.0;
        c.weightx = 1.0;
		window.add(ticketsListScroller, c);
		
		window.pack();
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
	
	public void closeWindow()
	{
		window.dispose();
	}
	
	public void initializeViewActionListeners(MainController controller)
	{
		initProjectsListListener(controller.getListSelectionListener());
		initNewProjectButton(controller.getNewProjectListener());
		
		initNewProjectMenuListeners(controller);
	}
	
	public void initNewProjectButton(ActionListener listener)
	{
		newProjectButton.addActionListener(listener);
	}
	
	public void initProjectsListListener(MouseListener listener)
	{
		projectsList.addMouseListener(listener);
	}

	public void initNewProjectMenuListeners(MainController controller)
	{
		//Ej: button1.addActionListener(controller.getButton1Listener()); ...
	}
	
	public void showProjectsList()
	{
		projectsList.setVisible(true);
		ticketsList.setVisible(true);
		
		Vector<Project> projects = (getModel().getProjects());
		
		DefaultListModel model = new DefaultListModel();
		
		Iterator i = projects.iterator();
		
		while(i.hasNext())
		{
			Project project = (Project)i.next();
			model.addElement(project);
		}
		projectsList.setModel(model);
	}
	
	public void showTicketsFromProject(String name)
	{
		
		Vector<Ticket> tickets = (getModel().getTicketsFromProject(name));
		
		DefaultListModel model = new DefaultListModel();
		
		Iterator i = tickets.iterator();
		
		while(i.hasNext())
		{
			Ticket ticket = (Ticket)i.next();
			model.addElement(ticket);
		}
		ticketsList.setModel(model);

	}
	
	private JPanel createLabelWith(String label, JComponent component)
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		panel.add(new JLabel(label));
		panel.add(component);
		return panel;
	}

	public void showNewTicketMenu()
	{
		JTextField titleText = new JTextField(30);
		JTextField descriptionText = new JTextField(30);
		JTextField typeText = new JTextField(30);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(4, 1));
		mainPanel.add(createLabelWith("Title:", titleText));
		mainPanel.add(createLabelWith("Description:", descriptionText));
		mainPanel.add(createLabelWith("Type:", typeText));
		
		int result = JOptionPane.showConfirmDialog(null, mainPanel, "New Ticket", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION)
		{
			System.out.println("Title: " + titleText.getText());
		    System.out.println("Description: " + descriptionText.getText());
		}
	}
	
	public void showNewProjectMenu()
	{
		JTextField nameText = new JTextField(30);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(3, 1));
		
		mainPanel.add(createLabelWith("Project name:", nameText));
	
		int result = JOptionPane.showConfirmDialog(null, mainPanel, "New Project", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION)
		{
			System.out.println("Project name: " + nameText.getText());
		}
	}

}
