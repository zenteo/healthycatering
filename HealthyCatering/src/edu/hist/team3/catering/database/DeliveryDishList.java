package edu.hist.team3.catering.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;

public class DeliveryDishList {
	private HashSet<DeliveryDish> links = null;
	private final Delivery delivery;
	
	public DeliveryDishList(Delivery delivery) {
		assert(delivery != null);
		this.delivery = delivery;
	}
	
	public Delivery getDelivery() {
		return delivery;
	}
	
	public DeliveryDish add(Dish dish) throws SQLException {
		assert(dish != null);
		DeliveryDish link = delivery.getManager().createDeliveryDish(delivery, dish);
		if (links != null && link != null) {
			links.add(link);
		}
		return link;
	}
	
	public DeliveryDish get(Dish dish) {
		assert(dish != null);
		return delivery.getManager().getDeliveryDish(delivery, dish);
	}
	
	public void remove(Dish dish) throws SQLException {
		assert(dish != null);
		DeliveryDish link = delivery.getManager().getDeliveryDish(delivery, dish);
		link.remove();
		if (links != null) {
			links.remove(link);
		}
	}
	
	public void removeAll() throws SQLException {
		Iterator<DeliveryDish> it = iterator();
		while (it.hasNext()) {
			it.next().remove();
		}
		links.clear();
	}
	
	public boolean contains(Dish dish) {
		tryFetch();
		return links.contains(delivery.getManager().getDeliveryDish(delivery, dish));
	}
	
	public Iterator<DeliveryDish> iterator() {
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
		String sql = "SELECT dish_id FROM Delivery_Dish WHERE delivery_id = " + delivery.getId();
		try (PreparedStatement ps = delivery.getManager().prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				links = new HashSet<DeliveryDish>();
				while (rs.next()) {
					Dish dish = delivery.getManager().getDish(rs.getInt(1));
					DeliveryDish deliveryDish = delivery.getManager().getDeliveryDish(delivery, dish);
					links.add(deliveryDish);
				}
			}
		}
	}
	
	@Override
	public String toString() {
		tryFetch();
		String ret = "DeliveryDishList[";
		Iterator<DeliveryDish> it = iterator();
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
