package edu.hist.team3.catering.database.managers;


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
	 * Get the Job with the parameter id.
	 * 
	 * @param id
	 * @return
	 */
	public Job getJob(int id) {
		return manager.getJob(id);
	}
}
