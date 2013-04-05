package edu.hist.team3.catering.gui;

import java.awt.Toolkit;

import javax.swing.JFrame;

public class Login {
	
	public Login() {
		JFrame frame = new JFrame("User Login");
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		
		frame.setSize(toolkit.getScreenSize());
		frame.setUndecorated(true);
		frame.setVisible(true);
	}
	
}
