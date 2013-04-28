package edu.hist.team3.catering.gui.panel;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public abstract class SearchPanel<T> extends JPanel implements SearchListener {
	protected SearchBar searchBar;
	protected JList<LabeledObject<T>> searchResult;
	
	public SearchPanel() {
		setLayout(new BorderLayout());
		searchResult = new JList<LabeledObject<T>>();
		searchResult.setModel(new DefaultListModel<LabeledObject<T>>());
		searchBar = new SearchBar();
		searchBar.setSearchListener(this);
		add(searchBar, BorderLayout.NORTH);
		add(new JScrollPane(searchResult), BorderLayout.CENTER);
	}
	
	public T getSelected() {
		if (getResultList().getSelectedValue() == null)
			return null;
		return getResultList().getSelectedValue().getObject();
	}
	
	public void doSearch() {
		searchBar.doSearch();
	}
	
	public SearchBar getSearchBar() {
		return searchBar;
	}
	
	public JList<LabeledObject<T>> getResultList() {
		return searchResult;
	}
}
