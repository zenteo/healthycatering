package edu.hist.team3.catering.gui.tabs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.hist.team3.catering.database.Employee;
import edu.hist.team3.catering.database.Job;
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
	private JList<Employee> employeeList;
	private JList<Job> jobList;
	
	public BossGUI(Services services) {
		setLayout(new BorderLayout());
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

		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(200, 600));
		buttonPanel.add(addEmployeeButton);
		buttonPanel.add(editEmployeeButton);
		buttonPanel.add(getEmployeeButton);
		buttonPanel.add(removeEmployeeButton);
		buttonPanel.add(addJobButton);
		buttonPanel.add(editJobButton);
		buttonPanel.add(removeJobButton);
		
		add(buttonPanel, BorderLayout.EAST);
		
		JPanel leftPanel = new JPanel();
		employeeList = new JList<Employee>();
		JScrollPane eScrollList = new JScrollPane(employeeList);
		leftPanel.add(eScrollList, BorderLayout.WEST);
		
		jobList = new JList<Job>();
		JScrollPane jScrollList = new JScrollPane(jobList);
		leftPanel.add(jScrollList, BorderLayout.EAST);
		add(leftPanel, BorderLayout.WEST);
	}


}
