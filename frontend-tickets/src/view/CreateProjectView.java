package view;

import controller.ProjectController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class CreateProjectView extends View {

    private JPanel projectMenuPanel = new JPanel();
    private JTextField nameText;
    private JTextField stateNameText;
    private JLabel labelNameProjectRequired;

    private Vector<Role> roles;
    private Vector<User> users;
    private Vector<TicketType> ticketsTypes;

    //Fields required
    private String[] fieldsRequired = new String[]{"Title", "Description"};

    //First key is ticket type, second key is required field.
    private HashMap<String,HashMap<String,JCheckBox>> ticketTypesCheckboxes = new HashMap<>();

    //First key is username
    private HashMap<String,JCheckBox> usersCheckboxes = new HashMap<>();
    private HashMap<String,JComboBox> usersComboboxes = new HashMap<>();

    private Vector<String> states = new Vector<>();
    //First key is state, second key is role
    private HashMap<String, HashMap<String, JCheckBox>> statesRoles = new HashMap<>();

    private ActionListener createProjectListener;

    public CreateProjectView(Vector<Role> roles, Vector<User> users, Vector<TicketType> ticketsTypes)
    {
        this.roles = roles;
        this.users = users;
        this.ticketsTypes = ticketsTypes;
    }

    public String getName(){
        return nameText.getText();
    }

    public void show() { return; }

    public Vector<TicketState> getTicketStates() {
        Vector<TicketState> ticketStates = new Vector<>();

        for (String state:states) {
            HashSet<String> stateRoles = new HashSet<>();

            for (Role role:roles) {
                JCheckBox checkBox = statesRoles.get(state).get(role.getId());
                if (checkBox != null && checkBox.isSelected()) stateRoles.add(role.getId());
            }

            ticketStates.add(new TicketState(state, stateRoles));
        }

        return ticketStates;
    }

    public Vector<User> getSelectedUsers() {
        Vector<User> selectedUsers = new Vector<>();

        for (User user: this.users) {

            JCheckBox checkBox = usersCheckboxes.get(user.getName());
            if (checkBox != null && checkBox.isSelected()) {
                JComboBox comboBox = usersComboboxes.get(user.getName());
                String selectedRole = (String) comboBox.getSelectedItem();
                selectedUsers.add(new User(user.getName(), new Role(selectedRole)));
            }
        }
        return selectedUsers;
    }

    public Vector<TicketType> getTicketTypesRequiredFields(){
        Vector<TicketType> ticketTypesRequiredFields = new Vector<>();

        for (TicketType ticketType: this.ticketsTypes) {
            HashSet<String> fields = new HashSet<>();

            for (String field:fieldsRequired) {
                JCheckBox checkBox = ticketTypesCheckboxes.get(ticketType.getType()).get(field);
                if (checkBox != null && checkBox.isSelected()) {
                    fields.add(field);
                }
            }

            TicketType toAdd = new TicketType(ticketType.getType(), fields);
            ticketTypesRequiredFields.add(toAdd);
        }

        return ticketTypesRequiredFields;
    }

    public void showNewProjectForm() {

        projectMenuPanel.removeAll();
        nameText = new JTextField(30);
        projectMenuPanel.setLayout(new BoxLayout(projectMenuPanel, BoxLayout.Y_AXIS));

        labelNameProjectRequired = new JLabel(" ");
        projectMenuPanel.add(createLabelWith("Project name:",labelNameProjectRequired, nameText));

        this.addProjectUsersMenu();
        this.addProjectTicketTypesRequiredFieldsMenu();
        this.addProjectStatesMenu();

        int result = JOptionPane.showConfirmDialog(null, projectMenuPanel, "New Project", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            createProjectListener.actionPerformed(null);
        }
    }

    private void addProjectUsersMenu() {

        Vector<String> rolesList = new Vector<String>();
        for (Role role:roles) {
            rolesList.add(role.getId());
        }

        JPanel containerSelectUsers = new JPanel();
        containerSelectUsers.setBorder(BorderFactory.createTitledBorder("Select Users"));
        containerSelectUsers.setLayout(new BoxLayout(containerSelectUsers, BoxLayout.Y_AXIS));

        for (User user: users) {

            JPanel containerUser = new JPanel();
            containerUser.setLayout(new BorderLayout());

            JCheckBox check = new JCheckBox(user.getName());
            usersCheckboxes.put(user.getName(), check);

            containerUser.add(BorderLayout.WEST,check);
            JComboBox comboBox = new JComboBox(rolesList);
            usersComboboxes.put(user.getName(), comboBox);

            containerUser.add(BorderLayout.EAST,comboBox);

            containerSelectUsers.add(containerUser);

        }

        projectMenuPanel.add(containerSelectUsers);
    }

    private void addProjectTicketTypesRequiredFieldsMenu() {

        JPanel containerFieldsRequired = new JPanel();
        containerFieldsRequired.setBorder(BorderFactory.createTitledBorder("Fields Required "));
        containerFieldsRequired.setLayout(new BoxLayout(containerFieldsRequired , BoxLayout.Y_AXIS));

        for (TicketType ticketType: this.ticketsTypes) {

            JPanel fieldRequiredPanel = new JPanel();
            fieldRequiredPanel.setLayout(new BorderLayout());

            fieldRequiredPanel.add(BorderLayout.WEST,new JLabel(ticketType.getType()));

            JPanel fieldPanel = new JPanel();
            fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.X_AXIS));

            HashMap<String,JCheckBox> checkboxes = new HashMap<>();
            for (String field:fieldsRequired) {
                JCheckBox checkBox = new JCheckBox(field);
                checkboxes.put(field, checkBox);
                fieldPanel.add(checkBox);
            }
            ticketTypesCheckboxes.put(ticketType.getType(), checkboxes);

            fieldRequiredPanel.add(BorderLayout.EAST,fieldPanel);
            containerFieldsRequired.add(fieldRequiredPanel);

        }

        projectMenuPanel.add(containerFieldsRequired);

    }

    private void addProjectStatesMenu() {

        JPanel containerNewState = new JPanel();
        containerNewState.setBorder(BorderFactory.createTitledBorder("New State "));
        containerNewState.setLayout(new BoxLayout(containerNewState, BoxLayout.Y_AXIS));

        stateNameText = new JTextField(5);
        containerNewState.add(createLabelWith("Name",null, stateNameText));

        JButton button = new JButton("Add");
        containerNewState.add(button);

        projectMenuPanel.add(containerNewState);

        JPanel panel = this.generateStatesPanel();
        button.addActionListener(getAddStateToMenuListener(panel));
    }

    private JPanel generateStatesPanel(){

        JPanel containerStateRoles = new JPanel();
        containerStateRoles.setBorder(BorderFactory.createTitledBorder("States"));
        containerStateRoles.setLayout(new BoxLayout(containerStateRoles , BoxLayout.Y_AXIS));

        JScrollPane scrollPanel = new JScrollPane(containerStateRoles);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanel.setPreferredSize(new Dimension(100,100));

        projectMenuPanel.add(scrollPanel);

        return containerStateRoles;
    }

    public ActionListener getAddStateToMenuListener(JPanel panel) {

        class addStateToMenuListener implements ActionListener
        {
            public void actionPerformed(ActionEvent arg0) {
                JPanel statePanel = new JPanel();
                statePanel.setLayout(new BorderLayout());
                statePanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

                JLabel stateLabel = new JLabel("  "+ stateNameText.getText());
                statePanel.add(BorderLayout.WEST,stateLabel);

                JPanel rolesPanel = new JPanel();
                rolesPanel.setLayout(new BoxLayout(rolesPanel, BoxLayout.Y_AXIS));

                states.add(stateNameText.getText());

                HashMap<String,JCheckBox> checkboxes = new HashMap<>();

                for (Role role:roles) {
                    JCheckBox checkBox = new JCheckBox(role.getId());
                    checkboxes.put(role.getId(), checkBox);
                    rolesPanel.add(checkBox);
                }
                statesRoles.put(stateNameText.getText(), checkboxes);

                statePanel.add(BorderLayout.EAST,rolesPanel);
                panel.add(statePanel);

                projectMenuPanel.revalidate();
                projectMenuPanel.repaint();

            }
        }
        return new addStateToMenuListener();
    }

    public void initializeViewActionListeners(ProjectController controller)
    {
        this.createProjectListener = controller.getCreateProjectListener();
    }


}
