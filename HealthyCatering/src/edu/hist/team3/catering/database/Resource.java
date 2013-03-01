package edu.hist.team3.catering.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Resource extends DatabaseRow {
	private final int id; 		// id			INT				GENERATED BY DEFAULT AS IDENTITY
	private String name;		// name			VARCHAR(64)		NOT NULL
	private String category;	// category		VARCHAR(64)
	private String description;	// description	VARCHAR(256)
	private String producer;	// producer		VARCHAR(64)
	private String source;		// source		VARCHAR(64)
	private double stockCount;	// stock_count	DOUBLE			NOT NULL
	private double costs;		// costs		DOUBLE
	private double amount;		// amount		DOUBLE
	private double weight;		// weight		DOUBLE
	private double volume;		// volume		DOUBLE
	private double calories;	// calories		DOUBLE
	private double healthiness;	// healthiness	DOUBLE
	
	protected Resource(DatabaseManager manager, int id) {
		super(manager);
		this.id = id;
	}
	
	public static Resource createDefault(DatabaseManager manager) throws SQLException {
		String sql = "INSERT INTO Resource (name, category, description, producer, source, stock_count, costs, amount, weight, volume, calories, healthiness)" +
				"VALUES ('', null, null, null, null, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)";
		Resource ret = null;
		try (PreparedStatement ps = manager.prepareStatement(sql, "id")) {
			ps.executeUpdate();
			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					ret = manager.getResource(rs.getInt(1));
				}
			}
		}
		return ret;
	}
	
	@Override
	public void fetch() throws SQLException {
		super.fetch();
		String sql = "SELECT name, category, description, producer, source, stock_count, costs, amount, weight, volume, calories, healthiness FROM Resource WHERE id = " + id;
		try (PreparedStatement ps = getManager().prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					this.name = rs.getString(1);
					this.category = rs.getString(2);
					this.description = rs.getString(3);
					this.producer = rs.getString(4);
					this.source = rs.getString(5);
					this.stockCount = rs.getDouble(6);
					this.costs = rs.getDouble(7);
					this.weight = rs.getDouble(8);
					this.calories = rs.getDouble(9);
					this.healthiness = rs.getDouble(10);
				}
			}
		}
	}
	
	@Override
	public void commit() throws SQLException {
		super.commit();
		
		String sql = "UPDATE Resource SET ";
		sql += "name = '" + name + "'";
		
		if (category == null) {
			sql += ", category = NULL";
		}
		else {
			sql += ", category = '" + category + "'";
		}
		
		if (description == null) {
			sql += ", description = NULL";
		}
		else {
			sql += ", description = '" + description + "'";
		}
		
		if (producer == null) {
			sql += ", producer = NULL";
		}
		else {
			sql += ", producer = '" + producer + "'";
		}
		
		if (source == null) {
			sql += ", source = NULL";
		}
		else {
			sql += ", source = '" + source + "'";
		}
		
		sql += ", stock_count = " + stockCount;
		sql += ", costs = " + costs;
		sql += ", amount = " + amount;
		sql += ", weight = " + weight;
		sql += ", volume = " + volume;
		sql += ", calories = " + calories;
		sql += ", healthiness = " + healthiness;
		sql += " WHERE id = " + id;
		
		try (PreparedStatement ps = getManager().prepareStatement(sql)) {
			ps.executeUpdate();
		}
	}
	
	@Override
	public void remove() throws SQLException {
		super.remove();
		String sql = "DELETE FROM Resource WHERE id = " + id;
		try (PreparedStatement ps = getManager().prepareStatement(sql)) {
			ps.executeUpdate();
		}
	}

	public String getName() {
		super.tryFetch();
		return name;
	}

	public void setName(String name) {
		assert(name != null);
		assert(name.length() <= 64);
		super.setChanged();
		this.name = name;
	}

	public String getCategory() {
		super.tryFetch();
		return category;
	}

	public void setCategory(String category) {
		assert(category.length() <= 64);
		super.setChanged();
		this.category = category;
	}

	public String getDescription() {
		super.tryFetch();
		return description;
	}

	public void setDescription(String description) {
		assert(description.length() <= 256);
		super.setChanged();
		this.description = description;
	}

	public String getProducer() {
		super.tryFetch();
		return producer;
	}

	public void setProducer(String producer) {
		assert(producer.length() <= 64);
		super.setChanged();
		this.producer = producer;
	}

	public String getSource() {
		super.tryFetch();
		return source;
	}

	public void setSource(String source) {
		assert(source.length() <= 64);
		super.setChanged();
		this.source = source;
	}

	public double getStockCount() {
		super.tryFetch();
		return stockCount;
	}

	public void setStockCount(double stockCount) {
		super.setChanged();
		this.stockCount = stockCount;
	}

	public double getCosts() {
		super.tryFetch();
		return costs;
	}

	public void setCosts(double costs) {
		super.setChanged();
		this.costs = costs;
	}

	public double getAmount() {
		super.tryFetch();
		return amount;
	}

	public void setAmount(double amount) {
		super.setChanged();
		this.amount = amount;
	}

	public double getWeight() {
		super.tryFetch();
		return weight;
	}

	public void setWeight(double weight) {
		super.setChanged();
		this.weight = weight;
	}

	public double getVolume() {
		super.tryFetch();
		return volume;
	}

	public void setVolume(double volum) {
		super.setChanged();
		this.volume = volum;
	}

	public double getCalories() {
		super.tryFetch();
		return calories;
	}

	public void setCalories(double calories) {
		super.setChanged();
		this.calories = calories;
	}

	public double getHealthiness() {
		super.tryFetch();
		return healthiness;
	}

	public void setHealthiness(double healthiness) {
		super.setChanged();
		this.healthiness = healthiness;
	}

	public int getId() {
		super.tryFetch();
		return id;
	}
	
	public boolean equals(Object other) {
		if(other == null || !(other instanceof Resource))
			return false;
		return getId() == ((Resource)other).getId();
	}
	
	public String toString() {
		return "Resource[name = '" + name + "'; category = '"
				+ category + "'; description = '" + description + "'; producer = '"
				+ producer + "'; source = " + source + "; stockCount = "
				+ stockCount + "; costs = " + costs + "; amount = " + amount
				+ "; weight = " + weight + "; volum = " + volume + "; calories = "
				+ calories + "; healthiness = " + healthiness + "]";
	}
}
