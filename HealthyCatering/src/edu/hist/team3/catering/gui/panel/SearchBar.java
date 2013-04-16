package edu.hist.team3.catering.gui.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchBar extends JPanel implements ActionListener {
	private JButton searchButton;
	private JTextField searchText;
	private SearchListener searchListener = null;
	
	public SearchBar() {
		searchButton = new JButton("Search");
		searchButton.addActionListener(this);
		searchText = new JTextField();
		searchText.addActionListener(this);
		setLayout(new BorderLayout());
		add(searchText, BorderLayout.CENTER);
		add(searchButton, BorderLayout.EAST);
	}
	
	public void doSearch() {
		if (searchListener != null) {
			searchListener.onSearch(searchText.getText());
		}
	}
	
	public void setSearchListener(SearchListener listener) {
		this.searchListener = listener;
	}
	
	public SearchListener getSearchListener() {
		return this.searchListener;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		doSearch();
	}
}
