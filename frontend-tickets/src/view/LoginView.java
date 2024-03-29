package view;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import controller.UserController;
import model.Model;

public class LoginView extends View {
	
	private JFrame window;
	private JPanel panelUser;
	private JPanel panelError;
	private JLabel labelUser;
	private JLabel labelLoginError = new JLabel();;
	private JButton loginButton = new JButton("Login");
	private JButton cancelButton = new JButton("Cancel");
	private JTextField user;

	public LoginView()
	{
	}

	public void show()
	{
		window = new JFrame("Tickets System");
		window.setLayout(new GridLayout(1, 1));

		JPanel generalPanel = new JPanel();
		generalPanel.setLayout(new BoxLayout(generalPanel, BoxLayout.Y_AXIS));

		panelUser = new JPanel();

		user = new JTextField();
		user.setColumns(30);

		labelUser = new JLabel();
		
		labelUser.setText("Username: ");
		
		panelUser.add(labelUser);
		panelUser.add(user);


		panelError = new JPanel();
		labelLoginError.setForeground(Color.RED);
		labelLoginError.setText(" ");
		panelError.add(labelLoginError);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(loginButton);
		buttonPanel.add(cancelButton);

		generalPanel.add(panelUser);
		generalPanel.add(panelError);
		generalPanel.add(buttonPanel);

		window.add(generalPanel);
		
		window.pack();
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
	
	public void closeWindow() {
		window.dispose();
	}
	
	public void initializeViewActionListeners(UserController controller) {
		loginButton.addActionListener(controller.getLoginListener());
		cancelButton.addActionListener(controller.getCancelListener());
	}

	public boolean isFieldEmpty() {
		return (user.getText().isEmpty());
	}

	public String getUsername() {
		return user.getText();
	}

	public void setError(String error) {
		labelLoginError.setText(error);
	}

}
