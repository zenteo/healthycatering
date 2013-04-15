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
 * Cooking GUI
 --
 --
 + getDeliveries()
 + getDishesOfADelivery()
 + getResourcesOfADish()
 + changeDeliveryStatus()
 */
@SuppressWarnings("serial")
public class CookingGUI extends JPanel {
	private Services services;
	
	public CookingGUI(Services services) {
		this.services = services;
		
		Dimension buttonDimension = new Dimension(190, 70);
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout());
		centerPanel.setPreferredSize(new Dimension(190, 640));

		JButton getDeliveriesButton = new JButton("Get Deliveries");
		getDeliveriesButton.setPreferredSize(buttonDimension);
		getDeliveriesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}

		});
		JButton getDishesOfADeliveryButton = new JButton("Get dishes of a Deliveries");
		getDishesOfADeliveryButton.setPreferredSize(buttonDimension);
		getDishesOfADeliveryButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}

		});
		JButton getResourcesOfADishButton = new JButton("Get Resources of a dish");
		getResourcesOfADishButton.setPreferredSize(buttonDimension);
		getResourcesOfADishButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}

		});
		JButton changeDeliveryStatusButton = new JButton("Change delivery status");
		changeDeliveryStatusButton.setPreferredSize(buttonDimension);
		changeDeliveryStatusButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}

		});
		
		centerPanel.add(getDeliveriesButton);
		centerPanel.add(Box.createRigidArea(buttonDimension));
		centerPanel.add(getDishesOfADeliveryButton);
		centerPanel.add(Box.createRigidArea(buttonDimension));
		centerPanel.add(getResourcesOfADishButton);
		centerPanel.add(Box.createRigidArea(buttonDimension));
		centerPanel.add(changeDeliveryStatusButton);
		add(centerPanel);

	}
}
