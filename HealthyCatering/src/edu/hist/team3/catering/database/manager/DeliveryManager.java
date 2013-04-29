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
	
	/**
	 * Creates a new DeliveryManager with DatabaseManager and Services as parameters.
	 * @param manager
	 * @param services
	 */
	public DeliveryManager(DatabaseManager manager, Services services) {
		this.manager = manager;
		this.services = services;
	}

	/**
	 * Returns a new delivery, with a plan and date as parameter
	 * @param plan
	 * @param date
	 * @return
	 */
	public Delivery createDelivery(Plan plan, Date date) {
		// TODO: Check if it can be delivered on this day of week.
		if (getDelivery(plan, date) != null)
			return null;
		try {
			Delivery ret = manager.createDelivery(plan);
			ret.setStatus(Delivery.STATUS_NONE);
			ret.setDate(date);
			ret.commit();
			return ret;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Returns an ArrayList of deliveries with date as parameter
	 * @param date
	 * @return
	 */
	public ArrayList<Delivery> createDeliveries(Date date) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		int dayOfWeek = now.get(Calendar.DAY_OF_WEEK);
		ArrayList<Delivery> ret = new ArrayList<Delivery>();
		ArrayList<Plan> plans = services.getPlanManager().getPlans(date);
		for (Plan p : plans) {
			boolean willDeliver = dayOfWeek == Calendar.SUNDAY && p.isDeliveredOn(Plan.DAY_SUNDAY)
					|| dayOfWeek == Calendar.MONDAY && p.isDeliveredOn(Plan.DAY_MONDAY)
					|| dayOfWeek == Calendar.TUESDAY && p.isDeliveredOn(Plan.DAY_TUESDAY)
					|| dayOfWeek == Calendar.WEDNESDAY && p.isDeliveredOn(Plan.DAY_WEDNESDAY)
					|| dayOfWeek == Calendar.THURSDAY && p.isDeliveredOn(Plan.DAY_THURSDAY)
					|| dayOfWeek == Calendar.FRIDAY && p.isDeliveredOn(Plan.DAY_FRIDAY)
					|| dayOfWeek == Calendar.SUNDAY && p.isDeliveredOn(Plan.DAY_SUNDAY);
					
			if (willDeliver) {
				Delivery delivery = createDelivery(p, date);
				if (delivery != null) {
					ret.add(delivery);
				}
			}
		}
		return ret;
	}
	
	/**
	 * Returns a delivery with selected date and plan
	 * @param plan
	 * @param date
	 * @return
	 */
	public Delivery getDelivery(Plan plan, Date date) {
		try (ResultSet result = manager
				.performSql("SELECT id FROM Delivery WHERE plan_id = "
						+ plan.getId() + " AND date = '" + date.toString()
						+ "'")) {
			while (result.next()) {
				return manager.getDelivery(result.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns an ArrayList of deliveries with date as parameter.
	 * @param date
	 * @return
	 */
	public ArrayList<Delivery> getDeliveries(Date date) {
		ArrayList<Delivery> ret = new ArrayList<Delivery>();
		try (ResultSet result = manager
				.performSql("SELECT id FROM Delivery WHERE date = '"
						+ date.toString() + "'")) {
			while (result.next()) {
				ret.add(manager.getDelivery(result.getInt(1)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * Get total outcome of all deliveries.
	 * @return double
	 */
	public double getTotalOutcome() {
		try (ResultSet result = manager
				.performSql("SELECT SUM(DELIVERY.SUM_OUTCOME) AS total_outcome FROM DELIVERY")) {
			if (result.next()) {
				return result.getDouble(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1.0;
	}

	/**
	 * Get total income of all deliveries.
	 * @return double
	 */
	public double getTotalIncome() {
		try (ResultSet result = manager.performSql("SELECT SUM(DELIVERY.SUM_INCOME) AS total_income FROM DELIVERY")) {
			if (result.next()) {
				return result.getDouble(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1.0;
	}
	
	/**
	 * Get profit between selected dates.
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public double getProfitOver(String startDate, String endDate) {
		try (ResultSet result = manager.performSql(
				"SELECT SUM(DELIVERY.SUM_INCOME) AS total_income, SUM(DELIVERY.SUM_OUTCOME) AS total_outcome FROM DELIVERY WHERE DATE BETWEEN '" + startDate + "' AND '" + endDate + "'")) {
			if(result.next()) {
				return (result.getDouble(1) - result.getDouble(2));
			} 
		} catch (SQLException e) {
				e.printStackTrace();
		}
		return -1.0;
	}

}
