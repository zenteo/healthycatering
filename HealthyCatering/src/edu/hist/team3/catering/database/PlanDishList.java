package edu.hist.team3.catering.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;

public class PlanDishList {
	private HashSet<PlanDish> links = null;
	private final Plan plan;
	/**
	 * Creates a new instance of PlanDishList
	 * @param plan
	 */
	public PlanDishList(Plan plan) {
		assert(plan != null);
		this.plan = plan;
	}
	/**
	 * Gives the plan
	 * @return Plan
	 */
	public Plan getPlan() {
		return plan;
	}
	/**
	 * Adds a dish to plan
	 * @param dish
	 * @return PlanDish
	 * @throws SQLException
	 */
	public PlanDish add(Dish dish) throws SQLException {
		assert(dish != null);
		PlanDish link = plan.getManager().createPlanDish(plan, dish);
		if (links != null && link != null) {
			links.add(link);
		}
		return link;
	}
	/**
	 * Gives a plandish based on dish
	 * @param dish
	 * @return plandish
	 */
	public PlanDish get(Dish dish) {
		assert(dish != null);
		tryFetch();
		Iterator<PlanDish> it = iterator();
		while (it.hasNext()) {
			PlanDish ret = it.next();
			if (ret.getDish().equals(dish)) {
				return ret;
			}
				
		}
		return null;
	}
	/**
	 * Removes a dish
	 * @param dish
	 * @throws SQLException
	 */
	public void remove(Dish dish) throws SQLException {
		assert(dish != null);
		PlanDish link = get(dish);
		if (link != null) {
			link.remove();
			if (links != null) {
				links.remove(link);
			}
		}
	}
	/**
	 * Removes a plandish
	 * @param planDish
	 * @throws SQLException
	 */
	public void remove(PlanDish planDish) throws SQLException {
		assert(planDish != null);
		planDish.remove();
		if (links != null) {
			links.remove(planDish);
		}
	}
	/**
	 * Removes all 
	 * @throws SQLException
	 */
	public void removeAll() throws SQLException {
		Iterator<PlanDish> it = iterator();
		while (it.hasNext()) {
			it.next().remove();
		}
		links.clear();
	}
	/**
	 * Checks if plandish contains dish
	 * @param dish
	 * @return yes/no
	 */
	public boolean contains(Dish dish) {
		tryFetch();
		return links.contains(plan.getManager().getPlanDish(plan, dish));
	}
	
	public Iterator<PlanDish> iterator() {
		tryFetch();
		return links.iterator();
	}
	
	public int size() {
		tryFetch();
		return links.size();
	}
	
	protected void tryFetch() {
		if (links == null) {
			try {
				fetch();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void fetch() throws SQLException {
		String sql = "SELECT dish_id FROM Plan_Dish WHERE plan_id = " + plan.getId();
		try (PreparedStatement ps = plan.getManager().prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				links = new HashSet<PlanDish>();
				while (rs.next()) {
					Dish dish = plan.getManager().getDish(rs.getInt(1));
					PlanDish planDish = plan.getManager().getPlanDish(plan, dish);
					links.add(planDish);
				}
			}
		}
	}
	
	@Override
	public String toString() {
		tryFetch();
		String ret = "PlanDishList[";
		Iterator<PlanDish> it = iterator();
		while (it.hasNext()) {
			ret += it.next().toString();
			if (it.hasNext()) {
				ret += "; ";
			}
		}
		ret += "]";
		return ret;
	
	}
}
