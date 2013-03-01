package edu.hist.team3.catering.database;

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
	
	public DishResource add(Resource resource) {
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
	
	public void remove(Resource resource) {
		assert(dish != null);
		DishResource link = dish.getManager().getDishResource(dish, resource);
		link.remove();
		if (links != null) {
			links.remove(link);
		}
	}
	
	public void removeAll() {
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
		if (links == null)
			fetch();
	}
	
	public void fetch() {
		//TODO: Give me a body!!!
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
