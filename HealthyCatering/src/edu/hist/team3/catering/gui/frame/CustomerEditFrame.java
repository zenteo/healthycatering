package edu.hist.team3.catering.gui.frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.manager.Services;
import edu.hist.team3.catering.gui.panel.CustomerEditPanel;

@SuppressWarnings("serial")
public class CustomerEditFrame extends JFrame implements ActionListener {
	private Services services;
	private CustomerEditPanel customerPanel;
	private JButton button;
	private Customer customer = null;
	/**
	 * Creates a new instance of CustomerEditFrame based on Services
	 * @param services
	 */
	public CustomerEditFrame(Services services) {
		super("Add customer");
		this.services = services;
		init("Create");
	}
	/**
	 * Creates a new instance of CustomerEditFrame based on Customer
	 * @param customer
	 */
	public CustomerEditFrame(Customer customer) {
		super("Edit customer");
		this.customer = customer;
		init("Save");
		customerPanel.fillInfo(customer);
	}
	/**
	 * Initializes the Customer Editor
	 * @param buttonText
	 */
	private void init(String buttonText) {
		this.setLayout(new BorderLayout());
		customerPanel = new CustomerEditPanel();
		button = new JButton(buttonText);
		button.addActionListener(this);
		add(customerPanel, BorderLayout.CENTER);
		add(button, BorderLayout.SOUTH);
		pack();
		setTitle("Customer editor");
		setSize(300, 200);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.setVisible(false);
		if (customer == null) {
			customer = services.getCustomerManager().createCustomer();
			if (customer == null) {
				Services.showError("Error: Could not create customer!");
				return;
			}
		}
		try {
			customerPanel.apply(customer);
		}
		catch (SQLException e1) {
			e1.printStackTrace();
			Services.showError("Error: Could not change customer!");
		}
	}
}