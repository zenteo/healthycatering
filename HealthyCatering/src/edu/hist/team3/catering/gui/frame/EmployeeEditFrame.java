package edu.hist.team3.catering.gui.frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;

import edu.hist.team3.catering.database.Employee;
import edu.hist.team3.catering.database.manager.Services;
import edu.hist.team3.catering.gui.panel.EmployeeEditPanel;
import edu.hist.team3.catering.gui.panel.JobEditPanel;

public class EmployeeEditFrame extends JFrame implements ActionListener {
	private EmployeeEditPanel editPanel;
	private JButton applyButton;
	private Employee employee;
	
	public EmployeeEditFrame(Employee employee) {
		editPanel = new EmployeeEditPanel();
		applyButton = new JButton("Save changes");
		applyButton.addActionListener(this);
		
		setLayout(new BorderLayout());
		setSize(600, 200);
		setLocationRelativeTo(null);
		setTitle("Employee editor");
		setAlwaysOnTop(true);
		add(editPanel, BorderLayout.CENTER);
		add(applyButton, BorderLayout.SOUTH);
		setTitle("Employee editor");
		editPanel.fillInfo(employee);
		this.employee = employee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			editPanel.apply(employee);
		}
		catch (SQLException e1) {
			Services.showError("Error: Could not save changes!");
			e1.printStackTrace();
		}
		setVisible(false);
	}
}
