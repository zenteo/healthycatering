package edu.hist.team3.catering.gui.tab;

import javax.swing.JPanel;

import edu.hist.team3.catering.database.manager.Services;

public class StatisticsTab extends JPanel {
	private Services services;
	
	public StatisticsTab(Services services) {
		this.services = services;
	}
}
