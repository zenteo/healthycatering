package edu.hist.team3.catering.database.manager;


import java.sql.SQLException;

import edu.hist.team3.catering.database.DatabaseManager;
import edu.hist.team3.catering.database.Job;

public class JobManager {
	private DatabaseManager manager;

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
	
	public Job getJob(int id) {
		return manager.getJob(id);
	}

	public void removeJob(int id)throws SQLException{
		manager.getJob(id).remove();
	}
}
