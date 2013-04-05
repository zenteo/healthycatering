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

public class LoginGUI {
	JFrame frame;
	Toolkit toolkit;
	JTextField loginField;
	JPasswordField passwordField;
	
	/**
	 * Creates a new Login window
	 */
	public LoginGUI() {
		frame = new JFrame("User Login");
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(200, 150));
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
				
		loginField = new JTextField();
		loginField.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		loginField.setPreferredSize(new Dimension(150, 30));
		
		passwordField = new JPasswordField();
		passwordField.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		passwordField.setPreferredSize(new Dimension(150, 30));
		
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
		
		frame.add(loginPanel);
		
		frame.setVisible(true);
	}
	
	/**
	 * When used, it checks the username and password in the textfields. 
	 * Then checks with the database if they are correct.
	 */
	private void login() {
		// String username = loginField.getText();
		// String password = extractPassword(passwordField.getPassword());
	}
	
	/**
	 * Takes the password in char[] format and turns it into a String
	 * 
	 * @param passwordAsChar[]
	 * @return passwordAsString
	 */
	@SuppressWarnings("unused")
	private String extractPassword(char[] password) {
		String pw = "";
		for(int i=0; i<password.length; i++) {
			pw += password[i];
		}
			
		return pw;
	}
	
}
