package edu.hist.team3.catering.gui.panel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.hist.team3.catering.database.Job;
/*
 * 	private String name;			// name				VARCHAR(32) NOT NULL
 *	private double yearlySalary;	// yearly_salary	DOUBLE		NOT NULL
 *	private double hourlySalary;	// hourly_salary	DOUBLE		NOT NULL
 *	private double percentSales;	// percent_sales	DOUBLE		NOT NULL
 *	private int privileges;			// privileges		INT				NOT NULL
 *	public static final int PRIVILEGE_ADMIN = 1; // 0000001
 *	public static final int PRIVILEGE_COOK = 2; // 0000010
 *	public static final int PRIVILEGE_SALESMAN = 4; // 0000100
 *	public static final int PRIVILEGE_DRIVER = 8; // 0001000
 *	public static final int PRIVILEGE_NUTRITIOUS = 16; // 0010000
 *	public static final int PRIVILEGE_RESOURCES = 32; // 0100000
 *	public static final int PRIVILEGE_STATISTICS = 64; // 1000000
 */

@SuppressWarnings("serial")
public class JobEditPanel extends JPanel {
	private JTextField nameField;
	private JFormattedTextField yearlySalary;
	private JFormattedTextField hourlySalary;
	private JFormattedTextField percentSales;
	private JCheckBox adminPrivileges;
	private JCheckBox cookPrivileges;
	private JCheckBox salemanPrivileges;
	private JCheckBox driverPrivileges;
	private JCheckBox nutritiousPrivileges;
	private JCheckBox resourcesPrivileges;
	private JCheckBox statisticsPrivileges;
	/**
	 * Creates a new instance of JobEditPanel
	 */
	public JobEditPanel() {
		nameField = new JTextField();
		yearlySalary = new JFormattedTextField();
		hourlySalary = new JFormattedTextField();
		percentSales = new JFormattedTextField();
		adminPrivileges = new JCheckBox("Admin privileges");
		cookPrivileges = new JCheckBox("Cook privileges");
		salemanPrivileges = new JCheckBox("Salesman privileges");
		driverPrivileges = new JCheckBox("Driver privileges");
		nutritiousPrivileges = new JCheckBox("Nutritious privileges");
		resourcesPrivileges = new JCheckBox("Stocks privileges");
		statisticsPrivileges = new JCheckBox("Statistics privileges");
		yearlySalary.setValue(new Double(0.0));
		hourlySalary.setValue(new Double(0.0));
		percentSales.setValue(new Double(0.0));
		
		JPanel options = new JPanel();
		options.setLayout(new GridLayout(4, 2));
		options.add(new JLabel("Name:"));
		options.add(nameField);
		options.add(new JLabel("Yearly salary:"));
		options.add(yearlySalary);
		options.add(new JLabel("Hourly salary:"));
		options.add(hourlySalary);
		options.add(new JLabel("Percent sales:"));
		options.add(percentSales);
		add(options);
		
		JPanel content = new JPanel();
		content.setLayout(new GridLayout(7, 1));
		content.add(adminPrivileges);
		content.add(cookPrivileges);
		content.add(salemanPrivileges);
		content.add(driverPrivileges);
		content.add(nutritiousPrivileges);
		content.add(resourcesPrivileges);
		content.add(statisticsPrivileges);

		setLayout(new BorderLayout());
		add(options, BorderLayout.NORTH);
		add(content, BorderLayout.CENTER);
	}
	/**
	 * Fills in information based on Job
	 * @param job
	 */
	public void fillInfo(Job job) {
		adminPrivileges.setSelected(job.hasPrivileges(Job.PRIVILEGE_ADMIN));
		cookPrivileges.setSelected(job.hasPrivileges(Job.PRIVILEGE_COOK));
		salemanPrivileges.setSelected(job.hasPrivileges(Job.PRIVILEGE_SALESMAN));
		driverPrivileges.setSelected(job.hasPrivileges(Job.PRIVILEGE_DRIVER));
		nutritiousPrivileges.setSelected(job.hasPrivileges(Job.PRIVILEGE_NUTRITIOUS));
		resourcesPrivileges.setSelected(job.hasPrivileges(Job.PRIVILEGE_RESOURCES));
		statisticsPrivileges.setSelected(job.hasPrivileges(Job.PRIVILEGE_STATISTICS));

		nameField.setText(job.getName());
		yearlySalary.setValue(job.getYearlySalary());
		hourlySalary.setValue(job.getHourlySalary());
		percentSales.setValue(job.getPercentSales());
	}
	/**
	 * Commits a new job
	 * @param job
	 * @throws SQLException
	 */
	public void apply(Job job) throws SQLException {
		job.setName(nameField.getText());
		job.setYearlySalary((Double)yearlySalary.getValue());
		job.setHourlySalary((Double)hourlySalary.getValue());
		job.setPercentSales((Double)percentSales.getValue());
		job.setPrivileges(0);
		if (adminPrivileges.isSelected())
			job.grantPrivileges(Job.PRIVILEGE_ADMIN);
		if (cookPrivileges.isSelected())
			job.grantPrivileges(Job.PRIVILEGE_COOK);
		if (salemanPrivileges.isSelected())
			job.grantPrivileges(Job.PRIVILEGE_SALESMAN);
		if (driverPrivileges.isSelected())
			job.grantPrivileges(Job.PRIVILEGE_DRIVER);
		if (nutritiousPrivileges.isSelected())
			job.grantPrivileges(Job.PRIVILEGE_NUTRITIOUS);
		if (resourcesPrivileges.isSelected())
			job.grantPrivileges(Job.PRIVILEGE_RESOURCES);
		if (statisticsPrivileges.isSelected())
			job.grantPrivileges(Job.PRIVILEGE_STATISTICS);
		job.commit();
	}
}
