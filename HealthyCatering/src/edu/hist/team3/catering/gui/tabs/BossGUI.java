package edu.hist.team3.catering.gui.tabs;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import edu.hist.team3.catering.database.managers.EmployeeManager;
import edu.hist.team3.catering.database.managers.JobManager;
import edu.hist.team3.catering.database.managers.Services;


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
	private Services services;
	private EmployeeManager eManager;
	private JobManager jManager;
	
	public BossGUI(Services services) {
		this.services = services;
		Dimension buttonDimension = new Dimension(150, 70);
		
		JButton addEmployeeButton = new JButton("Add Employee");
		addEmployeeButton.setPreferredSize(buttonDimension);
		addEmployeeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
			
		});

		JButton editEmployeeButton = new JButton("Edit Employee");
		editEmployeeButton.setPreferredSize(buttonDimension);
		editEmployeeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
			
		});
		
		JButton getEmployeeButton = new JButton("Get Employee Stats");
		getEmployeeButton.setPreferredSize(buttonDimension);
		getEmployeeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
			
		});
		
		JButton removeEmployeeButton = new JButton("Remove Employee");
		removeEmployeeButton.setPreferredSize(buttonDimension);
		removeEmployeeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
			
		});
		
		JButton addJobButton = new JButton("Add Job");
		addJobButton.setPreferredSize(buttonDimension);
		addJobButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
			
		});
		
		JButton editJobButton = new JButton("Edit Job");
		editJobButton.setPreferredSize(buttonDimension);
		editJobButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
			
		});
		
		JButton removeJobButton = new JButton("Remove Job");
		removeJobButton.setPreferredSize(buttonDimension);
		removeJobButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
			
		});
		
		add(addEmployeeButton);
		add(editEmployeeButton);
		add(getEmployeeButton);
		add(removeEmployeeButton);
		add(addJobButton);
		add(editJobButton);
		add(removeJobButton);
	}


}
