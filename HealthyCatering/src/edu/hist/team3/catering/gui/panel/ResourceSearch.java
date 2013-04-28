package edu.hist.team3.catering.gui.panel;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import edu.hist.team3.catering.database.Resource;
import edu.hist.team3.catering.database.manager.Services;

@SuppressWarnings("serial")
public class ResourceSearch extends SearchPanel<Resource> {
	private Services services;
	
	public ResourceSearch(Services services) {
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
			String name = "[" + res.getCategory() + "] " + res.getName() + " from " + res.getProducer();
			LabeledObject<Resource> lo = new LabeledObject<Resource>(name, res);
			model.addElement(lo);
		}
	}
}
