package edu.hist.team3.catering.gui.panel;

import java.util.Iterator;

import javax.swing.DefaultListModel;

import edu.hist.team3.catering.database.Plan;
import edu.hist.team3.catering.database.PlanDish;

@SuppressWarnings("serial")
public class PlanDishSearch extends SearchPanel<PlanDish> {
	private Plan plan;

	/**
	 * Create a new PlanDishSearch with selected plan.
	 * @param plan
	 */
	public PlanDishSearch(Plan plan) {
		this.plan = plan;
		doSearch();
	}

	/**
	 * This method will search for a link between a plan and dish with selected search text.
	 */
	@Override
	public void onSearch(String text) {
		DefaultListModel<LabeledObject<PlanDish>> model = (DefaultListModel<LabeledObject<PlanDish>>) getResultList()
				.getModel();
		model.clear();
		Iterator<PlanDish> it = plan.getDishes().iterator();
		while (it.hasNext()) {
			PlanDish pd = it.next();
			String txt;
			txt = pd.getCount() + " x " + pd.getDish().getName();
			if (txt.toLowerCase().contains(text)) {
				model.addElement(new LabeledObject<PlanDish>(txt, pd));
			}
		}
	}

}
