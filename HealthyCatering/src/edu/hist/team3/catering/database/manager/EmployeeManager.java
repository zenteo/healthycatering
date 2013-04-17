package edu.hist.team3.catering.database.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.DatabaseManager;
import edu.hist.team3.catering.database.Employee;
import edu.hist.team3.catering.database.Job;

public class EmployeeManager {
	private DatabaseManager manager;
	
	public EmployeeManager(DatabaseManager manager) {
		this.manager = manager;
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
	
	public Employee addEmployee(Job job)throws SQLException{
		return manager.createEmployee(job);
	}
	
	public Employee addEmployee(Customer base, Job job)throws SQLException{
		return manager.createEmployee(base, job);
	}
	
	public void removeEmployee(int id)throws SQLException{
		getEmployee(id).remove();
	}
	
	public ArrayList<Employee> findEmployee(String searchText) {
		searchText = searchText.toLowerCase();
		ArrayList<Employee> employees = new ArrayList<Employee>();
		try (ResultSet result = manager.performSQL("SELECT id FROM Employee, Customer WHERE Employee.customer_id = Customer.id AND LOWER(first_name || ' ' || last_name) LIKE '%" + searchText + "%'")) {
			while (result.next()) {
				employees.add(manager.getEmployee(result.getInt(1)));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return employees;
	}
}
