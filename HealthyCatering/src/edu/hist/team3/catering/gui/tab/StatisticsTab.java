package edu.hist.team3.catering.gui.tab;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

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

@SuppressWarnings("serial")
public class StatisticsTab extends JPanel {
	private Services services;
	private JPanel centralPanel;
	private JPanel leftPanel;
	private JComboBox<String> chartSelection;
	
	public StatisticsTab(Services services) {
		this.services = services;
		setLayout(new BorderLayout());
		
		update();
	}
	
	private void update() {
		this.removeAll();
		String[] choiceList = {
				"Income chart",
				"Year",
				"Profits current year"
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
		
		DecimalFormat form = new DecimalFormat("#.##");
		String outValue = form.format(services.getDeliveryManager().getTotalOutcome());
		String innValue = form.format(services.getDeliveryManager().getTotalIncome());

		JLabel outText = new JLabel("Total outcome: " + outValue);
		JLabel innText = new JLabel("Total income: " + innValue);
		
		JLabel brutto;
		double inn = services.getDeliveryManager().getTotalIncome(); 
		double out = services.getDeliveryManager().getTotalOutcome();
		
		if (inn != -1.0 || out != -1.0)
			brutto = new JLabel("Total: " + form.format(inn - out));
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
		centralPanel.add(profitYearChart(), choiceList[1]);
		centralPanel.add(profitMonthsCurrentYearChart(), choiceList[2]);
		
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
	
	private JPanel profitYearChart() {
		DecimalFormat df = new DecimalFormat("#.##");
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		int year = new GregorianCalendar().get(Calendar.YEAR);
		for (int i=2013; i <= year; i++) {
			double profit = services.getDeliveryManager().getProfitOver("" + i + "-01-01", "" + i + "-12-31");
			data.setValue(profit, "data", "" + i + ": " + df.format(profit));
		}
		
		JFreeChart chart = ChartFactory.createBarChart3D(
			"Yearly profits", 
			"Months", 
			"Profit", 
			data, 
			PlotOrientation.VERTICAL, 
			false, 
			true, 
			false
		);
		
		ChartPanel chartPanel = new ChartPanel(chart);
		return chartPanel;
	}
	
	private JPanel profitMonthsCurrentYearChart() {
		DecimalFormat df = new DecimalFormat("#.##");
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		int year = new GregorianCalendar().get(Calendar.YEAR);

		for (int month=1; month <= 12; month++) {
			int lastDayOfMonth = new GregorianCalendar(year, month, 1).getMaximum(Calendar.DAY_OF_MONTH);
			double profit = services.getDeliveryManager().getProfitOver("" + year + "-" + month + "-01", "" + year + "-12-" + lastDayOfMonth);
			data.setValue(profit, "data", "" + getMonth(month));
		}
		
		JFreeChart chart = ChartFactory.createBarChart3D(
			"Profits for " + year, 
			"Months", 
			"Profit", 
			data, 
			PlotOrientation.VERTICAL, 
			false, 
			true, 
			false
		);
		
		ChartPanel chartPanel = new ChartPanel(chart);
		return chartPanel;
	}
	
	private String getMonth(int i) {
		switch (i) {
		case 1:
			return "January";
		case 2:
			return "February";
		case 3:
			return "March";
		case 4:
			return "April";
		case 5:
			return "May";
		case 6:
			return "June";
		case 7:
			return "July";
		case 8:
			return "August";
		case 9:
			return "September";
		case 10:
			return "October";
		case 11:
			return "November";
		case 12:
			return "December";
		}
		return "Error";
	}

}
