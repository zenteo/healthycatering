package edu.hist.team3.catering.database.manager;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.hist.team3.catering.database.DatabaseManager;
import edu.hist.team3.catering.database.Job;

public class JobManager {
	private DatabaseManager manager;

	/** 
	 * Creates a new JobManager with DatabaseManager as parameter.
	 * @param manager
	 */
	public JobManager(DatabaseManager manager) {
		this.manager = manager;
	}

	/**
	 * Get the Job with the parameter id.
	 * 
	 * @param id
	 * @return
	 */
	public Job addJob() throws SQLException{
		return manager.createJob();
	}
	
	/**
	 * Returns a job with selected id.
	 * @param id
	 * @return
	 */
	public Job getJob(int id) {
		return manager.getJob(id);
	}

	/**
	 * Removes a job with selected id.
	 * @param id
	 * @throws SQLException
	 */
	public void removeJob(int id)throws SQLException{
		manager.getJob(id).remove();
	}
	
	/**
	 * Returns an ArrayList with jobs where the jobs contain the search text.
	 * @param searchText
	 * @return
	 */
	public ArrayList<Job> findJob(String searchText) {
		searchText = searchText.toLowerCase();
		ArrayList<Job> jobs = new ArrayList<Job>();
		try (ResultSet result = manager.performSql("SELECT id FROM Job WHERE LOWER(name) LIKE '%" + searchText + "%'")) {
			while (result.next()) {
				jobs.add(manager.getJob(result.getInt(1)));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return jobs;
	}
}
