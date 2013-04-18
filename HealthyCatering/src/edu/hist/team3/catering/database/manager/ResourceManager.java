package edu.hist.team3.catering.database.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.hist.team3.catering.database.DatabaseManager;
import edu.hist.team3.catering.database.Resource;

public class ResourceManager {
	private DatabaseManager manager;

	public ResourceManager(DatabaseManager manager) {
		this.manager = manager;
	}

	public ArrayList<Resource> findResource(String searchText) {
		searchText = searchText.toLowerCase();
		ArrayList<Resource> resources = new ArrayList<Resource>();
		try (ResultSet result = manager
				.performSql("SELECT id FROM Resource WHERE LOWER(name) LIKE '%"
						+ searchText + "%' OR LOWER(category) LIKE '%"
						+ searchText + "%' OR LOWER(producer) LIKE '%"
						+ searchText + "%'")) {
			while (result.next()) {
				resources.add(getResource(result.getInt(1)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resources;
	}

	public Resource createResource() {
		try {
			return manager.createResource();
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public Resource getResource(int id) {
		return manager.getResource(id);
	}

	public void removeResource(int id) throws SQLException {
		manager.getResource(id).remove();

	}
}
