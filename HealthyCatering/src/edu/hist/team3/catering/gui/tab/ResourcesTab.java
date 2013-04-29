package edu.hist.team3.catering.gui.tab;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.hist.team3.catering.database.Resource;
import edu.hist.team3.catering.database.manager.Services;
import edu.hist.team3.catering.gui.frame.ResourceInfoFrame;
import edu.hist.team3.catering.gui.panel.StocksSearch;

@SuppressWarnings("serial")
public class ResourcesTab extends JPanel {
	private Services services;
	/**
	 * Creates a new Resources Tab
	 * @param services
	 */
	public ResourcesTab(Services services) {
		this.services = services;
		
		final StocksSearch stockSearch = new StocksSearch(services);
		final JFormattedTextField stockCount = new JFormattedTextField();
		
		stockSearch.getResultList().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				Resource selected = stockSearch.getSelected();
				if (selected != null) {
					stockCount.setValue(selected.getStockCount());
				}
			}
		});
		
		JButton button1 = new JButton("Save");
		button1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Resource selected = stockSearch.getSelected();
				if (selected != null) {
					selected.setStockCount(((Number)stockCount.getValue()).doubleValue());
					try {
						selected.commit();
						stockSearch.doSearch();
					}
					catch (SQLException e) {
						Services.showError("Error: Could not save changes!");
					}
				}
				else {
					Services.showError("Select a resource first!");
				}
			}
		});
		
		JButton button2 = new JButton("Info");
		button2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Resource selected = stockSearch.getSelected();
				if (selected != null) {
					ResourceInfoFrame infoFrame = new ResourceInfoFrame(selected);
					infoFrame.setVisible(true);
				}
				else {
					Services.showError("Select a resource first!");
				}
			}
		});
		
		JPanel options = new JPanel();
		options.setLayout(new GridLayout(1, 2));
		options.add(new JLabel("Stocks count:"));
		options.add(stockCount);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(3, 1));
		rightPanel.add(options);
		rightPanel.add(button1);
		rightPanel.add(button2);
		
		JPanel rightHolder = new JPanel();
		rightHolder.setLayout(new BorderLayout());
		rightHolder.add(rightPanel, BorderLayout.NORTH);
		
		setLayout(new BorderLayout());
		add(stockSearch, BorderLayout.CENTER);
		add(rightHolder, BorderLayout.EAST);
	}
	
}
