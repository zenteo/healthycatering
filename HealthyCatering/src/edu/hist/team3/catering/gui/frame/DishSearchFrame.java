package edu.hist.team3.catering.gui.frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import edu.hist.team3.catering.database.Dish;
import edu.hist.team3.catering.database.manager.Services;
import edu.hist.team3.catering.gui.panel.DishSearch;

public class DishSearchFrame extends JFrame {
	private DishSearch dishSearch;
	private Dish selected;
	
	public DishSearchFrame(Services services) {

		this.dishSearch = new DishSearch(services);
		setLayout(new BorderLayout());
		add(dishSearch, BorderLayout.CENTER);
		JButton select = new JButton("Select");
		select.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setSelected(dishSearch.getSelected());
				setVisible(false);
			}
		});
		add(select, BorderLayout.SOUTH);
		setAlwaysOnTop(true);
		setTitle("Select dish");
		setSize(300, 200);
	}

	public Dish getSelected() {
		return selected;
	}

	public void setSelected(Dish selected) {
		this.selected = selected;
	}
}