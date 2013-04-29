package edu.hist.team3.catering.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;

public class DishResourceList {
	private HashSet<DishResource> links = null;
	private final Dish dish;
	/**
	 * Creates a new instance of DishResourceList
	 * @param dish
	 */
	public DishResourceList(Dish dish) {
		assert(dish != null);
		this.dish = dish;
	}
	/**
	 * Gives the dish
	 * @return
	 */
	public Dish getDish() {
		return dish;
	}
	/**
	 * adds a resource to a dish
	 * @param resource
	 * @return DishResource
	 * @throws SQLException
	 */
	public DishResource add(Resource resource) throws SQLException {
		assert(dish != null);
		DishResource link = dish.getManager().createDishResource(dish, resource);
		if (links != null && link != null) {
			links.add(link);
		}
		return link;
	}
	/**
	 * Gives DishResources based on resource
	 * @param resource
	 * @return DishResource
	 */
	public DishResource get(Resource resource) {
		assert(dish != null);
		tryFetch();
		Iterator<DishResource> it = iterator();
		while (it.hasNext()) {
			DishResource ret = it.next();
			if (ret.getResource().equals(resource)) {
				return ret;
			}
				
		}
		return null;
	}
	/**
	 * removes a resource
	 * @param resource
	 * @throws SQLException
	 */
	public void remove(Resource resource) throws SQLException {
		assert(dish != null);
		DishResource link = get(resource);
		if (link != null) {
			link.remove();
			if (links != null) {
				links.remove(link);
			}
		}
	}
	/**
	 * removes all resources
	 * @throws SQLException
	 */
	public void removeAll() throws SQLException {
		Iterator<DishResource> it = iterator();
		while (it.hasNext()) {
			it.next().remove();
		}
		links.clear();
	}
	/**
	 * Gives if Dish has resource
	 * @param resource
	 * @return yes/no if resource
	 */
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
