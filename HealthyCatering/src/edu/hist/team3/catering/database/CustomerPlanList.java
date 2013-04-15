package edu.hist.team3.catering.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;

public class CustomerPlanList {
	private HashSet<Plan> plans = null;
	private final Customer customer;
	
	public CustomerPlanList(Customer customer) {
		assert(customer != null);
		this.customer = customer;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public void add(Plan plan) throws SQLException {
		assert(plan != null);
		plan.setCustomer(customer);
		plan.commit();
		plans.add(plan);
	}
	
	public void remove(Plan plan) throws SQLException {
		assert(plan != null);
		plan.remove();
		plans.remove(plan);
	}
	
	public void removeAll() throws SQLException {
		Iterator<Plan> it = iterator();
		while (it.hasNext()) {
			it.next().remove();
		}
		plans.clear();
	}
	
	public boolean contains(Plan plan) {
		tryFetch();
		return plans.contains(plan);
	}
	
	public Iterator<Plan> iterator() {
		tryFetch();
		return plans.iterator();
	}
	
	public int size() {
		tryFetch();
		return plans.size();
	}
	
	protected void tryFetch() {
		if (plans == null) {
			try {
				fetch();
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void fetch() throws SQLException {
		String sql = "SELECT id FROM Plan WHERE customer_id = " + customer.getId();
		plans.clear();
		try (PreparedStatement ps = customer.getManager().prepareStatement(sql, "id")) {
			ps.executeUpdate();
			try (ResultSet rs = ps.getGeneratedKeys()) {
				while (rs.next()) {
					plans.add(customer.getManager().getPlan(rs.getInt(1)));
				}
			}
		}
	}
	
	@Override
	public String toString() {
		tryFetch();
		String ret = "CustomerPlanList[";
		Iterator<Plan> it = iterator();
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
