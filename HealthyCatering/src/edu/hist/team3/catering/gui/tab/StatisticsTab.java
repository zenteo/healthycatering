package edu.hist.team3.catering.gui.tab;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import edu.hist.team3.catering.database.manager.Services;

public class StatisticsTab extends JPanel {
	private Services services;
	
	public StatisticsTab(Services services) {
		this.services = services;
		setLayout(new BorderLayout());
		displayTestPieChart();
	}
	
	public void displayTestPieChart() {
		DefaultPieDataset data = new DefaultPieDataset();
		data.setValue("The pie!", 50);
		data.setValue("Not the pie!", 50);
		
		JFreeChart chart = ChartFactory.createPieChart3D("Fucking pie", data, true, true, false);
		ChartPanel frame = new ChartPanel(chart);
		add(frame, BorderLayout.CENTER);
	}
}
