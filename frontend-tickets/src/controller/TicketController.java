package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import container.TicketsSystemContainer;
import model.Comment;
import model.Model;
import model.Ticket;
import model.Project;
import service.TicketService;
import view.CreateTicketView;
import view.TicketDetailView;

import javax.swing.*;

public class TicketController extends Controller {

    private TicketDetailView ticketDetailView;
    private CreateTicketView createTicketView;
    private TicketService ticketService;

    private Ticket selectedTicket;
    private Project selectedProject;

    private ActionListener showTicketsFromProject;

    public TicketController(Model model, TicketsSystemContainer container)
    {
        super(model, container);
        ticketService = new TicketService();
    }

    public void showView()
    {
        return;
    }

    public void setSelectedTicket(Ticket selectedTicket){
        this.selectedTicket = selectedTicket;
    }

    public void setSelectedProject(Project selectedProject){
        this.selectedProject = selectedProject;
    }

    public void showTicketForm(){
        createTicketView = new CreateTicketView(getModel());
        createTicketView.initializeViewActionListeners(this);
        createTicketView.showView();
    }

    public void showTicketDetail()
    {
        Ticket ticket = null;
        try {
            ticket = ticketService.getTicket(selectedTicket);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ticketDetailView = new TicketDetailView(getModel());
        ticketDetailView.initializeViewActionListeners(this);
        ticketDetailView.show(ticket);
    }


    public ActionListener getPostCommentListener(JTextArea commentArea, JList commentsList)
    {
        class postCommentListener implements ActionListener
        {
            public void actionPerformed(ActionEvent arg)
            {
                if(commentArea.getText() != null)
                {
                    Comment comment = new Comment(getModel().getCurrentUser(), commentArea.getText(), selectedTicket);
                    commentArea.setText(null);
                    selectedTicket.addComment(comment);

                    TicketService service = new TicketService();
                    try {
                        service.postComment(comment);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    ticketDetailView.putCommentsInList(commentsList, selectedTicket.getComments());
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
        }
        return new createTicketListener();
    }

    public void initializeViewActionListeners(ProjectController controller)
    {
        this.showTicketsFromProject = controller.getShowProjectDetailListener();
    }


}
