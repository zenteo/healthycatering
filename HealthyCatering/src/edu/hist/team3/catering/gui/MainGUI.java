package edu.hist.team3.catering.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class MainGUI {
	JFrame frame;
	JTabbedPane tabsPanel;
	
	
	public MainGUI() {
		frame = new JFrame("Healthy Catering");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		
		GridLayout gridLayout = new GridLayout(2, 1);
		frame.setLayout(gridLayout);
		
		JButton exitButton = new JButton("Quit");
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				exitProgram();
			}
			
		});
		tabsPanel = new JTabbedPane();
		tabsPanel.addTab("Test", new JPanel());
		tabsPanel.addTab("Test2", new JPanel());
		
		frame.add(exitButton);
		frame.add(tabsPanel);
		frame.setVisible(true);
	}
	
	private void exitProgram() {
		System.exit(0);
	}
	
}
