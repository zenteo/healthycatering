package edu.hist.team3.catering.gui.frame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import edu.hist.team3.catering.database.Dish;
import edu.hist.team3.catering.database.manager.Services;
import edu.hist.team3.catering.gui.panel.DishEditPanel;

public class DishEditFrame extends JFrame {
	private DishEditPanel dishPanel;

	public DishEditFrame(Dish dish, Services services) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					dishPanel.apply();
				}
				catch (SQLException e1) {
					e1.printStackTrace();
					Services.showError("Error: Could not save changes!");
				}
			}
		});
		
		dishPanel = new DishEditPanel(dish, services);
		add(new JScrollPane(dishPanel));

		setTitle("Dish editor");
		setSize(700, 300);
		setAlwaysOnTop(true);
		setLocationRelativeTo(null);
	}
}