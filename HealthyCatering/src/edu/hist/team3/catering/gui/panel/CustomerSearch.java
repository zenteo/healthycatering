package edu.hist.team3.catering.gui.panel;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.manager.Services;

@SuppressWarnings("serial")
public class CustomerSearch extends SearchPanel<Customer> {
	private Services services;
	/**
	 * Creates a new instance of CustomerSearch based on services
	 * @param services
	 */
	public CustomerSearch(Services services) {
		this.services = services;
		onSearch("");
	}
	
	@Override
	public void onSearch(String text) {
		ArrayList<Customer> ret = services.getCustomerManager().findCustomer(text);
		DefaultListModel<LabeledObject<Customer>> model = (DefaultListModel<LabeledObject<Customer>>)this.searchResult.getModel();
		model.clear();
		
		for (Customer c : ret) {
			model.addElement(new LabeledObject<Customer>(c.getFirstName() + " " + c.getLastName(), c));
		}
		repaint();
	}

}
