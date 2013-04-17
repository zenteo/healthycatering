package edu.hist.team3.catering.gui.panel;

import java.sql.SQLException;
import java.util.Iterator;

import javax.swing.DefaultListModel;

import edu.hist.team3.catering.database.Plan;
import edu.hist.team3.catering.database.PlanDish;

public class PlanDishSearch extends SearchPanel<PlanDish> {
	private Plan plan;

	public PlanDishSearch(Plan plan) {
		this.plan = plan;
		doSearch();
	}
	
	@Override
	public void onSearch(String text) {
		DefaultListModel<LabeledObject<PlanDish>> model = (DefaultListModel<LabeledObject<PlanDish>>) getResultList()
				.getModel();
		model.clear();
		Iterator<PlanDish> it = plan.getDishes().iterator();
		try {
			while (it.hasNext()) {
				PlanDish pd = it.next();
				String txt;
				txt = pd.getCount() + " x " + pd.getDish().getName();
				if (txt.toLowerCase().contains(text)) {
					model.addElement(new LabeledObject<PlanDish>(txt, pd));
				}
			}
		} catch (SQLException e) {
			// TODO Throw Error.
			e.printStackTrace();
		}
	}

}
