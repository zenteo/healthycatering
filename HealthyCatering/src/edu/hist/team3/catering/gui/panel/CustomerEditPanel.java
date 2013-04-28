package edu.hist.team3.catering.gui.panel;

import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.hist.team3.catering.database.Customer;

@SuppressWarnings("serial")
public class CustomerEditPanel extends JPanel {
	private JTextField firstName;
	private JTextField lastName;
	private JTextField phoneNumber;
	private JTextField address;
	private JLabel created;
	
	public CustomerEditPanel() {
		setLayout(new GridLayout(5, 2));
		this.firstName = new JTextField();
		this.lastName = new JTextField();
		this.phoneNumber = new JTextField();
		this.address = new JTextField();
		this.created = new JLabel();
		add(new JLabel("First name:"));
		add(firstName);
		add(new JLabel("Last name:"));
		add(lastName);
		add(new JLabel("Phone number:"));
		add(phoneNumber);
		add(new JLabel("Address:"));
		add(address);
		add(new JLabel("Created:"));
		add(created);
	}
	
	public void fillInfo(Customer customer) {
		firstName.setText(customer.getFirstName());
		lastName.setText(customer.getLastName());
		phoneNumber.setText(customer.getPhone());
		address.setText(customer.getAddress());
		created.setText(customer.getCreationDate().toString());
	}
	
	public void apply(Customer customer) throws SQLException {
		customer.setFirstName(firstName.getText());
		customer.setLastName(lastName.getText());
		customer.setPhone(phoneNumber.getText());
		customer.setAddress(address.getText());
		customer.commit();
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
