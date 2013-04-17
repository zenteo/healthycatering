package edu.hist.team3.catering.gui.frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.manager.Services;
import edu.hist.team3.catering.gui.panel.CustomerEditPanel;

public class CustomerEditFrame extends JFrame implements ActionListener {
	private Services services;
	private CustomerEditPanel customerPanel;
	private JButton button;
	private Customer customer = null;

	public CustomerEditFrame(Services services) {
		super("Add customer");
		this.services = services;
		init("Create");
	}

	public CustomerEditFrame(Customer customer) {
		super("Edit customer");
		this.customer = customer;
		init("Save");
		customerPanel.fillInfo(customer);
	}

	private void init(String buttonText) {
		this.setLayout(new BorderLayout());
		customerPanel = new CustomerEditPanel();
		button = new JButton(buttonText);
		button.addActionListener(this);
		add(customerPanel, BorderLayout.CENTER);
		add(button, BorderLayout.SOUTH);
		pack();
		setSize(300, 200);
		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (customer == null) {
			customer = services.getCustomerManager().createCustomer();
			if (customer == null) {
				JOptionPane.showMessageDialog(null,
						"Error: Could not create customer!");
				return;
			}
		}
		customerPanel.apply(customer);
		try {
			customer.commit();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Error: Could not change customer!");
		}
		this.setVisible(false);
	}
}