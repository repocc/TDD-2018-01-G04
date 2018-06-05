package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.*;

import controller.MainController;
import controller.TicketController;
import controller.UserController;
import model.Comment;
import model.Model;
import model.Ticket;
import service.TicketService;

public class TicketDetailView extends View {

    private JButton postCommentButton = new JButton("Post");
    private JTextArea commentArea = new JTextArea(4, 30);
    private JList commentsList = new JList();

    public TicketDetailView(Model model)
    {
        super(model);
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

    public void showView()
    {
        return;
    }

    public void show(Ticket ticket){
        JPanel mainPanel = createTicketDetails(ticket);
        JOptionPane.showMessageDialog(null, mainPanel, "Ticket", JOptionPane.INFORMATION_MESSAGE);
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


    public JPanel createTicketDetails(Ticket ticket)
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

        commentsList.setVisibleRowCount(3);
        commentsList.setFixedCellHeight(35);

        putCommentsInList(commentsList, comments);

        JScrollPane commentsScrollPane = new JScrollPane(commentsList);

        c = createGbc(0, 1);
        usersCommentsPanel.add(commentsScrollPane, c);

        JLabel commentLabel = new JLabel("New comment: " );
        commentLabel.setHorizontalAlignment(JLabel.LEFT);

        commentArea.setLineWrap(true);
        commentArea.setWrapStyleWord(true);
        commentArea.setBorder(new JTextField().getBorder());
        commentArea.setText(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

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
        mainPanel.add(this.postCommentButton , c);

        return mainPanel;
    }

    public void initializeViewActionListeners(TicketController controller)
    {
        postCommentButton.addActionListener(controller.getPostCommentListener(commentArea, commentsList));
    }

}
