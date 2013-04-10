package edu.hist.team3.catering.gui.tabs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.hist.team3.catering.database.managers.EmployeeManager;
import edu.hist.team3.catering.database.managers.JobManager;


/*
 * Boss GUI
 --
 --
 + addEmployee()
 + editEmployee()
 + getEmployeeStats()
 + removeEmployee()
 + addJob()
 + editJob()
 + removeJob()
 */
@SuppressWarnings("serial")
public class BossGUI extends JPanel {
	EmployeeManager eManager;
	JobManager jManager;
	
	public BossGUI() {
		add(new JTextField("Hello"));
		JButton addEmployeeButton = new JButton("Add Employee");
		addEmployeeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				eManager.addEmployee();
			}
			
		});
		JButton editEmployeeButton = new JButton("Edit Employee");
		editEmployeeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				eManager.setEmployee();
			}
			
		});
		JButton getEmployeeButton = new JButton("Get Employee Stats");
		getEmployeeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				eManager.getEmployeeStats();
			}
			
		});
		JButton removeEmployeeButton = new JButton("Remove Employee");
		removeEmployeeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				eManager.removeEmployee();
			}
			
		});
		JButton addJobButton = new JButton("Add Job");
		addJobButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jManager.addJob();
			}
			
		});
		JButton editJobButton = new JButton("Edit Job");
		editJobButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jManager.editJob();
			}
			
		});
		JButton removeJobButton = new JButton("Remove Job");
		removeJobButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jManager.removeJob();
			}
			
		});
	}


}
