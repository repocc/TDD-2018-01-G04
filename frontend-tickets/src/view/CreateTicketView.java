package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;

import controller.TicketController;
import model.*;
import utils.GroupButtonUtils;

public class CreateTicketView extends View {

    private JTextField titleText;
    private JTextField descriptionText;
    private ButtonGroup ticketTypeButtonGroup;
    private ButtonGroup userButtonGroup;
    private JPanel mainPanel;
    private JLabel labelDescriptionRequired;
    private JLabel labelTittleRequired;

    private Vector<TicketType> ticketsTypes = new Vector<>();
    private Vector<User> users = new Vector<>();

    private GroupButtonUtils groupButtonUtils = new GroupButtonUtils();

    private ActionListener createTicketListener;

    public CreateTicketView(Vector<TicketType> ticketsTypes, Vector<User> users)
    {
        this.ticketsTypes = ticketsTypes;
        this.users = users;
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

        JPanel panelGeneral = new JPanel();
        panelGeneral.setLayout(new BorderLayout());
        JPanel containertype = new JPanel();
        containertype.setBorder(BorderFactory.createTitledBorder("Select Type "));
        containertype.setLayout(new BoxLayout(containertype , BoxLayout.Y_AXIS));

        ticketTypeButtonGroup = new ButtonGroup();

        for (TicketType type: ticketsTypes) {
            JRadioButton radioButton = new JRadioButton(type.getType(),false);
            ticketTypeButtonGroup.add(radioButton);
            containertype.add(radioButton);
        }

        ticketTypeButtonGroup.getElements().nextElement().setSelected(true);

        panelGeneral.add(containertype);
        mainPanel.add(panelGeneral);

    }

    private void addSelectedAssignedUser(JPanel mainPanel) {

        JPanel panelGeneral = new JPanel();
        panelGeneral.setLayout(new BorderLayout());
        JPanel containerSelectUser = new JPanel();
        containerSelectUser.setBorder(BorderFactory.createTitledBorder("Select Assigned user "));
        containerSelectUser.setLayout(new BoxLayout(containerSelectUser , BoxLayout.Y_AXIS));

        userButtonGroup = new ButtonGroup();

        for (User user: users) {

            JRadioButton radioButton = new JRadioButton(user.getName(),false);
            userButtonGroup.add(radioButton);
            containerSelectUser.add(radioButton);
        }

        userButtonGroup.getElements().nextElement().setSelected(true);

        panelGeneral.add(containerSelectUser);
        mainPanel.add(panelGeneral);
    }

    public void show()
    {
        titleText = new JTextField(30);
        descriptionText = new JTextField(30);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        labelTittleRequired = new JLabel(" ");
        labelTittleRequired.setForeground(Color.RED);
        labelDescriptionRequired = new JLabel(" ");
        labelDescriptionRequired.setForeground(Color.RED);

        mainPanel.add(createLabelWith("Title:", labelTittleRequired, titleText));
        mainPanel.add(createLabelWith("Description:", labelDescriptionRequired, descriptionText));

        this.addTicketTypeMenu(mainPanel);
        this.addSelectedAssignedUser(mainPanel);

        this.confirmDialog();

    }

    public void confirmDialog(){

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

    public void setErrorDescription(String message) {
        labelDescriptionRequired.setText(message);
    }

    public void setErrorTittle(String message) {
        labelTittleRequired.setText(message);
    }

}
