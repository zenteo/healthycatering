package edu.hist.team3.catering.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import edu.hist.team3.catering.database.Employee;
import edu.hist.team3.catering.database.manager.Services;

@SuppressWarnings("serial")
public class LoginFrame extends JFrame {
	private Services services;
	private JTextField loginField;
	private JPasswordField passwordField;
	
	/**
	 * Creates a new Login window
	 */
	public LoginFrame(Services services) {
		super("User login");
		this.services = services;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(220, 120));
		setLocationRelativeTo(null);
		
		JPanel content = new JPanel();
		content.setLayout(new GridLayout(2, 2));
		
		content.add(new JLabel("Username:"));
		loginField = new JTextField();
		loginField.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		loginField.setPreferredSize(new Dimension(150, 30));
		loginField.setText("");
		loginField.selectAll();
		content.add(loginField);
		
		content.add(new JLabel("Password:"));
		passwordField = new JPasswordField();
		passwordField.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		passwordField.setPreferredSize(new Dimension(150, 30));
		passwordField.setText("");
		passwordField.selectAll();
		passwordField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				login();
			}
			
		});
		content.add(passwordField);
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
			
		});
		
		setLayout(new BorderLayout());
		add(content, BorderLayout.NORTH);
		add(loginButton, BorderLayout.SOUTH);
	}
	
	/**
	 * When used, it checks the username and password in the textfields. 
	 * Then checks with the database if they are correct.
	 */
	private void login() {
		String username = loginField.getText();
		String password = extractPassword(passwordField.getPassword());
		Employee employee = services.getEmployeeManager().getEmployee(username, password);
		if (employee != null) {
			setVisible(false);
			new MainFrame(employee, services);
		}
		else {
			Services.showError("Wrong username or password!");
		}
		
	}
	
	/**
	 * Takes the password in char[] format and turns it into a String
	 * 
	 * @param passwordAsChar[]
	 * @return passwordAsString
	 */
	private String extractPassword(char[] password) {
		String pw = "";
		for(int i=0; i<password.length; i++) {
			pw += password[i];
		}
			
		return pw;
	}
	
}
