package edu.hist.team3.catering.gui.tab;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.hist.team3.catering.database.Job;
import edu.hist.team3.catering.database.manager.Services;
import edu.hist.team3.catering.gui.frame.CustomerEditFrame;
import edu.hist.team3.catering.gui.panel.EmployeeSearch;
import edu.hist.team3.catering.gui.panel.JobSearch;


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
public class BossTab extends JPanel {
	private Services services;
	private EmployeeSearch employeeSearch;
	private JobSearch jobSearch;
	private JList<Job> jobList;
	
	public BossTab(Services services) {
		setLayout(new BorderLayout());
		this.services = services;
		Dimension buttonDimension = new Dimension(150, 70);
		
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(1,2));
		employeeSearch = new EmployeeSearch(services);
		employeeSearch.getResultList().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				
			}
			
		});
		
		
		jobSearch = new JobSearch(services);
		jobSearch.getResultList().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				
			}
			
		});

		centerPanel.add(employeeSearch);
		centerPanel.add(jobSearch);
		add(centerPanel, BorderLayout.CENTER);
		
		
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

		JPanel rightButtonPanel = new JPanel();
		rightButtonPanel.setLayout(new BoxLayout(rightButtonPanel, BoxLayout.PAGE_AXIS));
		rightButtonPanel.add(addJobButton);
		rightButtonPanel.add(editJobButton);
		rightButtonPanel.add(removeJobButton);
		
		JPanel leftButtonPanel = new JPanel();
		leftButtonPanel.setLayout(new BoxLayout(leftButtonPanel, BoxLayout.PAGE_AXIS));
		leftButtonPanel.add(addEmployeeButton);
		leftButtonPanel.add(editEmployeeButton);
		leftButtonPanel.add(getEmployeeButton);
		leftButtonPanel.add(removeEmployeeButton);
		
		add(rightButtonPanel, BorderLayout.EAST);
		add(leftButtonPanel, BorderLayout.WEST);
	}
}
