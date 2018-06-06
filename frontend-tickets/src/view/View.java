package view;

import model.Model;

import javax.swing.*;
import java.awt.*;

public abstract class View {

	private Model model;
	
	public View(Model model)
	{
		this.model = model;
	}

	public abstract void showView();
	
	public Model getModel()
	{
		return model;
	}

	protected JPanel createLabelWith(String label, JComponent component)
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		panel.add(new JLabel(label));
		panel.add(component);
		return panel;
	}

}
