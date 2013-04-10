package edu.hist.team3.catering.database.managers;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.DatabaseManager;
import edu.hist.team3.catering.database.Employee;

public class EmployeeManager {
	DatabaseManager manager;
	private static EmployeeManager eManager;
	
	/**
	 * Returns the instance of the employee manager
	 * @return
	 */
	public static EmployeeManager getInstance() {
		if(eManager == null)
			eManager = new EmployeeManager();
		return eManager;
	}
	
	private EmployeeManager() {
		manager = DatabaseManager.getInstance();
	}
	
	/**
	 * Return (if found) an employee with inputted username and password
	 * @param username
	 * @param password
	 * @return employee
	 */
	public Employee getEmployee(String username, String password) {
		ResultSet result = manager.performSQL("SELECT customer_id FROM Employee WHERE username ='" + username + "' AND password ='" + password + "'");
		
		try {
			if (result.next()) {
				return new Employee(manager, new Customer(manager, result.getInt(1)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Get the employee with the parameter id.
	 * @param id
	 * @return
	 */
	public Employee getEmployee(int id) {
		return manager.getEmployee(id);
	}
}
