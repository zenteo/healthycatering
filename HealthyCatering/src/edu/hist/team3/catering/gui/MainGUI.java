package edu.hist.team3.catering.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import edu.hist.team3.catering.database.Employee;
import edu.hist.team3.catering.database.Job;
import edu.hist.team3.catering.database.manager.Services;
import edu.hist.team3.catering.gui.tab.BossTab;
import edu.hist.team3.catering.gui.tab.CookingTab;
import edu.hist.team3.catering.gui.tab.CustomerTab;
import edu.hist.team3.catering.gui.tab.DeliveryTab;
import edu.hist.team3.catering.gui.tab.MenuTab;
import edu.hist.team3.catering.gui.tab.ResourcesTab;
import edu.hist.team3.catering.gui.tab.StatisticsTab;

public class MainGUI extends JFrame implements WindowListener {
	private Calendar loggedInAt;
	private Services services;
	private JTabbedPane tabsPanel;
	private Toolkit toolkit;
	private Employee employee;

	public MainGUI(Employee employee, Services services) {
		super("Healthy Catering");
		this.services = services;
		this.employee = employee;
		toolkit = Toolkit.getDefaultToolkit();

		loggedInAt = Calendar.getInstance();
		
		setPreferredSize(toolkit.getScreenSize());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout());
		
		addWindowListener(this);

		JMenuBar menu = new JMenuBar();
		JMenu menuProgram = new JMenu("Program");
		JMenuItem userInfo = new JMenuItem("User: "
				+ employee.getCustomer().getFirstName() + " "
				+ employee.getCustomer().getLastName());

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
		setJMenuBar(menu);

		tabsPanel = new JTabbedPane();
		addTabs();
		add(tabsPanel, BorderLayout.CENTER);

		pack();
		setVisible(true);
	}

	private void exitProgram() {
		Object[] options = { "Yes", "No" };

		int choice = JOptionPane.showOptionDialog(this,
				"Do you want to exit the program?", "Warning",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
				options, options[1]);
		if (choice == 0)
			System.exit(0);
	}

	private void aboutDevelopers() {
		JFrame aboutWindow = new JFrame("About Team 3");
		aboutWindow.setLayout(new FlowLayout());
		aboutWindow.setResizable(false);
		aboutWindow.setPreferredSize(new Dimension(400, 300));
		aboutWindow.setLocation((getWidth() - 400) / 2, (getHeight() - 300) / 2);

		JLabel aboutInfo = new JLabel();
		aboutInfo.setText("<html><h1>Test</h1></html>");

		aboutWindow.add(aboutInfo);
		aboutWindow.pack();
		aboutWindow.setVisible(true);
	}

	private void addTabs() {
		Job job = employee.getJob();
		if (job.hasPrivileges(Job.PRIVILEGE_ADMIN))
			tabsPanel.addTab("Administrator", new BossTab(services));

		if (job.hasPrivileges(Job.PRIVILEGE_COOK))
			tabsPanel.addTab("Cooking", new JScrollPane(
					new CookingTab(services)));

		if (job.hasPrivileges(Job.PRIVILEGE_RESOURCES))
			tabsPanel.addTab("Resources", new ResourcesTab(services));

		if (job.hasPrivileges(Job.PRIVILEGE_SALESMAN))
			tabsPanel.addTab("Salesman", new JScrollPane(new CustomerTab(
					services)));

		if (job.hasPrivileges(Job.PRIVILEGE_DRIVER))
			tabsPanel.addTab("Delivery", new DeliveryTab(services));

		if (job.hasPrivileges(Job.PRIVILEGE_NUTRITIOUS))
			tabsPanel.addTab("Nutritious", new MenuTab(services));
		
		if (job.hasPrivileges(Job.PRIVILEGE_STATISTICS))
			tabsPanel.addTab("Statistics", new StatisticsTab(services));
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		Calendar now = Calendar.getInstance();
		double deltaSeconds = now.get(Calendar.SECOND) - loggedInAt.get(Calendar.SECOND);
		double deltaMinutes = now.get(Calendar.MINUTE) - loggedInAt.get(Calendar.MINUTE) + deltaSeconds / 60.0;
		double deltaHours = now.get(Calendar.HOUR_OF_DAY) - loggedInAt.get(Calendar.HOUR_OF_DAY) + deltaMinutes / 60.0;
		deltaHours += (now.get(Calendar.DAY_OF_YEAR) - loggedInAt.get(Calendar.DAY_OF_YEAR)) * 24.0;
		employee.setSessionHours(employee.getSessionHours() + deltaHours);
		System.out.println("Session hours: " + deltaHours);
		try {
			employee.commit();
		}
		catch (SQLException e) {
			Services.showError("Error: Could not save session hours!");
			e.printStackTrace();
		}
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
