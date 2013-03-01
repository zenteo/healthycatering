package edu.hist.team3.catering;

import java.sql.SQLException;

import edu.hist.team3.catering.database.DatabaseManager;
import edu.hist.team3.catering.database.Dish;

public class TestDatabase {
	public static void main(String[] args) {
		try {
			DatabaseManager manager = new DatabaseManager("jdbc:derby://localhost:1527/healthycatering", "db", "db");
			System.out.println("Create dish");
			Dish dish = manager.createDish();
			dish.setName("Porkchops");
			dish.setCategory("Meat");
			dish.setHealthiness(1.0);
			dish.setPrice(100);
			dish.setDefaultDiscount(0.1);
			dish.setLongtermDiscount(0.2);
			
			System.out.println("Commit dish");
			dish.commit();
			
			int id = dish.getId();
			dish = null;
			Dish a = manager.getDish(id);
			Dish b = manager.getDish(id);
			// Rollme advasdf
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Done");
	}
}
