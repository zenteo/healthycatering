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
	/**
	 * Creates a new instance of CustomerEditPanel
	 */
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
	/**
	 * Fills in info from customer
	 * @param customer
	 */
	public void fillInfo(Customer customer) {
		firstName.setText(customer.getFirstName());
		lastName.setText(customer.getLastName());
		phoneNumber.setText(customer.getPhone());
		address.setText(customer.getAddress());
		created.setText(customer.getCreationDate().toString());
	}
	/**
	 * Commits to the database
	 * @param customer
	 * @throws SQLException
	 */
	public void apply(Customer customer) throws SQLException {
		customer.setFirstName(firstName.getText());
		customer.setLastName(lastName.getText());
		customer.setPhone(phoneNumber.getText());
		customer.setAddress(address.getText());
		customer.commit();
	}
	/**
	 * Gives firstname from textfield
	 * @return firstname
	 */
	public JTextField getFirstName() {
		return firstName;
	}
	/**
	 * Gives Lastname from textfield
	 * @return Lastname
	 */
	public JTextField getLastName() {
		return lastName;
	}
	/**
	 * Gives phonenumber from textfield
	 * @return phonenumber
	 */
	public JTextField getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * Gives the address from textfield
	 * @return address
	 */
	public JTextField getAddress() {
		return address;
	}
}
