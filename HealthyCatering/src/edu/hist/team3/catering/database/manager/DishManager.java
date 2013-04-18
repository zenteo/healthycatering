package edu.hist.team3.catering.database.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.DatabaseManager;
import edu.hist.team3.catering.database.Dish;

public class DishManager {
	private DatabaseManager manager;

	public DishManager(DatabaseManager manager) {
		this.manager = manager;
	}
	
	public ArrayList<Dish> findDish(String searchText) {
		searchText = searchText.toLowerCase();
		ArrayList<Dish> dishes = new ArrayList<Dish>();
		try (ResultSet result = manager.performSql("SELECT id FROM Dish WHERE LOWER(name) LIKE '%" + searchText + "%'")) {
			while (result.next()) {
				dishes.add(manager.getDish(result.getInt(1)));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return dishes;
	}
	
	public Dish getDish(int id){
		return manager.getDish(id);
	}

	public Dish createDish() {
		try {
			return manager.createDish();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public void removeDish(int id)throws SQLException{
		manager.getDish(id).remove();
	}

}
