package edu.hist.team3.catering;

import java.sql.Date;
import java.sql.SQLException;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.DatabaseManager;
import edu.hist.team3.catering.database.Employee;
import edu.hist.team3.catering.database.Job;

public class CreateDefaultDatabase {
	
	/**
	 * This is just a test, and not to be used.
	 */
	public CreateDefaultDatabase(){
		
	}
	
	/**
	 * @param args This does nothing
	 */
	public static void main(String[] args) {
		try {
			DatabaseManager manager = null;
			
			manager = new DatabaseManager("jdbc:derby://db.stud.aitel.hist.no:1527/13ing1gr3", "team3", "Ikj721");
			
			System.out.println("Create default data:");
			
			Customer customer = manager.createCustomer();
			customer.setFirstName("Admin");
			customer.setLastName("Administrator");
			customer.setPhone("+4712345678");
			customer.setAddress("Klaebuveien 125, 7034 Trondheim");
			customer.commit();

			Job job = manager.createJob();
			job.setName("Administrator");
			job.setHourlySalary(400.0); // 400 NOK / hour
			job.setYearlySalary(660000.0); // 660 000 NOK / year
			job.setPercentSales(1.0); // 100% of all sales
			job.setPrivileges(Job.PRIVILEGE_ALL);
			job.commit();
			
			Employee employee = manager.createEmployee(customer, job);
			employee.setUsername("admin");
			employee.setPassword("admin");
			employee.setEmploymentDate(Date.valueOf("2013-01-23")); // Also test-date
			employee.setSessionHours(0.0); // Worked for 0.0 hour in his whole life.
			employee.commit();
			
			System.out.println("Done.");
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
