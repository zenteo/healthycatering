package edu.hist.team3.catering.database;

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
	
	public static DishResource createDefault(DatabaseManager manager, Dish dish, Resource resource) {
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
