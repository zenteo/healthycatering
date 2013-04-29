package edu.hist.team3.catering.gui.panel;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import edu.hist.team3.catering.database.Employee;
import edu.hist.team3.catering.database.manager.Services;

@SuppressWarnings("serial")
public class EmployeeSearch extends SearchPanel<Employee> {
	private Services services;
	/**
	 * Creates a new instance of employeesearch based on services 
	 * @param services
	 */
	public EmployeeSearch(Services services) {
		this.services = services;
		onSearch("");
	}
	
	@Override
	public void onSearch(String text) {
		ArrayList<Employee> ret = services.getEmployeeManager().findEmployee(text);
		
		DefaultListModel<LabeledObject<Employee>> model;
		model = (DefaultListModel<LabeledObject<Employee>>) getResultList().getModel();
		model.clear();
		
		for (Employee c : ret) {
			model.addElement(new LabeledObject<Employee>(c.getCustomer().getFirstName() + " " + c.getCustomer().getLastName(), c));
		}
		repaint();

	}
}
