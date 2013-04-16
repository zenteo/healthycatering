package edu.hist.team3.catering.gui.panel;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PropertyPanel<T extends Component> extends JPanel {
	private JLabel label;
	private T field;
	
	public PropertyPanel(String label, T field) {
		this(label, field, true);
	}
	
	public PropertyPanel(String label, T field, boolean labelFirst) {
		this.label = new JLabel(label);
		this.field = field;
		setLayout(new BorderLayout());
		if (labelFirst) {
			this.add(this.label, BorderLayout.WEST);
			this.add(this.field, BorderLayout.CENTER);
		}
		else {
			this.add(this.field, BorderLayout.CENTER);
			this.add(this.label, BorderLayout.EAST);
		}
	}
}
