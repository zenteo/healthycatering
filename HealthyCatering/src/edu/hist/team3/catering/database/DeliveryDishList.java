package edu.hist.team3.catering.database;

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
	
	public DeliveryDish add(Dish dish) {
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
	
	public void remove(Dish dish) {
		assert(dish != null);
		DeliveryDish link = delivery.getManager().getDeliveryDish(delivery, dish);
		link.remove();
		if (links != null) {
			links.remove(link);
		}
	}
	
	public void removeAll() {
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
		if (links == null)
			fetch();
	}
	
	public void fetch() {
		//TODO: Give me a body!!!
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
