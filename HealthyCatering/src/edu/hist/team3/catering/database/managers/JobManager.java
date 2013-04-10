package edu.hist.team3.catering.database.managers;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.DatabaseManager;
import edu.hist.team3.catering.database.Job;

public class JobManager {

	DatabaseManager manager;
	private static JobManager jManager;

	/**
	 * Returns the instance of the job manager
	 * 
	 * @return
	 */
	public static JobManager getInstance() {
		if (jManager == null)
			jManager = new JobManager();
		return jManager;
	}

	private JobManager() {
		manager = DatabaseManager.getInstance();
	}

	/**
	 * Return (if found) an Job with inputted username and password
	 * 
	 * @param username
	 * @param password
	 * @return Job
	 */
	public Job JobManager(String username, String password) {
		ResultSet result = manager
				.performSQL("SELECT customer_id FROM Job, Customer WHERE username ='"
						+ username + "' AND password ='" + password + "'");

		try {
			if (result.next()) {
				return new Job(manager, new Customer(manager, result.getInt(1)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Get the Job with the parameter id.
	 * 
	 * @param id
	 * @return
	 */
	public Job getJob(int id) {
		return manager.getJob(id);
	}
}
