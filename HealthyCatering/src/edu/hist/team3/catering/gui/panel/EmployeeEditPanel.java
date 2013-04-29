package edu.hist.team3.catering.gui.panel;

import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.hist.team3.catering.database.Employee;

@SuppressWarnings("serial")
public class EmployeeEditPanel extends JPanel {
	private CustomerEditPanel customerEdit;
	private JTextField username;
	private JTextField password;
	private JTextField email;
	private JLabel jobTitle;
	private JLabel employmentDate;
	
	/**
	 * Creates a new instance of EmployeeeEditPanel
	 */
	public EmployeeEditPanel() {
		customerEdit = new CustomerEditPanel();
		username = new JTextField();
		password = new JTextField();
		email = new JTextField();
		jobTitle = new JLabel();
		employmentDate = new JLabel();
		
		JPanel employeeEdit = new JPanel();
		employeeEdit.setLayout(new GridLayout(5, 2));
		employeeEdit.add(new JLabel("Username:"));
		employeeEdit.add(username);
		employeeEdit.add(new JLabel("Password:"));
		employeeEdit.add(password);
		employeeEdit.add(new JLabel("Email:"));
		employeeEdit.add("Email:", email);
		employeeEdit.add(new JLabel("Job:"));
		employeeEdit.add(jobTitle);
		employeeEdit.add(new JLabel("Employed:"));
		employeeEdit.add(employmentDate);
		
		setLayout(new GridLayout(1, 2));
		add(customerEdit);
		add(employeeEdit);
		
	}
	/**
	 * Fills in information about employee
	 * @param employee
	 */
	public void fillInfo(Employee employee) {
		customerEdit.fillInfo(employee.getCustomer());
		jobTitle.setText(employee.getJob().getName());
		username.setText(employee.getUsername());
		password.setText(employee.getPassword());
		email.setText(employee.getEmail());
		employmentDate.setText(employee.getEmploymentDate().toString());
	}
	/**
	 * Commits to database
	 * @param employee
	 * @throws SQLException
	 */
	public void apply(Employee employee) throws SQLException {
		customerEdit.apply(employee.getCustomer());
		employee.setUsername(username.getText());
		employee.setPassword(password.getText());
		employee.setEmail(email.getText());
		employee.commit();
	}
}
