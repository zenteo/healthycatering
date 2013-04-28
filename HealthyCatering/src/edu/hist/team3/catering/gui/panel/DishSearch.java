package edu.hist.team3.catering.gui.panel;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import edu.hist.team3.catering.database.Dish;
import edu.hist.team3.catering.database.manager.Services;

@SuppressWarnings("serial")
public class DishSearch extends SearchPanel<Dish> {
	private Services services;

	public DishSearch(Services services) {
		this.services = services;
		doSearch();
	}

	@Override
	public void onSearch(String text) {
		ArrayList<Dish> dishes = services.getDishManager().findDish(text);
		DefaultListModel<LabeledObject<Dish>> model = (DefaultListModel<LabeledObject<Dish>>) getResultList()
				.getModel();
		model.clear();
		for (Dish dish : dishes) {
			int healthiness = (int) (dish.getHealthiness() * 100.0);
			String dishText = "[" + dish.getCategory() + "] " + dish.getName()
					+ " " + healthiness + "% healthy";
			LabeledObject<Dish> obj = new LabeledObject<Dish>(dishText, dish);
			model.addElement(obj);
		}
	}

}
