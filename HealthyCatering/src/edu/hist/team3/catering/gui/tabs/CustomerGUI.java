package edu.hist.team3.catering.gui.tabs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.managers.CustomerManager;

/*
 * Customer GUI
 --
 --
 + findPlans()
 + addPlan()
 + editPlan()
 + removePlan()
 + findCostumers()
 + addCostumer()
 + editCostumer()
 + removeCostumer()
 */
@SuppressWarnings("serial")
public class CustomerGUI extends JPanel {
	private JScrollPane customerScrollPane;
	private JList<Customer> customerList;
	private CustomerManager cManager;
	private JTextField searchBar;
	
	public CustomerGUI() {
		setLayout(new BorderLayout());
		
		cManager = CustomerManager.getInstance();
		
		Customer[] list = cManager.getCustomers();
		System.out.println(list[0]);
		customerList = new JList<Customer>(list);

		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(700, 640));
		leftPanel.setLayout(new FlowLayout());
		customerScrollPane = new JScrollPane(customerList);
		customerScrollPane.setPreferredSize(new Dimension(500, 600));
		searchBar = new JTextField("Search bar");
		searchBar.selectAll();
		searchBar.setPreferredSize(new Dimension(300, 30));
		leftPanel.add(searchBar);
		leftPanel.add(customerScrollPane);
		
		add(leftPanel, BorderLayout.WEST);
	}

}
