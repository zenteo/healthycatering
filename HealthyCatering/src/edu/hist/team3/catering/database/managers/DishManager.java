package edu.hist.team3.catering.database.managers;

import java.sql.SQLException;

import edu.hist.team3.catering.database.DatabaseManager;
import edu.hist.team3.catering.database.Dish;

public class DishManager {
	private DatabaseManager manager;

	public DishManager(DatabaseManager manager) {
		this.manager = manager;
	}
	
	public Dish getDish(int id){
		return manager.getDish(id);
	}

	public Dish addDish()throws SQLException{
		return manager.createDish();
	}
	
	public void removeDish(int id)throws SQLException{
		manager.getDish(id).remove();
	}

}
