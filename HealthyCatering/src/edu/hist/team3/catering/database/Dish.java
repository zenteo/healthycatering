package edu.hist.team3.catering.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Dish extends DatabaseRow {
	private final int id;
	private String name;
	private String category;
	private double healthiness;
	private double price;
	private double defaultDiscount;
	private double longtermDiscount;
	private final DishResourceList resources;

	protected Dish(DatabaseManager manager, int id){
		super(manager);
		this.id = id;
		resources = new DishResourceList(this);
	}
	
	public static Dish createDefault(DatabaseManager manager) throws SQLException {
		String sql = "INSERT INTO Dish (NAME, CATEGORY, HEALTHINESS, PRICE, DEFAULT_DISCOUNT, LONGTERM_DISCOUNT)" +
				"VALUES ('', null, 1, 0, 0, 0)";
		Dish ret = null;
		try (PreparedStatement ps = manager.prepareStatement(sql, "id")) {
			ps.executeUpdate();
			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					ret = manager.getDish(rs.getInt(1));
				}
			}
		}
		return ret;
	}
	
	@Override
	public void fetch() throws SQLException {
		super.fetch();
		String sql = "SELECT NAME, CATEGORY, HEALTHINESS, PRICE, DEFAULT_DISCOUNT, LONGTERM_DISCOUNT FROM Dish WHERE id = " + id;
		try (PreparedStatement ps = getManager().prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					this.name = rs.getString(1);
					this.category = rs.getString(2);
					this.healthiness = rs.getDouble(3);
					this.price = rs.getDouble(4);
					this.defaultDiscount = rs.getDouble(5);
					this.longtermDiscount = rs.getDouble(6);
				}
			}
		}
	}
	
	@Override
	public void commit() throws SQLException {
		super.commit();
		String sql = "UPDATE Dish SET ";
		sql += "NAME = '" + name + "'";
		sql += ", CATEGORY = '" + category + "'";
		sql += ", HEALTHINESS = " + healthiness;
		sql += ", PRICE = " + price;
		sql += ", DEFAULT_DISCOUNT = " + defaultDiscount;
		sql += ", LONGTERM_DISCOUNT = " + longtermDiscount;
		sql += " WHERE id = " + id;
		try (PreparedStatement ps = getManager().prepareStatement(sql)) {
			ps.executeUpdate();
		}
	}
	
	@Override
	public void remove() throws SQLException {
		super.remove();
		String sql = "DELETE FROM Dish WHERE id = " + id;
		try (PreparedStatement ps = getManager().prepareStatement(sql)) {
			ps.executeUpdate();
		}
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() throws SQLException {
		super.tryFetch();
		return name;
	}

	public void setName(String name) {
		assert(name != null);				// NOT NULL
		assert(name.length() <= 64);		// Max length = 64
		super.setChanged();
		this.name = name;
	}

	public String getCategory() {
		super.tryFetch();
		return category;
	}

	public void setCategory(String category) {
		assert(category.length() <= 64);	// Max length = 64
		super.setChanged();
		this.category = category;
	}

	public double getHealthiness() {
		super.tryFetch();
		return healthiness;
	}

	public void setHealthiness(double healthiness) {
		super.setChanged();
		this.healthiness = healthiness;
	}

	public double getPrice() {
		super.tryFetch();
		return price;
	}

	public void setPrice(double price) {
		super.setChanged();
		this.price = price;
	}

	public double getDefaultDiscount() {
		super.tryFetch();
		return defaultDiscount;
	}

	public void setDefaultDiscount(double defaultDiscount) {
		super.setChanged();
		this.defaultDiscount = defaultDiscount;
	}

	public double getLongtermDiscount() {
		super.tryFetch();
		return longtermDiscount;
	}

	public void setLongtermDiscount(double longtermDiscount) {
		super.setChanged();
		this.longtermDiscount = longtermDiscount;
	}

	public DishResourceList getResources() {
		assert(!isRemoved());
		return resources;
	}

	public boolean equals(Object other) {
		if(other == null || !(other instanceof Dish)) {
			return false;
		}
		return getId() == ((Dish)other).getId();
	}

	public String toString() {
		super.tryFetch();
		return "Dish[name = '" + name + "'; healthiness = " + healthiness
				+ "; price = " + price + "; defaultDiscount = " + defaultDiscount
				+ "; longtermDiscount = " + longtermDiscount + "; resources = "
				+ resources + "]";
	}


}
