package edu.hist.team3.catering.database;

public class DeliveryDish extends DatabaseRow {
	private final Delivery delivery;
	private final Dish dish;
	private int count;
	
	protected DeliveryDish(DatabaseManager manager, Delivery delivery, Dish dish) {
		super(manager);
		assert(dish != null);
		assert(delivery != null);
		this.delivery = delivery;
		this.dish = dish;
	}
	
	public static DeliveryDish createDefault(DatabaseManager manager, Delivery delivery, Dish dish) {
		return null;
	}
	
	@Override
	public void fetch() {
		//TODO: Give me a body!!!
	}
	
	@Override
	public void commit() {
		//TODO: Give me a body!!!
	}
	
	@Override
	public void remove() {
		//TODO: Give me a body!!!
	}
	
	public Delivery getDelivery() {
		return delivery;
	}
	
	public Dish getDish() {
		return dish;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
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
		return "DeliveryDish[deliveryId = " + delivery.getId() + "; dishId = " + dish.getId() + "; count = " + count + "]";
	}
}
