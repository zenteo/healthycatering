package edu.hist.team3.catering.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar; 
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import edu.hist.team3.catering.database.Employee;

public class MainGUI {
	JFrame frame;
	JTabbedPane tabsPanel;
	Toolkit toolkit;
	Employee employee;
	
	public MainGUI(Employee employee) {
		this.employee = employee;
		toolkit = Toolkit.getDefaultToolkit();
		
		frame = new JFrame("Healthy Catering");
		frame.setPreferredSize(toolkit.getScreenSize());
		frame.setUndecorated(true);
		
		frame.setLayout(new BorderLayout());

		
		JMenuBar menu = new JMenuBar();
		JMenu menuProgram = new JMenu("Program");
		
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
		frame.setJMenuBar(menu);
		
		
		tabsPanel = new JTabbedPane();
		tabsPanel.addTab("Test", new JPanel());
		tabsPanel.addTab("Test2", new JPanel());
		
		frame.add(tabsPanel, BorderLayout.CENTER);

		frame.pack();
		frame.setVisible(true);
	}
	
	private void exitProgram() {
		Object[] options = {"Yes", "No"};
		
		int choice = JOptionPane.showOptionDialog(frame, "Do you really want to quit?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
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
	
}
