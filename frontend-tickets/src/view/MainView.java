package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.*;

import javax.swing.*;
import javax.swing.border.Border;

import com.google.gson.Gson;
import controller.MainController;
import model.*;
import service.ProjectService;
import service.TicketService;
import service.UserService;

public class MainView extends View {

	private JFrame window;
	private JButton newProjectButton = new JButton("New Project");
	private JButton newTicketButton = new JButton("New Ticket");
	private JList projectsList = new JList();
	private JScrollPane projectsListScroller;
	private JPanel ticketsListMainPanel = new JPanel(new BorderLayout());

	//create project
	private Map<String,String> selectUsers =new HashMap<>();
	private Map<String,HashSet<String>> fieldsRequired = new HashMap<>();
	private Map<String,HashSet<String>> rolesChangeState = new HashMap<>();
	private String userAsigned = "";
	private String typeAsigned = "";

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

	public void showNewTicketMenu(Project selectedProject, MainController controller) {

		JTextField titleText = new JTextField(30);
		JTextField descriptionText = new JTextField(30);
		
		JPanel mainPanel = new JPanel();
		//mainPanel.setLayout(new GridLayout(4, 1));
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		mainPanel.add(createLabelWith("Title:", titleText));
		mainPanel.add(createLabelWith("Description:", descriptionText));

		this.addTypeTicketSelectMenu(controller,mainPanel);
		this.addSelectUserAsigned(controller,mainPanel);
		
		int result = JOptionPane.showConfirmDialog(null, mainPanel, "New Ticket", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION)
		{

			String tittle = titleText.getText();
			String description = descriptionText.getText();

			Ticket ticket = new Ticket();
			ticket.setTitle(tittle);
			ticket.setDescription(description);
			ticket.setUserAsigned(this.userAsigned);
			ticket.setType(this.typeAsigned);
			ticket.setProjectAsigned(selectedProject.getID());

			TicketService service = new TicketService();

			try {
				service.postTicket(ticket);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	private void addTypeTicketSelectMenu(MainController controller, JPanel mainPanel) {

		TicketService ticketService = new TicketService();
		Vector<TicketTypes> ticketsTypes = null;
		try {
			ticketsTypes = ticketService.getTypes();
		} catch (IOException e) {
			e.printStackTrace();
		}

		JPanel containertype = new JPanel();
		containertype.setBorder(BorderFactory.createTitledBorder("Select Type "));
		containertype.setLayout(new BoxLayout(containertype , BoxLayout.Y_AXIS));

		ButtonGroup buttonTicketTypes = new ButtonGroup();

		for (TicketTypes type: ticketsTypes) {
			JRadioButton radioButton = new JRadioButton(type.getType(),false);
			radioButton.addActionListener(controller.getAssignTypeTicket());
			buttonTicketTypes.add(radioButton);
			containertype.add(radioButton);
		}

		mainPanel.add(containertype);

	}

	private void addSelectUserAsigned(MainController controller, JPanel mainPanel) {
		UserService userService = new UserService();

		Vector<User> users = null;
		try {
			users = userService.getUsers();
		} catch (IOException e) {
			e.printStackTrace();
		}

		JPanel containerSelectUser = new JPanel();
		containerSelectUser.setBorder(BorderFactory.createTitledBorder("Select User Asigned "));
		containerSelectUser.setLayout(new BoxLayout(containerSelectUser , BoxLayout.Y_AXIS));

		ButtonGroup buttonGroupUsers = new ButtonGroup();

		for (User user: users) {

			JRadioButton radioButton = new JRadioButton(user.getName(),false);
			radioButton.addActionListener(controller.getAssignUserTicket());
			buttonGroupUsers.add(radioButton);
			containerSelectUser.add(radioButton);
		}

		mainPanel.add(containerSelectUser);
	}

	public void showNewProjectMenu(MainController controller) {

		JTextField nameText = new JTextField(30);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		mainPanel.add(createLabelWith("Project name:", nameText));

		this.addSelectUsersNewProjectMenu(controller,mainPanel);

		this.addTicketsTypesNewProjectMenu(controller,mainPanel);

		this.addRolesStatesNewProjectMenu(controller,mainPanel);


		int result = JOptionPane.showConfirmDialog(null, mainPanel, "New Project", JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.OK_OPTION) {

			ProjectService projectService = new ProjectService();

			Project project = new Project();

			String nameProject = nameText.getText();

			project.setName(nameProject);

			//TODO: replace real owner
			project.setOwner("owner");

			Vector<TicketTypes> ticketTypesList = this.getTicketTypeList();

			project.setTicketTypes(ticketTypesList);

			Vector<TicketState> ticketStates = this.getTicketStates();

			project.setTicketStates(ticketStates);

			Vector<User> selectedUsers = getSelectedUser();

			project.setUsers(selectedUsers);

			try {
				projectService.postProject(project);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

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

	private Vector<TicketTypes> getTicketTypeList(){

		Vector<TicketTypes> ticketTypesList = new Vector<>();

		for(Map.Entry m:this.fieldsRequired.entrySet()){

			TicketTypes ticketTypes = new TicketTypes();
			ticketTypes.setType((String) m.getKey());
			ticketTypes.setFields((HashSet<String>) m.getValue());
			ticketTypesList.add(ticketTypes);
		}

		this.fieldsRequired.clear();

		return  ticketTypesList;

	}


	private Vector<TicketState> getTicketStates() {

		Vector<TicketState> ticketStates = new Vector<>();

		for(Map.Entry m:this.rolesChangeState.entrySet()){

			String stateName = (String) m.getKey();
			HashSet<String> rolesState = (HashSet<String>) m.getValue();
			ticketStates.add(new TicketState(stateName,rolesState));

		}

		this.rolesChangeState.clear();

		return ticketStates;

	}


	private void addSelectUsersNewProjectMenu(MainController controller, JPanel mainPanel) {

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

		mainPanel.add(containerSelectUsers);
	}

	private void addTicketsTypesNewProjectMenu(MainController controller, JPanel mainPanel) {

		TicketService ticketService = new TicketService();
		Vector<TicketTypes> ticketsTypes = null;
		try {
			ticketsTypes = ticketService.getTypes();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Vector<String> ticketsTypesList = new Vector<>();
		for (TicketTypes ticket:ticketsTypes) {
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

		mainPanel.add(containerFieldsRequired);

	}

	private void addRolesStatesNewProjectMenu(MainController controller, JPanel mainPanel) {

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

		//States
		String[] states = new String[]{"OPEN", "IN PROGRESS", "QA", "CLOSED"};

		JPanel containerStateRoles = new JPanel();
		containerStateRoles.setBorder(BorderFactory.createTitledBorder("Roles Change States"));
		containerStateRoles.setLayout(new BoxLayout(containerStateRoles , BoxLayout.Y_AXIS));

		for (String state:states) {

			this.rolesChangeState.put(state,new HashSet<>());

			JPanel statePanel = new JPanel();
			statePanel.setLayout(new BorderLayout());
			statePanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

			statePanel.add(BorderLayout.WEST,new JLabel("  "+state));

			JPanel rolesPanel = new JPanel();
			rolesPanel.setLayout(new BoxLayout(rolesPanel, BoxLayout.Y_AXIS));

			for (String role:rolesList) {
				JCheckBox checkBox = new JCheckBox(role);
				checkBox.addActionListener(controller.getRolesChangeStateListener(state));
				rolesPanel.add(checkBox);
			}

			statePanel.add(BorderLayout.EAST,rolesPanel);
			containerStateRoles.add(statePanel);

		}

		mainPanel.add(containerStateRoles);

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

		HashSet roles;
		if (this.rolesChangeState.containsKey(state)) {
			roles = this.rolesChangeState.get(state);
		} else {
			roles = new HashSet();
		}
		roles.add(role);
		this.rolesChangeState.put(state,roles);
	}

	public void removeRolesChangeState(String state,String role) {
		if (this.rolesChangeState.containsKey(state)) {
			HashSet fields = this.rolesChangeState.get(state);
			fields.remove(role);
		}
	}

	public void assignUser(String user) {
		this.userAsigned = user;
	}

	public void assignType(String type) {
		this.typeAsigned = type;
	}

}
