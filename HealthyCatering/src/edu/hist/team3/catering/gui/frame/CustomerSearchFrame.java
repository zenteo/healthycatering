package edu.hist.team3.catering.gui.frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.manager.Services;
import edu.hist.team3.catering.gui.panel.CustomerSearch;

@SuppressWarnings("serial")
public class CustomerSearchFrame extends JFrame {
	private CustomerSearch customerSearch;
	private Customer selected;
	/**
	 * Creates a new instance of CustomerSearchFrame based on services
	 * @param services
	 */
	public CustomerSearchFrame(Services services) {

		this.customerSearch = new CustomerSearch(services);
		setLayout(new BorderLayout());
		add(customerSearch, BorderLayout.CENTER);
		JButton select = new JButton("Select");
		select.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setSelected(customerSearch.getSelected());
				setVisible(false);
			}
		});
		add(select, BorderLayout.SOUTH);
		setAlwaysOnTop(true);
		setTitle("Select customer");
		setSize(300, 200);
	}
	/**
	 * Gives selected Customer
	 * @return customer
	 */
	public Customer getSelected() {
		return selected;
	}
	/**
	 * Sets selected Customer
	 * @param selected
	 */
	public void setSelected(Customer selected) {
		this.selected = selected;
	}
}
