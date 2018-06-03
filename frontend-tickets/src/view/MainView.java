package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.Border;

import controller.MainController;
import model.*;
import service.ProjectService;
import service.TicketService;

public class MainView extends View {

	private JFrame window;
	private JButton newProjectButton = new JButton("New Project");
	private JButton newTicketButton = new JButton("New Ticket");
	private JList projectsList = new JList();
	private JScrollPane projectsListScroller;
	private JPanel ticketsListMainPanel = new JPanel(new BorderLayout());

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
		
		ticketsListMainPanel.setLayout(new GridLayout(2, 2, 10, 10));

		JPanel ticketsGeneralPanel = new JPanel();
		ticketsGeneralPanel.setLayout(new BorderLayout());

		JPanel ticketButtonPanel = new JPanel();
		ticketButtonPanel.setLayout(new BorderLayout());
		ticketButtonPanel.add(newTicketButton, BorderLayout.EAST);
		newTicketButton.setVisible(false);

		ticketsGeneralPanel.add(ticketButtonPanel,BorderLayout.NORTH);
		ticketsGeneralPanel.add(ticketsListMainPanel,BorderLayout.CENTER);


		c.gridx = 2;
		c.gridwidth = 3;
		c.gridy = 0;
		c.weighty = 1.0;
        c.weightx = 1.0;
		window.add(ticketsGeneralPanel, c);
		
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
		initProjectsListListener(controller.getProjectsListSelectionListener());
		initNewProjectButton(controller.getNewProjectListener());
		initNewTicketButton(controller.getNewTicketListener());
		
