package edu.hist.team3.catering.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DishResource extends DatabaseRow {
	// Different columns of the Dish_Resource-table in the database:
	private final Dish dish;
	private final Resource resource;
	private double amount;
	
	/**
	 * Creates a new instance of DishResource
	 * @param manager	The DatabaseManager for a connection to a database
	 * @param dish		The Dish in the DishResource-link
	 * @param resource	The Resource in the DishResource-link
	 */
	protected DishResource(DatabaseManager manager, Dish dish, Resource resource) {
		super(manager);
		assert(dish != null);
		assert(resource != null);
		this.dish = dish;
		this.resource = resource;
	}
	
	/**
	 * Create a new default row in the Dish_Resource-table.
	 * @param manager	The DatabaseManager for a connection to a database
	 * @param dish		The Dish in the DishResource-link
	 * @param resource	The Resource in the DishResource-link
	 * @return			A instance of a DishResource
	 * @throws SQLException
	 */
	public static DishResource createDefault(DatabaseManager manager, Dish dish, Resource resource) throws SQLException {
		String sql = "INSERT INTO Dish_Resource (dish_id, resource_id, amount) " +
				"VALUES (" + dish.getId() + ", " + resource.getId() + ", 1.0)";
		DishResource ret = null;
		try (PreparedStatement ps = manager.prepareStatement(sql)) {
			ps.executeUpdate();
			ret = manager.getDishResource(dish, resource);
		}
		return ret;
	}
	
	@Override
	public void fetch() throws SQLException {
		super.fetch();
		String sql = "SELECT amount FROM Dish_Resource WHERE dish_id = " + dish.getId() + " AND resource_id = " + resource.getId();
		try (PreparedStatement ps = getManager().prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					this.amount = rs.getDouble(1);
				}
			}
		}
	}
	
	@Override
	public void commit() throws SQLException {
		super.commit();
		String sql = "UPDATE Dish_Resource SET ";
		sql += "amount = " + amount;
		sql += " WHERE dish_id = " + dish.getId() + " AND resource_id = " + resource.getId();
		try (PreparedStatement ps = getManager().prepareStatement(sql)) {
			ps.executeUpdate();
		}
	}
	
	@Override
	public void remove() throws SQLException {
		super.remove();
		String sql = "DELETE FROM Dish_Resource WHERE dish_id = " + dish.getId() + " AND resource_id = " + resource.getId();
		try (PreparedStatement ps = getManager().prepareStatement(sql)) {
			ps.executeUpdate();
		}
	}
	
	/**
	 * Gets the dish.
	 * @return The dish
	 */
	public Dish getDish() {
		return dish;
	}
	
	/**
	 * Gets the resource.
	 * @return The resource
	 */
	public Resource getResource() {
		return resource;
	}
	
	/**
	 * Gets the amount of resource.
	 * @return The amount of resource
	 */
	public double getAmount() {
		tryFetch();
		return amount;
	}
	
	/**
	 * Sets the new amount of resource.
	 * @param amount The new amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof DishResource)) {
			return false;
		}
		DishResource otherLink = (DishResource)other;
		return dish.equals(otherLink.dish) && resource.equals(otherLink.resource);
	}
	
	@Override
	public String toString() {
		super.tryFetch();
		return "DeliveryDish[dishId = " + dish.getId() + "; resourceId = " + resource.getId() + "; amount = " + amount + "]";
	}
}
