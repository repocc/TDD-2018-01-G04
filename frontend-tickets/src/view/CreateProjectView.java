package view;

import controller.TicketController;
import model.Model;
import model.TicketType;
import model.User;
import service.TicketService;
import service.UserService;
import utils.GroupButtonUtils;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

public class CreateProjectView extends View {

    private JTextField titleText;
    private JTextField descriptionText;
    private ButtonGroup ticketTypeButtonGroup;
    private ButtonGroup userButtonGroup;

    private GroupButtonUtils groupButtonUtils = new GroupButtonUtils();

    private ActionListener createProjectListener;

    public CreateProjectView(Model model)
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

    public void showView()
    {


    }

    public void initializeViewActionListeners(TicketController controller)
    {
        //this.createProjectListener = controller.getCreateProjectListener();
    }


}
