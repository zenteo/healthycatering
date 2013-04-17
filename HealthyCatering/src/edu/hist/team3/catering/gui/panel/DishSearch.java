package edu.hist.team3.catering.gui.panel;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

import edu.hist.team3.catering.database.Dish;
import edu.hist.team3.catering.database.manager.Services;

public class DishSearch extends SearchPanel<Dish> {
	private Services services;
	
	public DishSearch(Services services) {
		this.services = services;
		doSearch();
	}
	
	@Override
	public void onSearch(String text) {
		ArrayList<Dish> dishes = services.getDishManager().findDish(text);
		DefaultListModel<LabeledObject<Dish>> model = (DefaultListModel<LabeledObject<Dish>>)getResultList().getModel();
		model.clear();
		try {
			for (Dish dish : dishes) {
				int healthiness = (int)(dish.getHealthiness() * 100.0);
				String dishText = "[" + dish.getCategory() + "] " + dish.getName() + " " + healthiness + "% healthy";
				LabeledObject<Dish> obj = new LabeledObject<Dish>(dishText, dish);
				model.addElement(obj);
			}
		} catch (SQLException e) {
			Services.showError("Error: Could not receive info from database!");
			e.printStackTrace();
		}
	}

}
