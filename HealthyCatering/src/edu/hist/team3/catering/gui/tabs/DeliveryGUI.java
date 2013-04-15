package edu.hist.team3.catering.gui.tabs;

import javax.swing.JPanel;

import edu.hist.team3.catering.database.managers.Services;

/*
 * Delivery GUI
--
--
+ getDeliveres()
+ computeDeliveryRoute()
+ changeDeliveryStatus()

 */
@SuppressWarnings("serial")
public class DeliveryGUI extends JPanel {
	private Services services;
	
	public DeliveryGUI(Services services) {
		this.services = services;
	}
	
}
