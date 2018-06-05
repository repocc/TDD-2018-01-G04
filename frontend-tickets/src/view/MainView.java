package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.*;

import javax.swing.*;

import controller.MainController;
import model.*;
import service.ProjectService;
import service.TicketService;
import service.UserService;

public class MainView extends View {

	private JFrame window;
	private JButton newProjectButton = new JButton("New Project");
	private JButton newTicketButton = new JButton("New Ticket");
	private JButton logOutButton = new JButton("Log Out");
	private JList projectsList = new JList();
	private JScrollPane projectsListScroller;
	private JPanel ticketsListMainPanel = new JPanel(new BorderLayout());
	private JPanel projectMenuPanel = new JPanel();

	//create project
	private Map<String,String> selectUsers =new HashMap<>();
	private Map<String,HashSet<String>> fieldsRequired = new HashMap<>();
	private FlowState flowStates = new FlowState();

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
	
	public void initializeViewActionListeners(MainController controller)
	{
        projectsList.addMouseListener(controller.getProjectsListSelectionListener());

        newProjectButton.addActionListener(controller.getNewProjectListener());
        newTicketButton.addActionListener(controller.getNewTicketListener());

        logOutButton.addActionListener(controller.getLogOutListener());
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
	
	public void showTicketsFromProject(String name, MainController controller) {

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
	

	public void showNewProjectMenu(MainController controller) {

		projectMenuPanel.removeAll();

		JTextField nameText = new JTextField(30);

		projectMenuPanel.setLayout(new BoxLayout(projectMenuPanel, BoxLayout.Y_AXIS));

		projectMenuPanel.add(createLabelWith("Project name:", nameText));

		this.addSelectUsersNewProjectMenu(controller);

		this.addTicketsTypesNewProjectMenu(controller);

		this.addMenuAddNewState(controller);

		int result = JOptionPane.showConfirmDialog(null, projectMenuPanel, "New Project", JOptionPane.OK_CANCEL_OPTION);


		if (result == JOptionPane.OK_OPTION) {

			ProjectService projectService = new ProjectService();

			Project project = new Project();

			String nameProject = nameText.getText();

			project.setName(nameProject);

			String owner = getModel().getCurrentUser().getName();
			project.setOwner(owner);

			Vector<TicketType> ticketTypesList = this.getTicketTypeList();

			project.setTicketTypes(ticketTypesList);

			Vector<TicketState> ticketStates = this.flowStates.getTicketStates();//this.getTicketStates();

			project.setTicketStates(ticketStates);

			Vector<User> selectedUsers = getSelectedUser();

			project.setUsers(selectedUsers);

			try {
				projectService.postProject(project);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		flowStates.clear();

		showProjectsList();

	}

	private Vector<User> getSelectedUser() {

		Vector<User> selectedUsers = new Vector<>();

		for(Map.Entry m:selectUsers.entrySet()){

			String userID = (String) m.getKey();
			Role userRole = new Role((String) m.getValue());

			selectedUsers.add(new User(userID,userRole,userID));

		}

		this.selectUsers.clear();

		return selectedUsers;
	}

	private Vector<TicketType> getTicketTypeList(){

		Vector<TicketType> ticketTypesList = new Vector<>();

		for(Map.Entry m:this.fieldsRequired.entrySet()){

			TicketType ticketTypes = new TicketType();
			ticketTypes.setType((String) m.getKey());
			ticketTypes.setFields((HashSet<String>) m.getValue());
			ticketTypesList.add(ticketTypes);
		}

		this.fieldsRequired.clear();

		return  ticketTypesList;

	}

	private void addSelectUsersNewProjectMenu(MainController controller) {

		UserService userService = new UserService();

		Vector<Role> roles = null;
		try {
			roles = userService.getRoles();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Vector<String> rolesList = new Vector<String>();
		for (Role role:roles) {
			rolesList.add(role.getId());
		}


		Vector<User> users = null;
		try {
			users = userService.getUsers();
		} catch (IOException e) {
			e.printStackTrace();
		}

		JPanel containerSelectUsers = new JPanel();
		containerSelectUsers.setBorder(BorderFactory.createTitledBorder("Select Users"));
		containerSelectUsers.setLayout(new BoxLayout(containerSelectUsers, BoxLayout.Y_AXIS));

		for (User user: users) {

			JPanel containerUser = new JPanel();
			containerUser.setLayout(new BorderLayout());

			JCheckBox check = new JCheckBox(user.getName());
			containerUser.add(BorderLayout.WEST,check);
			JComboBox comboBox = new JComboBox(rolesList);
			containerUser.add(BorderLayout.EAST,comboBox);

			check.addActionListener(controller.getRoleSelectedListener(check,comboBox));
			comboBox.addActionListener(controller.getRoleSelectedListener(check,comboBox));

			containerSelectUsers.add(containerUser);

		}

		projectMenuPanel.add(containerSelectUsers);
	}

	private void addTicketsTypesNewProjectMenu(MainController controller) {

		TicketService ticketService = new TicketService();
		Vector<TicketType> ticketsTypes = null;
		try {
			ticketsTypes = ticketService.getTypes();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Vector<String> ticketsTypesList = new Vector<>();
		for (TicketType ticket:ticketsTypes) {
			ticketsTypesList.add(ticket.getType());
		}

		//Fields required
		String[] fieldsRequired = new String[]{"Title", "Description"};

		JPanel containerFieldsRequired = new JPanel();
		containerFieldsRequired.setBorder(BorderFactory.createTitledBorder("Fields Required "));
		containerFieldsRequired.setLayout(new BoxLayout(containerFieldsRequired , BoxLayout.Y_AXIS));

		for (String type:ticketsTypesList) {

			this.fieldsRequired.put(type,new HashSet<>());

			JPanel fieldRequiredPanel = new JPanel();
			fieldRequiredPanel.setLayout(new BorderLayout());

			fieldRequiredPanel.add(BorderLayout.WEST,new JLabel(type));

			JPanel fieldPanel = new JPanel();
			fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.X_AXIS));

			for (String field:fieldsRequired) {
				JCheckBox checkBox = new JCheckBox(field);
				checkBox.addActionListener(controller.getFieldRequiredListener(type));
				fieldPanel.add(checkBox);
			}

			fieldRequiredPanel.add(BorderLayout.EAST,fieldPanel);
			containerFieldsRequired.add(fieldRequiredPanel);

		}

		projectMenuPanel.add(containerFieldsRequired);

	}

	private void addMenuAddNewState(MainController controller) {

		JPanel containerNewState = new JPanel();
		containerNewState.setBorder(BorderFactory.createTitledBorder("New State "));
		containerNewState.setLayout(new BoxLayout(containerNewState, BoxLayout.Y_AXIS));

		JTextField nameText = new JTextField(5);
		containerNewState.add(createLabelWith("Name state", nameText));

		JButton button = new JButton("Add");
		containerNewState.add(button);

		projectMenuPanel.add(containerNewState);

		JPanel panel = this.generatePanelStates();

		button.addActionListener(controller.getAddStateListener(nameText,this.flowStates,panel));

	}

	public void addPanelNewState(MainController controller,JPanel panel, String state){

		UserService userService = new UserService();

		Vector<Role> roles = null;
		try {
			roles = userService.getRoles();
		} catch (IOException e) {
			e.printStackTrace();
		}

		JPanel statePanel = new JPanel();
		statePanel.setLayout(new BorderLayout());
		statePanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

		statePanel.add(BorderLayout.WEST,new JLabel("  "+state));

		JPanel rolesPanel = new JPanel();
		rolesPanel.setLayout(new BoxLayout(rolesPanel, BoxLayout.Y_AXIS));

		for (Role role:roles) {
			JCheckBox checkBox = new JCheckBox(role.getId());
			checkBox.addActionListener(controller.getRolesChangeStateListener(state));
			rolesPanel.add(checkBox);
		}

		statePanel.add(BorderLayout.EAST,rolesPanel);
		panel.add(statePanel);

		projectMenuPanel.revalidate();
		projectMenuPanel.repaint();

	}

	private JPanel generatePanelStates(){

		JPanel containerStateRoles = new JPanel();
		containerStateRoles.setBorder(BorderFactory.createTitledBorder("States"));
		containerStateRoles.setLayout(new BoxLayout(containerStateRoles , BoxLayout.Y_AXIS));

		JScrollPane scrollPanel = new JScrollPane(containerStateRoles);
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPanel.setPreferredSize(new Dimension(100,100));

		projectMenuPanel.add(scrollPanel);

		return containerStateRoles;
	}

	public void putUserSelect(String userID,String role) {
		this.selectUsers.put(userID,role);

	}

	public void removeUserSelect(String userID) {
		if (this.selectUsers.containsKey(userID)) {
			this.selectUsers.remove(userID);
		}

	}

	public void putFieldRequired(String typeTicket,String field) {

		HashSet fields;
		if (this.fieldsRequired.containsKey(typeTicket)) {
			fields = this.fieldsRequired.get(typeTicket);
		} else {
			fields = new HashSet();
		}
		fields.add(field);
		this.fieldsRequired.put(typeTicket,fields);
	}

	public void removeFieldRequired(String typeTicket,String field) {
		if (this.fieldsRequired.containsKey(typeTicket)) {
			HashSet fields = this.fieldsRequired.get(typeTicket);
			fields.remove(field);
		}
	}

	public void putRolesChangeState(String state,String role) {
		this.flowStates.addRoleInState(state,role);
	}

	public void removeRolesChangeState(String state,String role) {
		this.flowStates.removeRoleInState(state,role);
	}

}
