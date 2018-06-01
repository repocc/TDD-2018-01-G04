package view;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.IdentificationController;
import model.Model;

public class IdentificationView extends View {
	
	private JFrame window;
	private JPanel panelUser;
	private JLabel labelUser;
	private JButton loginButton = new JButton("Login");
	private JButton cancelButton = new JButton("Cancel");
	private JTextField user;

	public IdentificationView(Model model)
	{
		super(model);
	}

	public void showView()
	{
		window = new JFrame("Tickets System");
		window.setLayout(new GridLayout(2, 1));
		
		panelUser = new JPanel();
		user = new JTextField();
		user.setColumns(30);

		labelUser = new JLabel();
		
		labelUser.setText("Username: ");
		
		panelUser.add(labelUser);
		panelUser.add(user);		
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(loginButton);
		buttonPanel.add(cancelButton);
		
		window.add(panelUser);
		window.add(buttonPanel);
		
		window.pack();
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
	
	public void closeWindow()
	{
		window.dispose();
	}
	
	public void initializeViewActionListeners(IdentificationController controller)
	{
		initLoginButton(controller.getLoginListener());
		initCancelButton(controller.getCancelListener());
	}
	
	public void initLoginButton(ActionListener listener)
	{
		loginButton.addActionListener(listener);
	}
	
	public void initCancelButton(ActionListener listener)
	{
		cancelButton.addActionListener(listener);
	}
	
	public boolean isFieldEmpty()
	{
		return (user.getText().isEmpty());
	}

	public String getUsername()
	{
		return user.getText();
	}
	
}