package edu.hist.team3.catering.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField; 
import javax.swing.JTextField;

import edu.hist.team3.catering.database.manager.EmployeeManager;
import edu.hist.team3.catering.database.manager.Services;

public class LoginGUI extends JFrame {
	private Services services;
	private Toolkit toolkit;
	private JTextField loginField;
	private JPasswordField passwordField;
	
	/**
	 * Creates a new Login window
	 */
	public LoginGUI(Services services) {
		super("User login");
		this.services = services;
		
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(200, 150));
		setResizable(false);
		setLocationRelativeTo(null);
		
		loginField = new JTextField();
		loginField.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		loginField.setPreferredSize(new Dimension(150, 30));
		loginField.setText("Username");
		loginField.selectAll();
		
		passwordField = new JPasswordField();
		passwordField.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		passwordField.setPreferredSize(new Dimension(150, 30));
		passwordField.setText("Password");
		passwordField.selectAll();
		passwordField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				login();
			}
			
		});
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
			
		});
		
		JPanel loginPanel = new JPanel();
		loginPanel.setPreferredSize(new Dimension(150, 110));
		loginPanel.add(loginField);
		loginPanel.add(passwordField);
		loginPanel.add(loginButton);
		
		add(loginPanel);
	}
	
	/**
	 * When used, it checks the username and password in the textfields. 
	 * Then checks with the database if they are correct.
	 */
	private void login() {
		EmployeeManager eManager = services.getEmployeeManager();
		if(eManager == null)
			System.out.println("Is fucked");
		String username = loginField.getText();
		String password = extractPassword(passwordField.getPassword());
		
		if (eManager.getEmployee(username, password) != null) {
			setVisible(false);
			new MainGUI(eManager.getEmployee(username, password), services);
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
