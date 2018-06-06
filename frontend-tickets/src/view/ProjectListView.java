package view;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import controller.ProjectController;
import model.*;

public class ProjectListView extends View {

	private JFrame window;
	private JButton newProjectButton = new JButton("New Project");
	private JButton newTicketButton = new JButton("New Ticket");
	private JButton logOutButton = new JButton("Log Out");
	private JList projectsList = new JList();
	private JScrollPane projectsListScroller;
	private JPanel ticketsListMainPanel = new JPanel(new BorderLayout());

	public ProjectListView(Model model) {
		super(model);
	}
	
	public void showView() {
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
		ticketButtonPanel.add(newTicketButton, BorderLayout.WEST);
		newTicketButton.setVisible(false);

		ticketButtonPanel.add(logOutButton, BorderLayout.EAST);

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
	
	public void initializeViewActionListeners(ProjectController controller) {
        projectsList.addMouseListener(controller.getProjectsListSelectionListener());
        newProjectButton.addActionListener(controller.getNewProjectListener());
        newTicketButton.addActionListener(controller.getNewTicketListener());
        logOutButton.addActionListener(controller.getLogOutListener());
	}

	public void showProjectsList() {
		projectsList.setVisible(true);

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
	
	public void showProjectDetail(String name, ProjectController controller) {

		Vector<Ticket> tickets = (getModel().getTicketsFromProject(name));
		Vector<TicketState> states = (getModel().getStatesFromProject(name));
		Project project = getModel().getProjet(name);

		ticketsListMainPanel.removeAll();

		if (states != null){
			Iterator iStates = states.iterator();

			while(iStates.hasNext())
			{
				DefaultListModel model = new DefaultListModel();
				TicketState state = (TicketState)iStates.next();

				if (tickets != null){
					Iterator iTickets = tickets.iterator();

					while(iTickets.hasNext())
					{
						Ticket ticket = (Ticket)iTickets.next();
						if(ticket.isCurrentState(state.getName()))
						{
							model.addElement(ticket);
						}
					}
				}

				JList<Ticket> ticketsList = new JList<>(model);
				ticketsList.setVisible(true);
				ticketsList.addMouseListener(controller.getTicketClickedListener());

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
					changeStateButton.addActionListener(controller.getChangeTicketStateListener(project,state));
					if(!getModel().canUserChangeToState(project,state))
					{
						changeStateButton.setEnabled(false);
					}
					container.add(changeStateButton);
				}

				//Border around the container showing the state
				container.setBorder(BorderFactory.createTitledBorder(state.getName()));

				ticketsListMainPanel.add(container);
			}
		}

		newTicketButton.setVisible(true);

		ticketsListMainPanel.revalidate();
		ticketsListMainPanel.repaint();
	}
}
