package edu.hist.team3.catering.gui.panel;

import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.hist.team3.catering.database.Employee;

public class EmployeeEditPanel extends JPanel {
	private CustomerEditPanel customerEdit;
	private JTextField username;
	private JTextField password;
	private JTextField email;
	private JLabel jobTitle;
	
	
	public EmployeeEditPanel() {
		customerEdit = new CustomerEditPanel();
		username = new JTextField();
		password = new JTextField();
		email = new JTextField();
		jobTitle = new JLabel();
		
		JPanel employeeEdit = new JPanel();
		employeeEdit.setLayout(new GridLayout(4, 2));
		employeeEdit.add(new JLabel("Username:"));
		employeeEdit.add(username);
		employeeEdit.add(new JLabel("Password:"));
		employeeEdit.add(password);
		employeeEdit.add(new JLabel("Email:"));
		employeeEdit.add("Email:", email);
		employeeEdit.add(new JLabel("Job:"));
		employeeEdit.add(jobTitle);
		
		setLayout(new GridLayout(1, 2));
		add(customerEdit);
		add(employeeEdit);
		
	}
	
	public void fillInfo(Employee employee) {
		customerEdit.fillInfo(employee.getCustomer());
		jobTitle.setText(employee.getJob().getName());
		username.setText(employee.getUsername());
		password.setText(employee.getPassword());
		email.setText(employee.getEmail());
	}
	
	public void apply(Employee employee) throws SQLException {
		customerEdit.apply(employee.getCustomer());
		employee.setUsername(username.getText());
		employee.setPassword(password.getText());
		employee.setEmail(email.getText());
		employee.commit();
	}
}
