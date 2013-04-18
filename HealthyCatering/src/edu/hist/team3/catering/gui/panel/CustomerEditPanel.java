package edu.hist.team3.catering.gui.panel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.hist.team3.catering.database.Customer;

public class CustomerEditPanel extends JPanel {
	private JTextField firstName;
	private JTextField lastName;
	private JTextField phoneNumber;
	private JTextField address;
	
	public CustomerEditPanel() {
		setLayout(new GridLayout(4, 1));
		this.firstName = new JTextField();
		this.lastName = new JTextField();
		this.phoneNumber = new JTextField();
		this.address = new JTextField();
		add(new PropertyPanel<JTextField>("First name:", firstName));
		add(new PropertyPanel<JTextField>("Last name:", lastName));
		add(new PropertyPanel<JTextField>("Phone number:", phoneNumber));
		add(new PropertyPanel<JTextField>("Address:", address));
	}
	
	public void fillInfo(Customer customer) {
		firstName.setText(customer.getFirstName());
		lastName.setText(customer.getLastName());
		phoneNumber.setText(customer.getPhone());
		address.setText(customer.getAddress());
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
