package edu.hist.team3.catering.gui.panel;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.hist.team3.catering.database.Customer;

public class CustomerPanel extends JPanel {
	private JTextField firstName;
	private JTextField lastName;
	private JTextField phoneNumber;
	private JTextField address;
	
	public CustomerPanel() {
		setLayout(new GridLayout(4, 1));
		firstName = new JTextField();
		lastName = new JTextField();
		phoneNumber = new JTextField();
		address = new JTextField();
		add(new PropertyPanel<JTextField>("First name:", 50, firstName));
		add(new PropertyPanel<JTextField>("Last name:", 50, lastName));
		add(new PropertyPanel<JTextField>("Phone number:", 50, phoneNumber));
		add(new PropertyPanel<JTextField>("Address:", 50, address));
	}
	
	public void fillInfo(Customer customer) {
		firstName.setText(customer.getFirstName());
		lastName.setText(customer.getLastName());
		phoneNumber.setText(customer.getPhone());
		address.setText(customer.getAddress());
	}
	
	public void apply(Customer customer) {
		customer.setFirstName(firstName.getText());
		customer.setLastName(lastName.getText());
		customer.setPhone(phoneNumber.getText());
		customer.setAddress(address.getText());
	}
	
	public JTextField getFirstName() {
		return firstName;
	}

	public JTextField getLastName() {
		return lastName;
	}

	public JTextField getPhoneNumber() {
		return phoneNumber;
	}

	public JTextField getAddress() {
		return address;
	}
}
