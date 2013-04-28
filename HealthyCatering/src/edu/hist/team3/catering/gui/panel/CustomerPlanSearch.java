package edu.hist.team3.catering.gui.panel;

import java.util.Iterator;

import javax.swing.DefaultListModel;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.Plan;
import edu.hist.team3.catering.database.PlanDish;

@SuppressWarnings("serial")
public class CustomerPlanSearch extends SearchPanel<Plan> {
	private Customer customer = null;

	public CustomerPlanSearch() {

	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
		doSearch();
	}

	public Customer getCustomer() {
		return this.customer;
	}

	@Override
	public void onSearch(String text) {
		DefaultListModel<LabeledObject<Plan>> model = (DefaultListModel<LabeledObject<Plan>>) getResultList()
				.getModel();
		model.clear();
		text = text.toLowerCase();
		if (customer != null) {
			Iterator<Plan> it = customer.getPlans().iterator();
			while (it.hasNext()) {
				Plan p = it.next();
				String txt = p.getStartDate().toString();
				Iterator<PlanDish> it2 = p.getDishes().iterator();
				while (it2.hasNext()) {
					txt += ", " + it2.next().getDish().getName();
				}
				if (txt.toLowerCase().contains(text)) {
					model.addElement(new LabeledObject<Plan>(txt, p));
				}
			}
		}
	}

}
