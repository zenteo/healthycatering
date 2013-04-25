package edu.hist.team3.catering.gui.tab;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import edu.hist.team3.catering.database.manager.Services;

public class StatisticsTab extends JPanel {
	private Services services;
	private JPanel centralPanel;
	private JPanel leftPanel;
	private JComboBox chartSelection;
	
	public StatisticsTab(Services services) {
		this.services = services;
		setLayout(new BorderLayout());
		
		update();
	}
	
	private void update() {
		this.removeAll();
		String[] choiceList = {
				"Income chart",
				"Stats chart"
		};
		chartSelection = new JComboBox<String>(choiceList);
		chartSelection.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				CardLayout layout = (CardLayout) centralPanel.getLayout();
				layout.show(centralPanel, (String) arg0.getItem());
				//changeChart(chartSelection.getSelectedIndex());
			}
		});
		
		
		Double outValue = services.getDeliveryManager().getTotalOutcome();

		JLabel outText;
		if (outValue != -1.0)
			outText = new JLabel("Total outcome: " + outValue);
		else
			outText = new JLabel("Unable to fetch information.");
		
		
		Double innValue = services.getDeliveryManager().getTotalIncome();

		JLabel innText;
		if (innValue != -1.0)
			innText = new JLabel("Total income: " + innValue);
		else
			innText = new JLabel("Unable to fetch information.");
		
		JLabel brutto;
		if (innValue != -1.0 || outValue != -1.0)
			brutto = new JLabel("Total: " + (innValue - outValue));
		else
			brutto = new JLabel("");

		
		JButton refreshButton = new JButton("Refresh everything");
		refreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});

		JPanel leftHolder = new JPanel();
		leftHolder.setLayout(new GridLayout(5,1));
		leftHolder.add(innText);
		leftHolder.add(outText);
		leftHolder.add(brutto);
		leftHolder.add(refreshButton);
		leftHolder.add(chartSelection);
		
		leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add(leftHolder, BorderLayout.NORTH);
		
		centralPanel = new JPanel(new CardLayout());
		centralPanel.add(testPieChart(), choiceList[0]);
		centralPanel.add(statsChart(), choiceList[1]);
		
		add(leftPanel, BorderLayout.WEST);
		add(centralPanel, BorderLayout.CENTER);
	}
	
	public JPanel testPieChart() {
		DefaultPieDataset data = new DefaultPieDataset();
		data.setValue("The pie!", 50);
		data.setValue("Not the pie!", 50);
		
		PiePlot pie = new PiePlot(data);
		JFreeChart chart = new JFreeChart(pie);
		ChartPanel frame = new ChartPanel(chart);	
		return frame;
	}
	
	private JPanel statsChart() {
		
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		data.setValue(1, "Datastuff", "Something");
		data.setValue(2, "Datastuff", "Something else");
		data.setValue(2, "Datastuff", "Something else2");
		data.setValue(2, "Datastuff", "Something else3");
		data.setValue(2, "Datastuff", "Something else4");
		
		JFreeChart chart = ChartFactory.createBarChart(
			"A bar chart",
			"Somethings", 
			"Datastuff", 
			data, 
			PlotOrientation.VERTICAL, 
			false, 
			true, 
			false
		);
		
		ChartPanel chartPanel = new ChartPanel(chart);
		return chartPanel;
	}
}
