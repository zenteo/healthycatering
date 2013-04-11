package edu.hist.team3.catering.database.managers;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.hist.team3.catering.database.DatabaseManager;
import edu.hist.team3.catering.database.Plan;

public class PlanManager {
	
	private DatabaseManager manager;
	private static PlanManager pManager;
	
	public static PlanManager getInstance() {
		if (pManager == null)
			pManager = new PlanManager();
		return pManager;
	}
	
	private PlanManager() {
		manager = DatabaseManager.getInstance();
	}
	

	public Plan[] getPlansFor(int customerId) {
		int numOfRows = -1;
		
		ResultSet result = manager.performSQL("SELECT Count(*) FROM Plan WHERE Plan.CUSTOMER_ID = " + customerId);
		
		try {
			if(result.next())	
				numOfRows = result.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Plan[] planList = new Plan[numOfRows];
		result = manager.performSQL("SELECT id FROM Plan WHERE Plan.CUSTOMER_ID = " + customerId);
		
		for (int i=0; i<numOfRows; i++) {
			try {
				result.next();
				manager.getPlan(result.getInt(1)).fetch();
				planList[i] = manager.getPlan(result.getInt(1));
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return planList;

	}
	
}
