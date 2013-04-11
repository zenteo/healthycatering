package edu.hist.team3.catering.database.managers;

import java.sql.SQLException;

import edu.hist.team3.catering.database.DatabaseManager;
import edu.hist.team3.catering.database.Dish;

public class DishManager {
	private DatabaseManager manager;
	private static DishManager dManager;
	
	public static DishManager getInstance() {
		if (dManager == null)
			dManager = new DishManager();
		return dManager;
	}
	
	public DishManager() {
		manager = DatabaseManager.getInstance();
	}
	public Dish getDish(int id){
		return manager.getDish(id);
	}
	public void editDish(int id, String name, String category,double healthiness,double price, double dDiscount, double lDiscount ){
		manager.getDish(id).setName(name);
		manager.getDish(id).setCategory(category);
		manager.getDish(id).setHealthiness(healthiness);
		manager.getDish(id).setPrice(price);
		manager.getDish(id).setDefaultDiscount(dDiscount);
		manager.getDish(id).setLongtermDiscount(lDiscount);
	}
	public void addDish()throws SQLException{
		manager.createDish();
	}
	public void removeDish(int id)throws SQLException{
		manager.getDish(id).remove();
	}

}
