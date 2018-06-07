package view;

import javax.swing.*;
import java.awt.*;

public abstract class View {

	public abstract void show();

	protected JPanel createLabelWith(String label, JLabel labelError, JComponent component)
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));

		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));

		labelPanel.add(new JLabel(label));

		if (labelError != null)
			labelPanel.add(labelError);

		panel.add(labelPanel);
		panel.add(component);
		return panel;
	}

}
