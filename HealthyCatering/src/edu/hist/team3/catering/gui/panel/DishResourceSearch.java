package edu.hist.team3.catering.gui.panel;

import java.util.Iterator;

import javax.swing.DefaultListModel;

import edu.hist.team3.catering.database.Dish;
import edu.hist.team3.catering.database.DishResource;

@SuppressWarnings("serial")
public class DishResourceSearch extends SearchPanel<DishResource> {
	private Dish dish;
	/**
	 * Creates a new instance of DishResourceSearch based on dish
	 * @param dish
	 */
	public DishResourceSearch(Dish dish) {
		this.dish = dish;
		doSearch();
	}
/**
 * Gives you the dish
 * @return dish
 */
	public Dish getDish() {
		return dish;
	}
	/**
	 * Sets the dish
	 * @param dish
	 */
	public void setDish(Dish dish) {
		this.dish = dish;
		doSearch();
	}
	
	@Override
	public void onSearch(String text) {
		if (dish == null)
			return;
		DefaultListModel<LabeledObject<DishResource>> model;
		model = (DefaultListModel<LabeledObject<DishResource>>)getResultList().getModel();
		model.clear();
		Iterator<DishResource> it = dish.getResources().iterator();
		while (it.hasNext()) {
			DishResource dr = it.next();
			double amount = dr.getResource().getAmount();
			if (amount == 0.0)
				amount = 1.0;
			amount *= dr.getAmount();
			String label = amount + " x [" + dr.getResource().getCategory() +"] " + dr.getResource().getName();
			label += " from " + dr.getResource().getProducer();
			LabeledObject<DishResource> labelObj = new LabeledObject<DishResource>(label, dr);
			model.addElement(labelObj);
		}
	}
 }
