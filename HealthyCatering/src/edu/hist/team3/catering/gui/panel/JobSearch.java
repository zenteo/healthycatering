package edu.hist.team3.catering.gui.panel;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import edu.hist.team3.catering.database.Job;
import edu.hist.team3.catering.database.manager.Services;

@SuppressWarnings("serial")
public class JobSearch extends SearchPanel<Job> {
	private Services services;
	
	public JobSearch(Services services) {
		this.services = services;
		onSearch("");
	}
	
	@Override
	public void onSearch(String text) {
		ArrayList<Job> ret = services.getJobManager().findJob(text);
		
		DefaultListModel<LabeledObject<Job>> model;
		model = (DefaultListModel<LabeledObject<Job>>) getResultList().getModel();
		model.clear();
		
		for (Job c : ret) {
			model.addElement(new LabeledObject<Job>(c.getName(), c));
		}
		repaint();

	}

}
