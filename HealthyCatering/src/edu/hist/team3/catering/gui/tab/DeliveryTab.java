package edu.hist.team3.catering.gui.tab;

import javax.swing.JPanel;

import edu.hist.team3.catering.database.manager.Services;

/*
 * Delivery GUI
--
--
+ getDeliveres()
+ computeDeliveryRoute()
+ changeDeliveryStatus()

 */
@SuppressWarnings("serial")
public class DeliveryTab extends JPanel {
	private Services services;
	
	public DeliveryTab(Services services) {
		this.services = services;
	}
	
}
