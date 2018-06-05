package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

import container.TicketsSystemContainer;
import model.Comment;
import model.Model;
import model.Ticket;
import service.TicketService;
import view.TicketDetailView;

import javax.swing.*;

public class TicketController extends Controller {

    private TicketDetailView ticketDetailView;
    private TicketService ticketService;

    private Ticket selectedTicket;

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

}