		initNewProjectMenuListeners(controller);
	}
	
	public void initNewProjectButton(ActionListener listener)
	{
		newProjectButton.addActionListener(listener);
	}

	public void initNewTicketButton(ActionListener listener)
	{
		newTicketButton.addActionListener(listener);
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
		//ticketsList.setVisible(true);
		
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
	
	public void showTicketsFromProject(String name, MainController controller)
	{
		Vector<Ticket> tickets = (getModel().getTicketsFromProject(name));
		Vector<TicketState> states = (getModel().getStatesFromProject(name));
		
		Iterator iStates = states.iterator();
		
		ticketsListMainPanel.removeAll();
		
		while(iStates.hasNext())
		{
			DefaultListModel model = new DefaultListModel();
			TicketState state = (TicketState)iStates.next();
			Iterator iTickets = tickets.iterator();
			
			while(iTickets.hasNext())
			{
				Ticket ticket = (Ticket)iTickets.next();
				if(ticket.isCurrentState(state.getName()))
				{
					model.addElement(ticket);
				}
			}
			JList<Ticket> ticketsList = new JList<>(model);
			ticketsList.setVisible(true);
			ticketsList.addMouseListener(controller.getTicketLabelListener());
			
			JScrollPane scrollPanel = new JScrollPane(ticketsList);
			scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

			//Container for jscrollpane and change state button
            JPanel container = new JPanel();
            container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
            container.add(scrollPanel);

            //Change state button
            if(iStates.hasNext())
            {
				JButton changeStateButton = new JButton(">");
				changeStateButton.addActionListener(controller.getChangeStateListener(state));
				if(!getModel().canUserChangeToState(state))
				{
					changeStateButton.setEnabled(false);
				}
				container.add(changeStateButton);
			}

			//Border around the container showing the state
			container.setBorder(BorderFactory.createTitledBorder(state.getName()));

			ticketsListMainPanel.add(container);
		}

		newTicketButton.setVisible(true);

		ticketsListMainPanel.revalidate();
		ticketsListMainPanel.repaint();
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
			String state = "OPEN";
			Ticket ticket = new Ticket(titleText.getText(),descriptionText.getText(),typeText.getText(),state);
			TicketService service = new TicketService();
			try {
				service.postTicket(ticket);
			} catch (IOException e) {
				e.printStackTrace();
			}
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
			Vector<TicketState> states3 = new Vector<TicketState>();
			Vector<String> roles3 = new Vector<String>();
			Vector<String> roles = new Vector<String>();
			roles3.add("guest");
			roles.add("admin");
			roles.add("guest");
			states3.add(new TicketState("OPEN", roles));
			states3.add(new TicketState("IN PROGRESS", roles));
			states3.add(new TicketState("QA", roles3));
			states3.add(new TicketState("CLOSED", null));
			Vector<User> users = new Vector<User>();
			users.add(new User("Pepe"));
			users.add(new User("Dylan"));
			users.add(new User("Tom"));

			Project project = new Project(nameText.getText(), new User("Pepe"),users, states3);

			ProjectService service = new ProjectService();
			try {
				service.postProject(project);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	private GridBagConstraints createGbc(int x, int y) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;

		gbc.anchor = (x == 0) ? GridBagConstraints.WEST : GridBagConstraints.EAST;
		gbc.fill = (x == 0) ? GridBagConstraints.BOTH
				: GridBagConstraints.HORIZONTAL;

		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.weightx = (x == 0) ? 0.1 : 1.0;
		gbc.weighty = 1.0;
		return gbc;
	}

	public void showTicketDetails(Ticket ticket, MainController controller)
	{
		JPanel mainPanel = createTicketDetails(ticket, controller);
		JOptionPane.showMessageDialog(window, mainPanel, "Ticket", JOptionPane.INFORMATION_MESSAGE);
	}

	public JPanel createTicketDetails(Ticket ticket, MainController controller)
	{
		JPanel informationPanel = new JPanel();
		informationPanel.setLayout(new GridLayout(0, 2));

		if (!ticket.getName().isEmpty()){
			informationPanel.add(new JLabel("Title: "));
			informationPanel.add(new JLabel(ticket.getName()));
		}
		if (!ticket.getDescription().isEmpty()){
			informationPanel.add(new JLabel("Description: " ));
			informationPanel.add(new JLabel(ticket.getDescription()));
		}
		if (!ticket.getType().isEmpty()){
			informationPanel.add(new JLabel("Type: " ));
			informationPanel.add(new JLabel(ticket.getType()));
		}

		informationPanel.add(new JLabel("State: " ));
		informationPanel.add(new JLabel(ticket.getCurrentState()));

		JPanel usersCommentsPanel = new JPanel();
		usersCommentsPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = createGbc(0, 0);

		usersCommentsPanel.add(new JLabel("Comments: "), c);

		Vector<Comment> comments = ticket.getComments();

		JList commentsList = new JList();
		commentsList.setVisibleRowCount(3);
		commentsList.setFixedCellHeight(35);

		putCommentsInList(commentsList, comments);

		JScrollPane commentsScrollPane = new JScrollPane(commentsList);

		c = createGbc(0, 1);
		usersCommentsPanel.add(commentsScrollPane, c);

		JLabel commentLabel = new JLabel("New comment: " );
		commentLabel.setHorizontalAlignment(JLabel.LEFT);

		JTextArea commentArea = new JTextArea(4, 30);
		commentArea.setLineWrap(true);
		commentArea.setWrapStyleWord(true);
		commentArea.setBorder(new JTextField().getBorder());
		commentArea.setText(null);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());

		JButton postButton = new JButton("Post");
		postButton.addActionListener(controller.getPostCommentListener(commentArea, commentsList));

		c = createGbc(0, 0);
		mainPanel.add(informationPanel, c);
		c = createGbc(0, 1);
		c.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(usersCommentsPanel, c);
		c = createGbc(0, 2);
		mainPanel.add(commentLabel, c);
		c = createGbc(0, 3);
		mainPanel.add(commentArea,c);
		c = createGbc(0, 4);
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.EAST;
		mainPanel.add(postButton, c);

		return mainPanel;
	}

	public JList putCommentsInList(JList list, Vector<Comment> comments)
	{
		DefaultListModel model = new DefaultListModel();
		Iterator iComments = comments.iterator();

		while(iComments.hasNext())
		{
			Comment comment = (Comment)iComments.next();
			model.addElement(comment);
		}
		list.setModel(model);
		return list;
	}
}
