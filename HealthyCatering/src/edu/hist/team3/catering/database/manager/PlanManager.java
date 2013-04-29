package edu.hist.team3.catering.database.manager;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.DatabaseManager;
import edu.hist.team3.catering.database.Plan;

public class PlanManager {
	private DatabaseManager manager;

	/**
	 * Creates a new PlanManager.
	 * @param manager
	 */
	public PlanManager(DatabaseManager manager) {
		this.manager = manager;
	}
	
	/**
	 * Returns a plan with selected id.
	 * @param planId
	 * @return
	 */
	public Plan getPlan(int planId) {
		return manager.getPlan(planId);
	}

	/**
	 * Returns an ArrayList of plans with selected date.
	 * @param date
	 * @return
	 */
	public ArrayList<Plan> getPlans(Date date) {
		ArrayList<Plan> ret = new ArrayList<Plan>();
		try (ResultSet result = manager
				.performSql("SELECT id FROM Plan WHERE start_date <= '"
						+ date.toString() + "' AND (end_date > '"
						+ date.toString() + "' OR end_date IS null)")) {
			while (result.next()) {
				ret.add(manager.getPlan(result.getInt(1)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * Removes a plan with selected plan
	 * @param plan
	 */
	public void removePlan(Plan plan) {
		Calendar calendar = Calendar.getInstance();
		plan.setEndDate(Date.valueOf(calendar.get(Calendar.YEAR) + "-"
				+ (calendar.get(Calendar.MONTH) + 1) + "-"
				+ calendar.get(Calendar.DAY_OF_MONTH)));
		try {
			plan.commit();
			plan.getCustomer().getPlans().fetch();
		} catch (SQLException e) {
			Services.showError("Could not either save the changes or fetch info from the database.");
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new plan with selected Customer.
	 * @param customer
	 * @return
	 */
	public Plan createPlan(Customer customer) {
		try {
			return customer.getPlans().add();
		} catch (SQLException e) {
		}
		return null;
	}
}
