package edu.hist.team3.catering.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlanDish extends DatabaseRow {
	// Different columns of the DeliveryDish-table in the database:
	private final Plan plan;			// plan_id		INT		NOT NULL
	private final Dish dish;			// dish_id		INT		NOT NULL
	private int count;					// count		INT		NOT NULL
	private double discount;			// discount		DOUBLE	NOT NULL
	
	/**
	 * Creates a new instance of PlanDish
	 * @param manager	The DatabaseManager for a connection to a database
	 * @param plan		The Plan in the PlanDish-link
	 * @param dish		The Dish in the PlanDish-link
	 */
	protected PlanDish(DatabaseManager manager, Plan plan, Dish dish) {
		super(manager);
		assert(dish != null);
		assert(plan != null);
		this.plan = plan;
		this.dish = dish;
	}
	
	/**
	 * Create a new default row in the Plan_Dish-table.
	 * @param manager	The DatabaseManager for a connection to a database
	 * @param delivery	The Delivery in the PlanDish-link
	 * @param dish		The Dish in the PlanDish-link
	 * @return			A instance of a PlanDish
	 * @throws SQLException
	 */
	public static PlanDish createDefault(DatabaseManager manager, Plan plan, Dish dish) throws SQLException {
		String sql = "INSERT INTO Plan_Dish (plan_id, dish_id, count, discount) " +
				"VALUES (" + plan.getId() + ", " + dish.getId() + ", 1, " + dish.getDefaultDiscount() + ")";
		PlanDish ret = null;
		try (PreparedStatement ps = manager.prepareStatement(sql)) {
			ps.executeUpdate();
			ret = manager.getPlanDish(plan, dish);
		}
		return ret;
	}
	
	@Override
	public void fetch() throws SQLException {
		super.fetch();
		String sql = "SELECT count, discount FROM Plan_Dish WHERE plan_id = " + plan.getId() + " AND dish_id = " + dish.getId();
		try (PreparedStatement ps = getManager().prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					this.count = rs.getInt(1);
					this.discount = rs.getDouble(2);
				}
			}
		}
	}
	
	@Override
	public void commit() throws SQLException {
		super.commit();
		String sql = "UPDATE Plan_Dish SET ";
		sql += "count = " + count;
		sql += ", discount = " + discount;
		sql += " WHERE plan_id = " + plan.getId() + " AND dish_id = " + dish.getId();
		try (PreparedStatement ps = getManager().prepareStatement(sql)) {
			ps.executeUpdate();
		}
	}
	
	@Override
	public void remove() throws SQLException {
		super.remove();
		String sql = "DELETE FROM Plan_Dish WHERE plan_id = " + plan.getId() + " AND dish_id = " + dish.getId();
		try (PreparedStatement ps = getManager().prepareStatement(sql)) {
			ps.executeUpdate();
		}
	}
	
	/**
	 * Gets the plan.
	 * @return The plan
	 */
	public Plan getPlan() {
		return plan;
	}
	
	/**
	 * Gets the dish.
	 * @return The dish
	 */
	public Dish getDish() {
		return dish;
	}
	
	/**
	 * Gets the count.
	 * @return The count
	 */
	public int getCount() {
		super.tryFetch();
		return count;
	}
	
	/**
	 * Sets the count.
	 * @param count The new count
	 */
	public void setCount(int count) {
		super.setChanged();
		this.count = count;
	}
	
	/**
	 * Adds the count.
	 * @param deltaCount Change in count
	 */
	public void addCount(int deltaCount) {
		super.setChanged();
		this.count += deltaCount;
	}
	
	/**
	 * Gets the discount.
	 * @return The discount
	 */
	public double getDiscount() {
		super.tryFetch();
		return discount;
	}
	
	/**
	 * Sets the discount.
	 * @param discount The new discount
	 */
	public void setDiscount(double discount) {
		super.setChanged();
		this.discount = discount;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof PlanDish)) {
			return false;
		}
		PlanDish otherLink = (PlanDish)other;
		return plan.equals(otherLink.plan) && dish.equals(otherLink.dish);
	}
	
	@Override
	public String toString() {
		super.tryFetch();
		return "PlanDish[planId = " + plan.getId() + "; dishId = " + dish.getId() + "; count = " + count + "; discount = " + discount + "]";
	}
}
