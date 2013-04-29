package edu.hist.team3.catering.database.manager;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.DatabaseManager;

public class CustomerManager {
	private DatabaseManager manager;
	
	/**
	 * Creates a new CustomerManager with DatabaseManager as parameter
	 * @param DatabaseManager
	 */
	public CustomerManager(DatabaseManager manager) {
		this.manager = manager;
	}
	
	/**
	 * Returns all customers in database as a list.
	 * @return
	 */
	public Customer[] getCustomers() {
		ArrayList<Customer> customers = new ArrayList<Customer>();
		try (ResultSet result = manager.performSql("SELECT id FROM Customer")) {
			while (result.next()) {
				customers.add(manager.getCustomer(result.getInt(1)));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		Customer[] ret = new Customer[customers.size()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = customers.get(i);
		}
		return ret;
	}
	
	/**
	 * Returns an ArrayList of customers which contains the selected search text
	 * @param searchText
	 * @return ArrayList<Customer>
	 */
	public ArrayList<Customer> findCustomer(String searchText) {
		searchText = searchText.toLowerCase();
		ArrayList<Customer> customers = new ArrayList<Customer>();
		try (ResultSet result = manager.performSql("SELECT id FROM Customer WHERE LOWER(first_name || ' ' || last_name) LIKE '%" + searchText + "%'")) {
			while (result.next()) {
				customers.add(manager.getCustomer(result.getInt(1)));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}
	
	/**
	 * Returns customer with the parameters id
	 * @param id
	 * @return Customer
	 */
	public Customer getCustomer(int id) {
		return manager.getCustomer(id);
	}
	
	/**
	 * Returns a newly created blank customer.
	 * @return
	 */
	public Customer createCustomer() {
		try {
			return manager.createCustomer();			
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "The manager was unable to create a new customer");
		}
		return null;
	}
	
	/**
	 * The Customer with the selected id is given new data with the parameters
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param phone
	 * @param address
	 * @return
	 */
	public boolean editCustomer(int id, String firstName, String lastName, String phone, String address) {
		Customer customer = getCustomer(id);
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setPhone(phone);
		customer.setAddress(address);
		try {
			customer.commit();
			return true;
		} catch (SQLException e) {
			
		}
		return false;
		
	}
	
	/**
	 * Creates a new customer and adds it to the database with data from parameters.
	 * @param firstName
	 * @param lastName
	 * @param phone
	 * @param address
	 * @return
	 */
	public boolean addCustomer(String firstName, String lastName, String phone, String address) {
		Customer customer = createCustomer();
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setPhone(phone);
		customer.setAddress(address);
		Calendar cal = Calendar.getInstance();
		customer.setCreationDate(Date.valueOf(cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DAY_OF_MONTH)));
		try {
			customer.commit();
			return true;
		} catch (SQLException e) {
		}
		return false;
	}
	
}
