package edu.hist.team3.catering.gui.tabs;

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
		cManager = CustomerManager.getInstance();
		customerScrollPane = new JScrollPane();
		customerList = new JList<Customer>();
		
		Customer[] list = cManager.getCustomers();
		System.out.println(list[0]);
		if(list != null)
			customerList.setListData(list);

		customerScrollPane.add(customerList);
		
		add(customerScrollPane);
	}

}
