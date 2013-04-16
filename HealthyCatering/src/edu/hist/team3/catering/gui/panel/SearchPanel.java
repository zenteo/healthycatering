package edu.hist.team3.catering.gui.panel;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public abstract class SearchPanel extends JPanel implements SearchListener {
	protected SearchBar searchBar;
	protected JList<LabeledObject> searchResult;
	
	public SearchPanel() {
		setLayout(new BorderLayout());
		searchResult = new JList<LabeledObject>();
		searchResult.setModel(new DefaultListModel<LabeledObject>());
		searchBar = new SearchBar();
		searchBar.setSearchListener(this);
		add(searchBar, BorderLayout.NORTH);
		add(new JScrollPane(searchResult), BorderLayout.CENTER);
	}
	
	public void doSearch() {
		searchBar.doSearch();
	}
	
	public SearchBar getSearchBar() {
		return searchBar;
	}
	
	public JList<LabeledObject> getResultList() {
		return searchResult;
	}
}
