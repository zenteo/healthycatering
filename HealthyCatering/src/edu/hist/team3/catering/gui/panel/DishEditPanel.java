package edu.hist.team3.catering.gui.panel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.hist.team3.catering.database.Dish;
import edu.hist.team3.catering.database.DishResource;
import edu.hist.team3.catering.database.Resource;
import edu.hist.team3.catering.database.manager.Services;
import edu.hist.team3.catering.gui.frame.ResourceSearchFrame;
/*
 * 	private String name;						// name					VARCHAR(64)	NOT NULL
 *	private String category;					// category				VARCHAR(64)
 *	private double healthiness;					// healthiness			DOUBLE
 *	private double price;						// price				DOUBLE		NOT NULL
 *	private double defaultDiscount;				// default_discount		DOUBLE		NOT NULL
 *	private double longtermDiscount;			// longterm_discount	DOUBLE		NOT NULL
 */

public class DishEditPanel extends JPanel {
	private Dish dish;
	private JTextField name;
	private JTextField category;
	private JFormattedTextField healthiness;
	private JFormattedTextField price;
	private JFormattedTextField defaultDiscount;
	private JFormattedTextField longtermDiscount;
	private DishResourceSearch resourceSearch;
	private JFormattedTextField amount;
	private Resource selected;
	
	public DishEditPanel(final Dish dish, final Services services) {
		this.dish = dish;
		this.resourceSearch = new DishResourceSearch(dish);
		this.resourceSearch.getResultList().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				DishResource dr = resourceSearch.getSelected();
				if (dr != null) {
					selected = dr.getResource();
					double val = selected.getAmount();
					if (val == 0.0) {
						val = 1.0;
					}
					val *= dr.getAmount();
					amount.setValue(val);
				}
			}
		});
		
		this.name = new JTextField();
		this.name.setText(dish.getName());
		this.category = new JTextField();
		this.category.setText(dish.getCategory());
		this.healthiness = new JFormattedTextField();
		this.healthiness.setValue(dish.getHealthiness() * 100.0);
		this.price = new JFormattedTextField();
		this.price.setValue(dish.getPrice());
		this.defaultDiscount = new JFormattedTextField();
		this.defaultDiscount.setValue(dish.getDefaultDiscount());
		this.longtermDiscount = new JFormattedTextField();
		this.longtermDiscount.setValue(dish.getLongtermDiscount());
		this.amount = new JFormattedTextField();
		this.amount.setValue(new Double(0.0));
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
		
		leftPanel.add(new PropertyPanel<JTextField>("Name:", name));
		leftPanel.add(new PropertyPanel<JTextField>("Category:", category));
		leftPanel.add(new PropertyPanel<JTextField>("Price:", price));
		leftPanel.add(new PropertyPanel<JTextField>("Default discount:", defaultDiscount));
		leftPanel.add(new PropertyPanel<JTextField>("Longterm discount:", longtermDiscount));
		leftPanel.add(new PropertyPanel<JTextField>("Healthiness:", healthiness));
		
		JButton button = new JButton("Compute healthiness");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				double sum = 0.0;
				int count = 0;
				Iterator<DishResource> it = dish.getResources().iterator();
				while (it.hasNext()) {
					sum += it.next().getDish().getHealthiness();
					count++;
				}
				sum *= 100.0;
				if (count != 0) {
					sum /= count;
				}
				healthiness.setValue(sum);
			}
		});
		leftPanel.add(button);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(4, 1));
		
		rightPanel.add(new PropertyPanel("Amount:", amount));
		
		button = new JButton("Select resource");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				final ResourceSearchFrame selectFrame = new ResourceSearchFrame(services);
				selectFrame.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentHidden(ComponentEvent arg0) {
						selected = selectFrame.getSelected();
					}
				});
				selectFrame.setVisible(true);
			}
		});
		rightPanel.add(button);
		
		button = new JButton("Add/Save");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (selected != null) {
					DishResource dr = dish.getResources().get(selected);
					if (dr == null) {
						try {
							dr = dish.getResources().add(selected);
						}
						catch (SQLException e) {
							e.printStackTrace();
							Services.showError("Error: Could not add resource!");
							return;
						}
					}
					double val = ((Number)amount.getValue()).doubleValue();
					if (selected.getAmount() != 0.0) {
						val /= selected.getAmount();
					}
					dr.setAmount(val);
					try {
						dr.commit();
						resourceSearch.doSearch();
					}
					catch (SQLException e) {
						e.printStackTrace();
						Services.showError("Error: Could not save changes!");
					}
				}
				else {
					Services.showError("Select a resource first!");
				}
			}
		});
		rightPanel.add(button);
		
		button = new JButton("Remove");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (selected != null) {
					try {
						dish.getResources().remove(selected);
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
		add(resourceSearch, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);
	}
	
	public void apply() throws SQLException {
		dish.setName(name.getText());
		dish.setCategory(category.getText());
		dish.setHealthiness(((Number)healthiness.getValue()).doubleValue() / 100.0);
		dish.setPrice(((Number)price.getValue()).doubleValue());
		dish.setDefaultDiscount(((Number)defaultDiscount.getValue()).doubleValue());
		dish.setLongtermDiscount(((Number)longtermDiscount.getValue()).doubleValue());
		dish.commit();
	}
}
