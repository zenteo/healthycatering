package edu.hist.team3.catering.database.managers;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.DatabaseManager;

public class CustomerManager {
	private DatabaseManager manager;
	private static CustomerManager cManager;
	
	private CustomerManager() {
		manager = DatabaseManager.getInstance();
	}
	
	public static CustomerManager getInstance() {
		if (cManager == null) {
			cManager = new CustomerManager();
		}
		return cManager;
	}
	
	/**
	 * Returns all customers in database as a list.
	 * @return
	 */
	public Customer[] getCustomers() {
		int numOfRows = -1;
		ResultSet result = manager.performSQL("SELECT count(*) FROM Customer");
		
		try {
			if(result.next())	
				numOfRows = result.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Customer[] customerList = new Customer[numOfRows];
		result = manager.performSQL("SELECT id FROM Customer");
		
		for (int i=0; i<numOfRows; i++) {
			try {
				result.next();
				manager.getCustomer(result.getInt(1)).fetch();
				customerList[i] = manager.getCustomer(result.getInt(1));
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return customerList;
	}
	
	public Customer getCustomer(int id) {
		return manager.getCustomer(id);
	}
}
