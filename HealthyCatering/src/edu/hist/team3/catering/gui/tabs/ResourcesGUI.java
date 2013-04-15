package edu.hist.team3.catering.gui.tabs;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.hist.team3.catering.database.managers.Services;

/*
 * Stocks GUI
 --
 --
 + getResources()
 + getTodaysResources()
 + getResourcesWithCriticallyLowStock-count()
 + changeTheStock-CountOfAResource()

 */
@SuppressWarnings("serial")
public class ResourcesGUI extends JPanel {
	private Services services;
	
	public ResourcesGUI(Services services) {
		this.services = services;
		
		Dimension buttonDimension = new Dimension(190, 70);
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout());
		centerPanel.setPreferredSize(new Dimension(190, 640));
		
		JButton getResourcesButton = new JButton("Get Resources");
		getResourcesButton.setPreferredSize(buttonDimension);
		getResourcesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
			
		});JButton getTodaysResourcesButton = new JButton("Get todays resources");
		getTodaysResourcesButton.setPreferredSize(buttonDimension);
		getTodaysResourcesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
			
		});JButton getCriticallyLowButton = new JButton("Get resources with low number");
		getCriticallyLowButton.setPreferredSize(buttonDimension);
		getCriticallyLowButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
			
		});JButton setStockCountButton = new JButton("Set the Stock count");
		setStockCountButton.setPreferredSize(buttonDimension);
		setStockCountButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
			
		});
		centerPanel.add(getResourcesButton);
		centerPanel.add(Box.createRigidArea(buttonDimension));
		centerPanel.add(getTodaysResourcesButton);
		centerPanel.add(Box.createRigidArea(buttonDimension));
		centerPanel.add(getCriticallyLowButton);
		centerPanel.add(Box.createRigidArea(buttonDimension));
		centerPanel.add(setStockCountButton);
		add(centerPanel);
	}
	
}
