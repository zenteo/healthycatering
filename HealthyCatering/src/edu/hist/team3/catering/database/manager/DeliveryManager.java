package edu.hist.team3.catering.database.manager;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import edu.hist.team3.catering.database.DatabaseManager;
import edu.hist.team3.catering.database.Delivery;
import edu.hist.team3.catering.database.Plan;

public class DeliveryManager {
	private DatabaseManager manager;
	private Services services;

	public DeliveryManager(DatabaseManager manager, Services services) {
		this.manager = manager;
		this.services = services;
	}
	
	public Delivery createDelivery(Plan plan, Date date) {
		//TODO: Check if it can be delivered on this day of week.
		if (getDelivery(plan, date) != null)
			return null;
		try {
			Delivery ret = manager.createDelivery(plan);
			ret.setStatus(Delivery.STATUS_NONE);
			ret.setDate(date);
			ret.commit();
			return ret;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<Delivery> createDeliveries(Date date) {
		ArrayList<Delivery> ret = new ArrayList<Delivery>();
		ArrayList<Plan> plans = services.getPlanManager().getPlans(date);
		for (Plan p : plans) {
			Delivery delivery = createDelivery(p, date);
			if (delivery != null) {
				ret.add(delivery);
			}
		}
		return ret;
	}

	public Delivery getDelivery(Plan plan, Date date) {
		try (ResultSet result = manager.performSql("SELECT id FROM Delivery WHERE plan_id = " + plan.getId() + " AND date = '" + date.toString() + "'")) {
			while (result.next()) {
				return manager.getDelivery(result.getInt(1));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<Delivery> getDeliveries(Date date) {
		ArrayList<Delivery> ret = new ArrayList<Delivery>();
		try (ResultSet result = manager.performSql("SELECT id FROM Delivery WHERE date = '" + date.toString() + "'")) {
			while (result.next()) {
				ret.add(manager.getDelivery(result.getInt(1)));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public double getTotalOutcome() {
		try (ResultSet result = manager.performSql("SELECT SUM(DELIVERY.SUM_OUTCOME) AS total_outcome FROM DELIVERY")){
			if (result.next()) {
				return result.getDouble(1);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return -1.0;
	}

	public double getTotalIncome() {
		try (ResultSet result = manager.performSql("SELECT SUM(DELIVERY.SUM_INCOME) AS total_income FROM DELIVERY")){
			if (result.next()) {
				return result.getDouble(1);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return -1.0;
	}

}
