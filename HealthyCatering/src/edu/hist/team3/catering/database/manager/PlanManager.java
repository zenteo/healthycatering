package edu.hist.team3.catering.database.manager;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Iterator;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.DatabaseManager;
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
	
	public Plan editPlan(Plan plan) {
		Plan ret = createPlan(plan.getCustomer());
		if (ret != null) {
			ret.setDaysOfWeek(plan.getDaysOfWeek());
			Iterator<PlanDish> it = plan.getDishes().iterator();
			try {
				while (it.hasNext()) {
					PlanDish pd = it.next();
					PlanDish npd;
					npd = ret.getDishes().add(pd.getDish());
					npd.setCount(pd.getCount());
					npd.setDiscount(pd.getDiscount());
					npd.commit();
				}
			}
			catch (SQLException e) {
				try {
					ret.remove();
					ret = null;
				}
				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		removePlan(plan);
		return ret;
	}
	
	public void removePlan(Plan plan) {
		Calendar calendar = Calendar.getInstance();
		plan.setEndDate(Date.valueOf(calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH)));
		try {
			plan.commit();
			plan.getCustomer().getPlans().fetch();
		}
		catch (SQLException e) {
			Services.showError("Could not either save the changes or fetch info from the database.");
			e.printStackTrace();
		}
	}
	
	public Plan createPlan(Customer customer) {
		try {
			return customer.getPlans().add();
		}
		catch (SQLException e) {
		}
		return null;
	}
}
