package edu.hist.team3.catering.gui.tab;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.hist.team3.catering.database.Dish;
import edu.hist.team3.catering.database.Resource;
import edu.hist.team3.catering.database.manager.Services;
import edu.hist.team3.catering.gui.frame.DishEditFrame;
import edu.hist.team3.catering.gui.frame.ResourceEditFrame;
import edu.hist.team3.catering.gui.panel.DishSearch;
import edu.hist.team3.catering.gui.panel.ResourceSearch;


/*
 * 
 *
 *Menu GUI
 *--
 *--
 *+ findResources()
 *+ editResources()
 *+ addResources()
 *+ removeResource()
 *+ findDishes()
 *+ editDises()
 *+ addDish()
 *+ removeDish()
 *+ findMenus()
 *+ addMenu()
 *+ editMenu()
 *+ removeMenu()
 */
@SuppressWarnings("serial")
public class MenuTab extends JPanel{
	private Services services;
	private DishSearch dishSearch;
	private ResourceSearch resourceSearch;
	
	public MenuTab(final Services services) {
		this.services = services;
		this.dishSearch = new DishSearch(services);
		this.resourceSearch = new ResourceSearch(services);
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(1, 2));
		centerPanel.add(dishSearch);
		centerPanel.add(resourceSearch);
		
		JButton button = new JButton("Add dish");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Dish dish = services.getDishManager().createDish();
				if (dish != null) {
					DishEditFrame editFrame = new DishEditFrame(dish, services);
					editFrame.addComponentListener(new ComponentAdapter() {
						@Override
						public void componentHidden(ComponentEvent arg0) {
							dishSearch.doSearch();
						}
					});
					editFrame.setVisible(true);
				}
				else {
					Services.showError("Error: Could not create dish!");
				}
			}
		});
		leftPanel.add(button);
		
		button = new JButton("Edit dish");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Dish dish = dishSearch.getSelected();
				if (dish != null) {
					DishEditFrame editFrame = new DishEditFrame(dish, services);
					editFrame.addComponentListener(new ComponentAdapter() {
						@Override
						public void componentHidden(ComponentEvent arg0) {
							dishSearch.doSearch();
						}
					});
					editFrame.setVisible(true);
				}
				else {
					Services.showError("Select a dish first!");
				}
			}
		});
		leftPanel.add(button);
		
		button = new JButton("Remove dish");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Dish dish = dishSearch.getSelected();
				if (dish != null) {
					try {
						dish.remove();
						dishSearch.doSearch();
					}
					catch (SQLException e) {
						e.printStackTrace();
						Services.showError("Error: Could not remove dish!");
					}
				}
				else {
					Services.showError("Select a dish first!");
				}
			}
		});
		leftPanel.add(button);
		
		button = new JButton("Add resource");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ResourceEditFrame editFrame = new ResourceEditFrame(services);
				editFrame.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentHidden(ComponentEvent arg0) {
						resourceSearch.doSearch();
					}
				});
				editFrame.setVisible(true);
			}
		});
		rightPanel.add(button);
		
		button = new JButton("Edit resource");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Resource res = resourceSearch.getSelected();
				if (res != null) {
					ResourceEditFrame editFrame = new ResourceEditFrame(res);
					editFrame.addComponentListener(new ComponentAdapter() {
						@Override
						public void componentHidden(ComponentEvent arg0) {
							resourceSearch.doSearch();
						}
					});
					editFrame.setVisible(true);
				}
				else {
					Services.showError("Select a resource first!");
				}
			}
		});
		rightPanel.add(button);
		
		button = new JButton("Remove resource");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Resource res = resourceSearch.getSelected();
				if (res != null) {
					try {
						res.remove();
						resourceSearch.doSearch();
					}
					catch (SQLException e) {
						e.printStackTrace();
						Services.showError("Error: Could not remove resource!");
					}
				}
				else {
					Services.showError("Select a resource first!");
				}
			}
		});
		rightPanel.add(button);
		
		setLayout(new BorderLayout());
		add(leftPanel, BorderLayout.WEST);
		add(rightPanel, BorderLayout.EAST);
		add(centerPanel, BorderLayout.CENTER);
	}
	
}
