package edu.hist.team3.catering.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import edu.hist.team3.catering.database.Employee;
import edu.hist.team3.catering.database.Job;
import edu.hist.team3.catering.database.managers.Services;
import edu.hist.team3.catering.gui.tabs.BossGUI;
import edu.hist.team3.catering.gui.tabs.CookingGUI;
import edu.hist.team3.catering.gui.tabs.CustomerGUI;
import edu.hist.team3.catering.gui.tabs.DeliveryGUI;
import edu.hist.team3.catering.gui.tabs.MenuGUI;
import edu.hist.team3.catering.gui.tabs.ResourcesGUI;

public class MainGUI {
	private Services services;
	private JFrame frame;
	private JTabbedPane tabsPanel;
	private Toolkit toolkit;
	private Employee employee;
	
	public MainGUI(Employee employee, Services services) {
		this.services = services;
		this.employee = employee;
		toolkit = Toolkit.getDefaultToolkit();
		
		frame = new JFrame("Healthy Catering");
		frame.setPreferredSize(toolkit.getScreenSize());
		frame.setUndecorated(true);
		
		frame.setLayout(new BorderLayout());

		
		JMenuBar menu = new JMenuBar();
		JMenu menuProgram = new JMenu("Program");
		JMenuItem userInfo = new JMenuItem("User: " + employee.getCustomer().getFirstName() + " " + employee.getCustomer().getLastName());
		
		JMenuItem programExit = new JMenuItem("Exit");
		programExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				exitProgram();
			}
			
		});
		
		JMenuItem showDevelopers = new JMenuItem("About");
		showDevelopers.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				aboutDevelopers();
			}
			
		});
		
		menuProgram.add(showDevelopers);
		menuProgram.add(programExit);
		menu.add(menuProgram);
		menu.add(userInfo);
		frame.setJMenuBar(menu);
		
		tabsPanel = new JTabbedPane();
		addTabs();
		frame.add(tabsPanel, BorderLayout.CENTER);

		frame.pack();
		frame.setVisible(true);
	}
	
	private void exitProgram() {
		Object[] options = {"Yes", "No"};
		
		int choice = JOptionPane.showOptionDialog(frame, "Do you want to exit the program?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
		if(choice == 0)
			System.exit(0);
	}
	
	private void aboutDevelopers() {
		JFrame aboutWindow = new JFrame("About Team 3");
		aboutWindow.setLayout(new FlowLayout());
		aboutWindow.setResizable(false);
		aboutWindow.setPreferredSize(new Dimension(400, 300));
		aboutWindow.setLocation(
				(frame.getWidth() - 400) / 2, 
				(frame.getHeight() - 300) / 2
				);
		
		JLabel aboutInfo = new JLabel();
		aboutInfo.setText("<html><h1>Test</h1></html>");

		aboutWindow.add(aboutInfo);
		aboutWindow.pack();
		aboutWindow.setVisible(true);
	}
	
	private void addTabs() {
		Job job = employee.getJob();
		if (job.hasPrivileges(Job.PRIVILEGE_ADMIN))
			tabsPanel.addTab("Administrator", new BossGUI(services));
		
		if (job.hasPrivileges(Job.PRIVILEGE_COOK))
			tabsPanel.addTab("Cooking", new CookingGUI(services));

		if (job.hasPrivileges(Job.PRIVILEGE_RESOURCES))
			tabsPanel.addTab("Resources", new ResourcesGUI(services));
		
		if (job.hasPrivileges(Job.PRIVILEGE_SALESMAN))
			tabsPanel.addTab("Salesman", new CustomerGUI(services));

		if (job.hasPrivileges(Job.PRIVILEGE_DRIVER))
			tabsPanel.addTab("Delivery", new DeliveryGUI(services));

		if (job.hasPrivileges(Job.PRIVILEGE_NUTRITIOUS))
			tabsPanel.addTab("Nutritious", new MenuGUI(services));
	}
	
}
