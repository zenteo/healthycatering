package edu.hist.team3.catering.gui.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SearchBar extends JPanel implements ActionListener {
	private JButton searchButton;
	private JTextField searchText;
	private SearchListener searchListener = null;
	
	/**
	 * Creates a new SearchBar.
	 */
	public SearchBar() {
		searchButton = new JButton("Search");
		searchButton.addActionListener(this);
		searchText = new JTextField();
		searchText.addActionListener(this);
		setLayout(new BorderLayout());
		add(searchText, BorderLayout.CENTER);
		add(searchButton, BorderLayout.EAST);
	}
	
	/**
	 * A method that is implemented in other objects for specifying what object to search for.
	 */
	public void doSearch() {
		if (searchListener != null) {
			searchListener.onSearch(searchText.getText());
		}
	}
	
	/**
	 * Set objects search listener.
	 * @param listener
	 */
	public void setSearchListener(SearchListener listener) {
		this.searchListener = listener;
	}
	
	/**
	 * Returns the search listener.
	 * @return
	 */
	public SearchListener getSearchListener() {
		return this.searchListener;
	}

	/**
	 * A method that is implemented from ActionListener where it does a search.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		doSearch();
	}
}
