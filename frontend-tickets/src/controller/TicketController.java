package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashSet;
import java.util.Vector;

import container.TicketsSystemContainer;
import model.*;
import service.TicketService;
import service.UserService;
import view.CreateTicketView;
import view.TicketDetailView;

import javax.swing.*;

public class TicketController extends Controller {

    private TicketDetailView ticketDetailView;
    private CreateTicketView createTicketView;
    private TicketService ticketService;
    private UserService userService;

    private Ticket selectedTicket;
    private Project selectedProject;

    private ActionListener showTicketsFromProject;

    public TicketController(TicketsSystemContainer container)
    {
        super(container);
        ticketService = new TicketService();
        userService = new UserService();
    }

    public void setSelectedTicket(Ticket selectedTicket){
        this.selectedTicket = selectedTicket;
    }

    public void setSelectedProject(Project selectedProject){
        this.selectedProject = selectedProject;
    }

    public void showTicketForm(){

        Vector<TicketType> ticketsTypes = null;
        try {
            ticketsTypes = ticketService.getTypes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Vector<User> users = null;
        try {
            users = userService.getUsers();
        } catch (IOException e) {
            e.printStackTrace();
        }

        createTicketView = new CreateTicketView(ticketsTypes, users);
        createTicketView.initializeViewActionListeners(this);
        createTicketView.show();
    }

    private Ticket loadTicketDetail(){
        Ticket ticket = null;
        try {
            ticket = ticketService.getTicketById(selectedTicket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ticket;
    }

    public void showTicketDetail()
    {
        Ticket ticket = loadTicketDetail();
        ticketDetailView = new TicketDetailView();
        ticketDetailView.setTicket(ticket);
        ticketDetailView.initializeViewActionListeners(this);
        ticketDetailView.show();
    }


    public ActionListener getPostCommentListener(JTextArea commentArea, JList commentsList)
    {
        class postCommentListener implements ActionListener
        {
            public void actionPerformed(ActionEvent arg)
            {
                if(commentArea.getText() != null)
                {
                    Comment comment = new Comment(getContainer().getCurrentUser(), commentArea.getText(), selectedTicket);
                    commentArea.setText(null);
                    selectedTicket.addComment(comment);

                    TicketService service = new TicketService();
                    try {
                        service.postComment(comment);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Ticket ticket = loadTicketDetail();
                    ticketDetailView.setTicket(ticket);
                    ticketDetailView.putCommentsInList(commentsList, ticket.getComments());
                }
            }
        }
        return new postCommentListener();
    }

    public ActionListener getCreateTicketListener() {
        TicketController controller = this;

        class createTicketListener implements ActionListener
        {
            public void actionPerformed(ActionEvent arg0) {

                if (this.validateRequiredField()) {

                    this.createTicket();

                } else {

                    createTicketView.confirmDialog();

                }

            }

            private boolean validateRequiredField(){

                String tittle = createTicketView.getTitle();
                String description = createTicketView.getDescription();
                String type = createTicketView.getTicketType();

                Boolean validDescription = selectedProject.validateTicketType("Description",description,type);
                Boolean validTittle = selectedProject.validateTicketType("Title",tittle,type);

                this.validateDescription(validDescription);
                this.validateTittle(validTittle);

                return validDescription && validTittle;

            }

            private void createTicket() {

                String tittle = createTicketView.getTitle();
                String description = createTicketView.getDescription();

                Ticket ticket = new Ticket();
                ticket.setTitle(tittle);
                ticket.setDescription(description);

                ticket.setAssignedUser(createTicketView.getAssignedUser());
                ticket.setType(createTicketView.getTicketType());
                ticket.setProject(controller.selectedProject.getID());

                try {
                    ticketService.postTicket(ticket);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                controller.showTicketsFromProject.actionPerformed(null);

            }

            private void validateDescription(boolean validDescription) {
                if (!validDescription) {
                    createTicketView.setErrorDescription(" Description is required");
                } else {
                    createTicketView.setErrorDescription(" ");
                }
            }

            private void validateTittle(boolean validTittle) {
                if (!validTittle) {
                    createTicketView.setErrorTittle(" Tittle is required");
                } else {
                    createTicketView.setErrorTittle(" ");
                }
            }

        }
        return new createTicketListener();
    }

    public void initializeViewActionListeners(ProjectController controller)
    {
        this.showTicketsFromProject = controller.getShowProjectDetailListener();
    }


}
