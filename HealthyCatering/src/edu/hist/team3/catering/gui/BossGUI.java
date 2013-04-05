package edu.hist.team3.catering.gui;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
public class BossGUI extends JPanel{

	JFrame frame;
	Toolkit toolkit;

	public BossGUI() {
		JTextField testStuff = new JTextField();
		this.add(testStuff);
	}


}
