package edu.hist.team3.catering.gui.tabs;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
	JScrollPane customerScrollPane;
	JList<Customer> customerList;
	CustomerManager cManager;
	
	public CustomerGUI() {
		setLayout(new FlowLayout());
		
		cManager = CustomerManager.getInstance();
		
		Customer[] list = cManager.getCustomers();
		System.out.println(list[0]);
		customerList = new JList<Customer>(list);
		
		customerScrollPane = new JScrollPane(customerList);
		customerScrollPane.setPreferredSize(new Dimension(500, 600));
		
		this.add(customerScrollPane);
		this.add(new JButton("Test"));
	}

}
