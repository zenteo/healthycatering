package edu.hist.team3.catering.gui.frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
		
		JButton button = new JButton("Close window");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					dishPanel.apply();
				}
				catch (SQLException e1) {
					e1.printStackTrace();
					Services.showError("Error: Could not save changes!");
				}
				setVisible(false);
			}
		});
		
		dishPanel = new DishEditPanel(dish, services);
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		content.add(dishPanel, BorderLayout.CENTER);
		content.add(button, BorderLayout.SOUTH);
		add(content);
		
		setTitle("Dish editor");
		setSize(900, 300);
		setAlwaysOnTop(true);
		setLocationRelativeTo(null);
	}
}