package view;

import javax.swing.*;
import java.awt.*;

public abstract class View {

	public abstract void show();

	protected JPanel createLabelWith(String label, JComponent component)
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		panel.add(new JLabel(label));
		panel.add(component);
		return panel;
	}

}
