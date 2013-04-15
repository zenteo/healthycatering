package edu.hist.team3.catering.database.managers;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Iterator;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.DatabaseManager;
import edu.hist.team3.catering.database.Plan;

public class PlanManager {
	private DatabaseManager manager;
	
	public PlanManager(DatabaseManager manager) {
		this.manager = manager;
	}
	
	public Plan getPlan(int planId) {
		return manager.getPlan(planId);
	}
	
	public Plan[] getPlansFor(int customerId) {
		return getPlansFor(manager.getCustomer(customerId));
	}
	
	public Plan[] getPlansFor(Customer customer) {
		Plan[] planList = new Plan[customer.getPlans().size()];
		Iterator<Plan> it = customer.getPlans().iterator();
		int i = 0;
		while (it.hasNext()) {
			planList[i++] = it.next();
		}
		return planList;
	}
	
	public boolean createPlan(int customerId, int deliverOnDays, String startDate, String endDate, double sumIncome, double sumOutcome) {
		try {
			Plan plan = manager.createPlan(manager.getCustomer(customerId));
			plan.setDaysOfWeek(deliverOnDays);
			plan.setDeliveredOn(deliverOnDays);
			plan.setStartDate(Date.valueOf(startDate));
			plan.setEndDate(Date.valueOf(endDate));
			plan.setSumIncome(sumIncome);
			plan.setSumOutcome(sumOutcome);
			plan.commit();
			return true;
		} catch (SQLException e) {
		}
		return false;
	}
}
