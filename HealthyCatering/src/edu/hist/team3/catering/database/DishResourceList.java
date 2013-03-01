package edu.hist.team3.catering.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;

public class DishResourceList {
	private HashSet<DishResource> links = null;
	private final Dish dish;
	
	public DishResourceList(Dish dish) {
		assert(dish != null);
		this.dish = dish;
	}
	
	public Dish getDish() {
		return dish;
	}
	
	public DishResource add(Resource resource) throws SQLException {
		assert(dish != null);
		DishResource link = dish.getManager().createDishResource(dish, resource);
		if (links != null && link != null) {
			links.add(link);
		}
		return link;
	}
	
	public DishResource get(Resource resource) {
		assert(dish != null);
		return dish.getManager().getDishResource(dish, resource);
	}
	
	public void remove(Resource resource) throws SQLException {
		assert(dish != null);
		DishResource link = dish.getManager().getDishResource(dish, resource);
		link.remove();
		if (links != null) {
			links.remove(link);
		}
	}
	
	public void removeAll() throws SQLException {
		Iterator<DishResource> it = iterator();
		while (it.hasNext()) {
			it.next().remove();
		}
		links.clear();
	}
	
	public boolean contains(Resource resource) {
		tryFetch();
		return links.contains(dish.getManager().getDishResource(dish, resource));
	}
	
	public Iterator<DishResource> iterator() {
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void fetch() throws SQLException {
		String sql = "SELECT resource_id FROM Dish_Resource WHERE dish_id = " + dish.getId();
		try (PreparedStatement ps = dish.getManager().prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				links = new HashSet<DishResource>();
				while (rs.next()) {
					Resource resource = dish.getManager().getResource(rs.getInt(1));
					DishResource dishResource = dish.getManager().getDishResource(dish, resource);
					links.add(dishResource);
				}
			}
		}
	}
	
	@Override
	public String toString() {
		tryFetch();
		String ret = "DishResourceList[";
		Iterator<DishResource> it = iterator();
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
