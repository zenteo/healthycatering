package edu.hist.team3.catering.gui.tab;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import edu.hist.team3.catering.database.manager.Services;

public class StatisticsTab extends JPanel {
	private Services services;
	private JPanel centralPanel;
	private JComboBox chartSelection;
	
	public StatisticsTab(Services services) {
		this.services = services;
		setLayout(new BorderLayout());
		
		String[] choiceList = {
				"Income chart",
				"Some other chart"
		};
		chartSelection = new JComboBox(choiceList);
		chartSelection.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				System.out.println(chartSelection.getSelectedIndex());
			}
		});
		
		
		JPanel outcomePanel = new JPanel();
		outcomePanel.setLayout(new BorderLayout());
		Double outValue = services.getDeliveryManager().getTotalOutcome();

		JLabel outText;
		if (outValue != -1.0)
			outText = new JLabel("Total outcome: " + outValue);
		else
			outText = new JLabel("Unable to fetch information.");

		outcomePanel.add(outText, BorderLayout.CENTER);

		
		JPanel incomePanel = new JPanel();
		incomePanel.setLayout(new BorderLayout());
		Double innValue = services.getDeliveryManager().getTotalIncome();

		JLabel innText;
		if (innValue != -1.0)
			innText = new JLabel("Total income: " + innValue);
		else
			innText = new JLabel("Unable to fetch information.");
		
		incomePanel.add(innText, BorderLayout.CENTER);

		
		JButton refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			
		});

		
		JPanel leftHolder = new JPanel();
		leftHolder.setLayout(new GridLayout(4,1));
		leftHolder.add(incomePanel);
		leftHolder.add(outcomePanel);
		leftHolder.add(refreshButton);
		leftHolder.add(chartSelection);
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		
		leftPanel.add(leftHolder, BorderLayout.NORTH);
		
		centralPanel = new JPanel();		
		add(leftPanel, BorderLayout.WEST);
		add(centralPanel, BorderLayout.CENTER);
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
}
