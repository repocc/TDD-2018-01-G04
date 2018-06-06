package view;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.*;

import controller.TicketController;
import model.*;
import service.TicketService;
import service.UserService;
import utils.GroupButtonUtils;

public class CreateTicketView extends View {

    private JTextField titleText;
    private JTextField descriptionText;
    private ButtonGroup ticketTypeButtonGroup;
    private ButtonGroup userButtonGroup;

    private GroupButtonUtils groupButtonUtils = new GroupButtonUtils();

    private ActionListener createTicketListener;

    public CreateTicketView(Model model)
    {
        super(model);
    }

    public String getTitle(){
        return titleText.getText();
    }

    public String getDescription(){
        return descriptionText.getText();
    }

    public String getTicketType(){
        return groupButtonUtils.getSelectedButtonText(ticketTypeButtonGroup);
    }

    public String getAssignedUser(){
        return groupButtonUtils.getSelectedButtonText(userButtonGroup);
    }

    private void addTicketTypeMenu(JPanel mainPanel) {

        /*TODO: Extract request to controller*/
        TicketService ticketService = new TicketService();
        Vector<TicketType> ticketsTypes = null;
        try {
            ticketsTypes = ticketService.getTypes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel containertype = new JPanel();
        containertype.setBorder(BorderFactory.createTitledBorder("Select Type "));
        containertype.setLayout(new BoxLayout(containertype , BoxLayout.Y_AXIS));

        ticketTypeButtonGroup = new ButtonGroup();

        for (TicketType type: ticketsTypes) {
            JRadioButton radioButton = new JRadioButton(type.getType(),false);
            ticketTypeButtonGroup.add(radioButton);
            containertype.add(radioButton);
        }

        mainPanel.add(containertype);

    }

    private void addSelectedAssignedUser(JPanel mainPanel) {
        UserService userService = new UserService();

        /*TODO: Extract request to controller*/
        Vector<User> users = null;
        try {
            users = userService.getUsers();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel containerSelectUser = new JPanel();
        containerSelectUser.setBorder(BorderFactory.createTitledBorder("Select Assigned user "));
        containerSelectUser.setLayout(new BoxLayout(containerSelectUser , BoxLayout.Y_AXIS));

        userButtonGroup = new ButtonGroup();

        for (User user: users) {

            JRadioButton radioButton = new JRadioButton(user.getName(),false);
            userButtonGroup.add(radioButton);
            containerSelectUser.add(radioButton);
        }

        mainPanel.add(containerSelectUser);
    }

    public void showView()
    {
        titleText = new JTextField(30);
        descriptionText = new JTextField(30);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.add(createLabelWith("Title:", titleText));
        mainPanel.add(createLabelWith("Description:", descriptionText));

        this.addTicketTypeMenu(/*controller,*/mainPanel);
        this.addSelectedAssignedUser(/*controller,*/mainPanel);

        int result = JOptionPane.showConfirmDialog(null, mainPanel, "New Ticket", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION)
        {
            this.createTicketListener.actionPerformed(null);
        }

    }

    public void initializeViewActionListeners(TicketController controller)
    {
        this.createTicketListener = controller.getCreateTicketListener();
    }


}
