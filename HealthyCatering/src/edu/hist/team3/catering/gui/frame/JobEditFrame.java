package edu.hist.team3.catering.gui.frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;

import edu.hist.team3.catering.database.Job;
import edu.hist.team3.catering.database.manager.Services;
import edu.hist.team3.catering.gui.panel.JobEditPanel;

@SuppressWarnings("serial")
public class JobEditFrame extends JFrame implements ActionListener {
	private JobEditPanel editPanel;
	private JButton applyButton;
	private Services services;
	private Job job;
	/**
	 * Creates a new instance of JobEditFrame Based on services
	 * @param services
	 */
	public JobEditFrame(Services services) {
		this.services = services;
		this.job = null;
		init("Create job");
	}
	/**
	 * Create a new instance of JobEditFrame based on services and job
	 * @param services
	 * @param job
	 */
	public JobEditFrame(Services services, Job job) {
		this.services = services;
		this.job = job;
		init("Save changes");
		editPanel.fillInfo(job);
	}
	/**
	 * Initializing the job Editor
	 * @param buttonText
	 */
	private void init(String buttonText) {
		editPanel = new JobEditPanel();
		applyButton = new JButton(buttonText);
		applyButton.addActionListener(this);
		
		setLayout(new BorderLayout());
		setSize(300, 400);
		setLocationRelativeTo(null);
		setTitle("Job editor");
		setAlwaysOnTop(true);
		add(editPanel, BorderLayout.CENTER);
		add(applyButton, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (job == null) {
			try {
				job = services.getJobManager().addJob();
				editPanel.apply(job);
			}
			catch (SQLException e) {
				Services.showError("Error: Could not create job!");
				e.printStackTrace();
			}
		}
		else {
			try {
				editPanel.apply(job);
			}
			catch (SQLException e) {
				Services.showError("Error: Could not save changes to the job!");
				e.printStackTrace();
			}
		}
		setVisible(false);
	}
}
