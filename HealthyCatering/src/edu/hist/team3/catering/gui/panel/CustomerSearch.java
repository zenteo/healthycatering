package edu.hist.team3.catering.gui.panel;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.managers.Services;

public class CustomerSearch extends SearchPanel {
	private Services services;
	
	public CustomerSearch(Services services) {
		this.services = services;
		onSearch("");
	}
	
	public Customer getSelected() {
		if (getResultList().getSelectedValue() == null)
			return null;
		return (Customer)getResultList().getSelectedValue().getObject();
	}
	
	@Override
	public void onSearch(String text) {
		ArrayList<Customer> ret = services.getCustomerManager().findCustomer(text);
		DefaultListModel<LabeledObject> model = (DefaultListModel<LabeledObject>)this.searchResult.getModel();
		model.clear();
		for (Customer c : ret) {
			model.addElement(new LabeledObject<Customer>(c.getFirstName() + " " + c.getLastName(), c));
		}
		repaint();
	}

}
