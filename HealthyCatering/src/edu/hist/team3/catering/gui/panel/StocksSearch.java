package edu.hist.team3.catering.gui.panel;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import edu.hist.team3.catering.database.Resource;
import edu.hist.team3.catering.database.manager.Services;

public class StocksSearch extends SearchPanel<Resource> {
	private Services services;
	
	public StocksSearch(Services services) {
		this.services = services;
		doSearch();
	}

	
	@Override
	public void onSearch(String text) {
		DefaultListModel<LabeledObject<Resource>> model;
		model = (DefaultListModel<LabeledObject<Resource>>)getResultList().getModel();
		model.clear();
		ArrayList<Resource> result = services.getResourceManager().findResource(text);
		for (Resource res : result) {
			String name = res.getStockCount() + " x [" + res.getCategory() + "] " + res.getName() + " from " + res.getProducer();
			LabeledObject<Resource> lo = new LabeledObject<Resource>(name, res);
			model.addElement(lo);
		}
	}

}
