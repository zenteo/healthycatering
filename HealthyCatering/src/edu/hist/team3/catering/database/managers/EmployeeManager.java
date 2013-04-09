package edu.hist.team3.catering.database.managers;

import edu.hist.team3.catering.database.DatabaseManager;
import edu.hist.team3.catering.database.Employee;

public class EmployeeManager {
	DatabaseManager manager = DatabaseManager.getInstance();
	private static EmployeeManager eManager = new EmployeeManager();
	
	public EmployeeManager getInstance() {
		return eManager;
	}
	
	private EmployeeManager() {
		
	}
	
	public Employee getEmployee(String username, String password) {
		return manager.getEmployee(username, password);
	}
	
	public Employee getEmployee(int id) {
		return manager.getEmployee(id);
	}
}
