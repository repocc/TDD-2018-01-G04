package view;

import controller.MainController;
import controller.TicketController;
import model.*;
import service.ProjectService;
import service.TicketService;
import service.UserService;
import utils.GroupButtonUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Vector;

public class CreateProjectView extends View {

    private JPanel projectMenuPanel = new JPanel();
    private JTextField nameText;

    private Map<String,HashSet<String>> fieldsRequired = new HashMap<>();
    private Map<String,String> selectUsers =new HashMap<>();
    private FlowState flowStates = new FlowState();

    private ActionListener createProjectListener;

    public CreateProjectView(Model model)
    {
        super(model);
    }

    public String getName(){
        return nameText.getText();
    }

    public FlowState getFlowStates(){
        return flowStates;
    }

    public void showView() { return; }

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

    public Vector<User> getSelectedUser() {

        Vector<User> selectedUsers = new Vector<>();

        for(Map.Entry m:selectUsers.entrySet()){

            String userID = (String) m.getKey();
            Role userRole = new Role((String) m.getValue());

            selectedUsers.add(new User(userID,userRole,userID));

        }

        this.selectUsers.clear();

        return selectedUsers;
    }

    public Vector<TicketType> getTicketTypeList(){

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

    public void showNewProjectMenu(MainController controller) {

        projectMenuPanel.removeAll();

        nameText = new JTextField(30);

        projectMenuPanel.setLayout(new BoxLayout(projectMenuPanel, BoxLayout.Y_AXIS));

        projectMenuPanel.add(createLabelWith("Project name:", nameText));

        this.addSelectUsersNewProjectMenu(controller);

        this.addTicketsTypesNewProjectMenu(controller);

        this.addMenuAddNewState(controller);

        int result = JOptionPane.showConfirmDialog(null, projectMenuPanel, "New Project", JOptionPane.OK_CANCEL_OPTION);


        if (result == JOptionPane.OK_OPTION) {
            createProjectListener.actionPerformed(null);
        }

        flowStates.clear();
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

    public void initializeViewActionListeners(MainController controller)
    {
        this.createProjectListener = controller.getCreateProjectListener();
    }


}
