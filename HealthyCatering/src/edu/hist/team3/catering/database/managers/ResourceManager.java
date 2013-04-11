package edu.hist.team3.catering.database.managers;

import java.sql.SQLException;

import edu.hist.team3.catering.database.DatabaseManager;
import edu.hist.team3.catering.database.Resource;

public class ResourceManager {
	DatabaseManager manager;
	private static ResourceManager rManager;

	public static ResourceManager getInstance() {
		if (rManager == null)
			rManager = new ResourceManager();
		return rManager;
	}

	private ResourceManager() {
		manager = DatabaseManager.getInstance();
	}

	public void createResource() throws SQLException {
		manager.createResource();
	}

	public Resource getResource(int id) {
		return manager.getResource(id);
	}

	public void changeResource(int id, String name, String category,
			String description, String producer, String source,
			double stockCount, double costs, double amount, double weight,
			double volume, double calories, double healthiness) {
		manager.getResource(id).setName(name);
		manager.getResource(id).setCategory(category);
		manager.getResource(id).setDescription(description);
		manager.getResource(id).setProducer(producer);
		manager.getResource(id).setSource(source);
		manager.getResource(id).setStockCount(stockCount);
		manager.getResource(id).setCosts(costs);
		manager.getResource(id).setAmount(amount);
		manager.getResource(id).setWeight(weight);
		manager.getResource(id).setVolume(volume);
		manager.getResource(id).setCalories(calories);
		manager.getResource(id).setHealthiness(healthiness);
			
	}

	public void removeResource(int id) throws SQLException {
		manager.getResource(id).remove();

	}
}
