package edu.hist.team3.catering.database.manager;

import java.sql.SQLException;

import edu.hist.team3.catering.database.DatabaseManager;
import edu.hist.team3.catering.database.Resource;

public class ResourceManager {
	private DatabaseManager manager;

	public ResourceManager(DatabaseManager manager) {
		this.manager = manager;
	}

	public void createResource() throws SQLException {
		manager.createResource();
	}

	public Resource getResource(int id) {
		return manager.getResource(id);
	}

	public void removeResource(int id) throws SQLException {
		manager.getResource(id).remove();

	}
}
