package edu.hist.team3.catering.gui.tab;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import edu.hist.team3.catering.database.manager.Services;

public class StatisticsTab extends JPanel {
	private Services services;
	
	public StatisticsTab(Services services) {
		this.services = services;
		setLayout(new BorderLayout());
		displayTestPieChart();
		
		add(totalPanel());
	}
	
	public void displayTestPieChart() {
		DefaultPieDataset data = new DefaultPieDataset();
		data.setValue("The pie!", 50);
		data.setValue("Not the pie!", 50);
		
		PiePlot pie = new PiePlot(data);
		JFreeChart chart = new JFreeChart(pie);
		ChartPanel frame = new ChartPanel(chart);	
		add(frame, BorderLayout.CENTER);
	}
	
	private JPanel outcomePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		Double value = services.getDeliveryManager().getTotalOutcome();

		JLabel text;
		if (value != -1.0)
			text = new JLabel("Total outcome: " + value);
		else
			text = new JLabel("Unable to fetch information.");
		
		panel.add(text, BorderLayout.CENTER);
		return panel;
	}
	
	private JPanel incomePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		Double value = services.getDeliveryManager().getTotalIncome();

		JLabel text;
		if (value != -1.0)
			text = new JLabel("Total income: " + value);
		else
			text = new JLabel("Unable to fetch information.");
		
		panel.add(text, BorderLayout.CENTER);
		return panel;
	}
	
	private JPanel totalPanel() {
		JPanel total = new JPanel();
		total.setLayout(new BorderLayout());
		total.add(incomePanel(), BorderLayout.NORTH);
		total.add(outcomePanel(), BorderLayout.SOUTH);
		return total;
	}
}
