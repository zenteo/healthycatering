package edu.hist.team3.catering.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DishResource extends DatabaseRow {
	private final Dish dish;
	private final Resource resource;
	private double amount;
	
	protected DishResource(DatabaseManager manager, Dish dish, Resource resource) {
		super(manager);
		assert(dish != null);
		assert(resource != null);
		this.dish = dish;
		this.resource = resource;
	}
	
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
					this.amount = rs.getInt(1);
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
	
	public Dish getDish() {
		return dish;
	}
	
	public Resource getResource() {
		return resource;
	}
	
	public double getAmount() {
		return amount;
	}
	
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
