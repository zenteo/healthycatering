package edu.hist.team3.catering.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeliveryDish extends DatabaseRow {
	private final Delivery delivery;	// delivery_id	INT		NOT NULL
	private final Dish dish;			// dish_id		INT		NOT NULL
	private int count;					// count		INT		NOT NULL
	private double discount;			// discount		DOUBLE	NOT NULL
	
	protected DeliveryDish(DatabaseManager manager, Delivery delivery, Dish dish) {
		super(manager);
		assert(dish != null);
		assert(delivery != null);
		this.delivery = delivery;
		this.dish = dish;
	}
	
	public static DeliveryDish createDefault(DatabaseManager manager, Delivery delivery, Dish dish) throws SQLException {
		String sql = "INSERT INTO Delivery_Dish (delivery_id, dish_id, count, discount) " +
				"VALUES (" + delivery.getId() + ", " + dish.getId() + ", 1, " + dish.getDefaultDiscount() + ")";
		DeliveryDish ret = null;
		try (PreparedStatement ps = manager.prepareStatement(sql)) {
			ps.executeUpdate();
			ret = manager.getDeliveryDish(delivery, dish);
		}
		return ret;
	}
	
	@Override
	public void fetch() throws SQLException {
		super.fetch();
		String sql = "SELECT count, discount FROM Delivery_Dish WHERE delivery_id = " + delivery.getId() + " AND dish_id = " + dish.getId();
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
		String sql = "UPDATE Delivery_Dish SET ";
		sql += "count = " + count;
		sql += ", discount = " + discount;
		sql += " WHERE delivery_id = " + delivery.getId() + " AND dish_id = " + dish.getId();
		try (PreparedStatement ps = getManager().prepareStatement(sql)) {
			ps.executeUpdate();
		}
	}
	
	@Override
	public void remove() throws SQLException {
		super.remove();
		String sql = "DELETE FROM Delivery_Dish WHERE delivery_id = " + delivery.getId() + " AND dish_id = " + dish.getId();
		try (PreparedStatement ps = getManager().prepareStatement(sql)) {
			ps.executeUpdate();
		}
	}
	
	public Delivery getDelivery() {
		return delivery;
	}
	
	public Dish getDish() {
		return dish;
	}
	
	public int getCount() {
		super.tryFetch();
		return count;
	}
	
	public void setCount(int count) {
		super.setChanged();
		this.count = count;
	}
	
	public void addCount(int deltaCount) {
		super.setChanged();
		this.count += deltaCount;
	}
	
	public double getDiscount() {
		super.tryFetch();
		return discount;
	}
	
	public void setDiscount(double discount) {
		super.setChanged();
		this.discount = discount;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof DeliveryDish)) {
			return false;
		}
		DeliveryDish otherLink = (DeliveryDish)other;
		return delivery.equals(otherLink.delivery) && dish.equals(otherLink.dish);
	}
	
	@Override
	public String toString() {
		super.tryFetch();
		return "DeliveryDish[deliveryId = " + delivery.getId() + "; dishId = " + dish.getId() + "; count = " + count + "; discount = " + discount + "]";
	}
}
