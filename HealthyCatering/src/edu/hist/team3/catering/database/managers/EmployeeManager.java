package edu.hist.team3.catering.database.managers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.DatabaseManager;
import edu.hist.team3.catering.database.Employee;
import edu.hist.team3.catering.database.Job;

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
	public void addEmployee(Job job)throws SQLException{
		manager.createEmployee(job);
	}
	public void editEmployee(int id, Job job, String username, String password, String email, Date employmentdate, Double sessionhours, int userprivileges){
		manager.getEmployee(id).setJob(job);
		manager.getEmployee(id).setUsername(username);
		manager.getEmployee(id).setPassword(password);
		manager.getEmployee(id).setEmail(email);
		manager.getEmployee(id).setEmploymentDate(employmentdate);
		manager.getEmployee(id).setSessionHours(sessionhours);
		manager.getEmployee(id).setPrivileges(userprivileges);
	}
	public void removeEmployee(int id)throws SQLException{
		manager.getEmployee(id).remove();
		
	}
}
