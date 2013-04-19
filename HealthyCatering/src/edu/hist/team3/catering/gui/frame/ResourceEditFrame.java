package edu.hist.team3.catering.gui.frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;

import edu.hist.team3.catering.database.Resource;
import edu.hist.team3.catering.database.manager.Services;
import edu.hist.team3.catering.gui.panel.ResourceEditPanel;

public class ResourceEditFrame extends JFrame implements ActionListener {
	private Services services;
	private ResourceEditPanel resourceEdit;
	private JButton button;
	private Resource resource = null;

	public ResourceEditFrame(Services services) {
		super("Add resource");
		this.services = services;
		init("Create");
	}

	public ResourceEditFrame(Resource resource) {
		super("Edit resource");
		this.resource = resource;
		init("Save");
		resourceEdit.fillInfo(resource);
	}

	private void init(String buttonText) {
		this.setLayout(new BorderLayout());
		resourceEdit = new ResourceEditPanel();
		button = new JButton(buttonText);
		button.addActionListener(this);
		add(resourceEdit, BorderLayout.CENTER);
		add(button, BorderLayout.SOUTH);
		setTitle("Resource editor");
		pack();
		setSize(300, 400);
		setAlwaysOnTop(true);
		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.setVisible(false);
		if (resource == null) {
			resource = services.getResourceManager().createResource();
			if (resource == null) {
				Services.showError("Error: Could not create resource!");
				return;
			}
		}
		try {
			resourceEdit.apply(resource);
		}
		catch (SQLException e) {
			e.printStackTrace();
			Services.showError("Error: Could not save changes to resource!");
		}
	}
}
