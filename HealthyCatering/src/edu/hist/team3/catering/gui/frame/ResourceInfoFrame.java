package edu.hist.team3.catering.gui.frame;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import edu.hist.team3.catering.database.Resource;
import edu.hist.team3.catering.gui.panel.ResourceEditPanel;

public class ResourceInfoFrame extends JFrame {
	ResourceEditPanel editPanel;
	
	public ResourceInfoFrame(Resource resource) {
		this.setLayout(new BorderLayout());
		editPanel = new ResourceEditPanel();
		editPanel.fillInfo(resource);
		add(editPanel);
		setTitle("Resource info");
		setSize(300, 400);
		setAlwaysOnTop(true);
		setLocationRelativeTo(null);
	}
}
