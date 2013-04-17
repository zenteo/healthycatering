package edu.hist.team3.catering.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;

public class PlanDishList {
	private HashSet<PlanDish> links = null;
	private final Plan plan;
	
	public PlanDishList(Plan plan) {
		assert(plan != null);
		this.plan = plan;
	}
	
	public Plan getPlan() {
		return plan;
	}
	
	public PlanDish add(Dish dish) throws SQLException {
		assert(dish != null);
		PlanDish link = plan.getManager().createPlanDish(plan, dish);
		if (links != null && link != null) {
			links.add(link);
		}
		return link;
	}
	
	public PlanDish get(Dish dish) {
		assert(dish != null);
		return plan.getManager().getPlanDish(plan, dish);
	}
	
	public void remove(Dish dish) throws SQLException {
		assert(dish != null);
		PlanDish link = plan.getManager().getPlanDish(plan, dish);
		link.remove();
		if (links != null) {
			links.remove(link);
		}
	}
	
	public void remove(PlanDish planDish) throws SQLException {
		assert(planDish != null);
		planDish.remove();
		if (links != null) {
			links.remove(planDish);
		}
	}
	
	public void removeAll() throws SQLException {
		Iterator<PlanDish> it = iterator();
		while (it.hasNext()) {
			it.next().remove();
		}
		links.clear();
	}
	
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
