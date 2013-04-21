package edu.hist.team3.catering.database.manager;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.DatabaseManager;
import edu.hist.team3.catering.database.Delivery;
import edu.hist.team3.catering.database.Plan;
import edu.hist.team3.catering.database.PlanDish;

public class PlanManager {
	private DatabaseManager manager;

	public PlanManager(DatabaseManager manager) {
		this.manager = manager;
	}

	public Plan getPlan(int planId) {
		return manager.getPlan(planId);
	}

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

	public Plan createPlan(Customer customer) {
		try {
			return customer.getPlans().add();
		} catch (SQLException e) {
		}
		return null;
	}
}
