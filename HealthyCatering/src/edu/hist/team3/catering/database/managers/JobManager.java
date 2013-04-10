package edu.hist.team3.catering.database.managers;


import java.sql.SQLException;

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
	public void addJob(){
		
	}
	public Job getJob(int id) {
		return manager.getJob(id);
	}
	public void editJob(int id, String name, double hourlySalary){
		manager.getJob(id).setName(name);
		manager.getJob(id).setHourlySalary(hourlySalary);
		
	}
	public void removeJob(int id)throws SQLException{
		manager.getJob(id).remove();
	}
}
