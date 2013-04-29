package edu.hist.team3.catering.gui.panel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Date;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.hist.team3.catering.database.Dish;
import edu.hist.team3.catering.database.Plan;
import edu.hist.team3.catering.database.PlanDish;
import edu.hist.team3.catering.database.manager.Services;
import edu.hist.team3.catering.gui.frame.DishSearchFrame;

@SuppressWarnings("serial")
public class PlanEditPanel extends JPanel {
	private Plan plan;
	private JTextField startDate;
	private JTextField endDate;
	private JCheckBox mondays;
	private JCheckBox tuesdays;
	private JCheckBox wednesdays;
	private JCheckBox thurdays;
	private JCheckBox fridays;
	private JCheckBox saturdays;
	private JCheckBox sundays;
	private PlanDishSearch dishSearch;
	private JFormattedTextField count;
	private JFormattedTextField discount;
	private JButton dishSelector;
	private Dish selectedDish;
	private DishSearchFrame dishSearchFrame;
	
	/**
	 * Creates a new PlanEditPanel.
	 * @param plan
	 * @param services
	 */
	public PlanEditPanel(final Plan plan, Services services) {
		this.plan = plan;
		startDate = new JTextField();
		endDate = new JTextField();
		mondays = new JCheckBox("Mondays");
		tuesdays = new JCheckBox("Tuesdays");
		wednesdays = new JCheckBox("Wednesdays");
		thurdays = new JCheckBox("Thurdays");
		fridays = new JCheckBox("Fridays");
		saturdays = new JCheckBox("Saturdays");
		sundays = new JCheckBox("Sundays");
		count = new JFormattedTextField();
		discount = new JFormattedTextField();
		
		
		JPanel options = new JPanel();
		options.setLayout(new GridLayout(8, 1));
		JPanel gridOptions = new JPanel();
		gridOptions.setLayout(new GridLayout(2, 2));
		gridOptions.add(new JLabel("Start date:"));
		gridOptions.add(startDate);
		gridOptions.add(new JLabel("End date:"));
		gridOptions.add(endDate);
		options.add(gridOptions);
		options.add(mondays);
		options.add(tuesdays);
		options.add(wednesdays);
		options.add(thurdays);
		options.add(fridays);
		options.add(saturdays);
		options.add(sundays);
		
		dishSearchFrame = new DishSearchFrame(services);
		dishSearchFrame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentHidden(ComponentEvent arg0) {
				selectedDish = dishSearchFrame.getSelected();
				if (selectedDish != null) {
					if (endDate.getText().equals("")) {
						discount.setValue(selectedDish.getLongtermDiscount() * 100.0);
					}
					else {
						discount.setValue(selectedDish.getDefaultDiscount() * 100.0);
					}
				}
			}
		});

		dishSearch = new PlanDishSearch(plan);

		dishSearch.getResultList().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						PlanDish selected = dishSearch.getSelected();
						if (selected != null) {
							selectedDish = selected.getDish();
							count.setValue(selected.getCount());
							discount.setValue(selected.getDiscount() * 100.0);
						}
					}
				});

		JPanel editPanel = new JPanel();
		editPanel.setLayout(new GridLayout(4, 1));

		gridOptions = new JPanel();
		gridOptions.setLayout(new GridLayout(2, 2));
		
		count.setValue(new Integer(1));
		gridOptions.add(new JLabel("Count:"));
		gridOptions.add(count);

		discount.setValue(new Double(0.0));
		gridOptions.add(new JLabel("Discount:"));
		gridOptions.add(discount);

		editPanel.add(gridOptions);
		
		JButton dishSelector = new JButton("Select dish");
		dishSelector.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dishSearchFrame.setVisible(true);
			}
		});
		editPanel.add(dishSelector);

		JButton button = new JButton("Add/Save");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				double discountValue;
				int countValue;
				discountValue = ((Number)discount.getValue()).doubleValue();
				countValue = ((Number)count.getValue()).intValue();
				if (selectedDish != null) {
					try {
						PlanDish pd = plan.getDishes().get(selectedDish);
						if (pd == null) {
							pd = plan.getDishes().add(selectedDish);
						}
						pd.setCount(((Number)count.getValue()).intValue());
						pd.setDiscount(((Number)discount.getValue()).doubleValue() / 100.0);
						pd.commit();
						dishSearch.doSearch();
					} catch (SQLException e) {
						// TODO Rollback?
						Services.showError("Could not save the changes to the database.");
						e.printStackTrace();
					}
				} else {
					Services.showError("Select a dish first!");
				}
			}
		});
		editPanel.add(button);


		button = new JButton("Remove");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PlanDish pd = dishSearch.getSelected();
				if (pd != null) {
					if (Services.choiceMessage("Do you really want to remove the dish?", "Are you sure?")) {
						try {
							plan.getDishes().remove(pd);
							dishSearch.doSearch();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				} else {
					Services.showError("Select a dish first!");
				}
			}
		});
		editPanel.add(button);

		JPanel optionsHolder = new JPanel();
		optionsHolder.setLayout(new BorderLayout());
		optionsHolder.add(options, BorderLayout.NORTH);
		
		JPanel editHolder = new JPanel();
		editHolder.setLayout(new BorderLayout());
		editHolder.add(editPanel, BorderLayout.NORTH);
		
		setLayout(new BorderLayout());
		add(optionsHolder, BorderLayout.WEST);
		add(dishSearch, BorderLayout.CENTER);
		add(editHolder, BorderLayout.EAST);
		
		fillInfo();
	}
	
	/**
	 * Fills the panel with information.
	 */
	public void fillInfo() {
		startDate.setText(plan.getStartDate().toString());
		if (plan.getEndDate() != null) {
			endDate.setText(plan.getEndDate().toString());
		}
		mondays.setSelected(plan.isDeliveredOn(Plan.DAY_MONDAY));
		tuesdays.setSelected(plan.isDeliveredOn(Plan.DAY_TUESDAY));
		wednesdays.setSelected(plan.isDeliveredOn(Plan.DAY_WEDNESDAY));
		thurdays.setSelected(plan.isDeliveredOn(Plan.DAY_THURSDAY));
		fridays.setSelected(plan.isDeliveredOn(Plan.DAY_FRIDAY));
		saturdays.setSelected(plan.isDeliveredOn(Plan.DAY_SATURDAY));
		sundays.setSelected(plan.isDeliveredOn(Plan.DAY_SUNDAY));
	}
	
	/**
	 * Applies changes.
	 */
	public void apply() {
		try {
			Date sDate = Date.valueOf(startDate.getText());
			plan.setStartDate(sDate);
		}
		catch(IllegalArgumentException ex) {
		}
		try {
			Date eDate = Date.valueOf(endDate.getText());
			plan.setEndDate(eDate);
		}
		catch(IllegalArgumentException ex) {
		}
		plan.setDaysOfWeek(Plan.DAY_NONE);
		if (mondays.isSelected())
			plan.setDeliveredOn(Plan.DAY_MONDAY);
		if (tuesdays.isSelected())
			plan.setDeliveredOn(Plan.DAY_TUESDAY);
		if (wednesdays.isSelected())
			plan.setDeliveredOn(Plan.DAY_WEDNESDAY);
		if (thurdays.isSelected())
			plan.setDeliveredOn(Plan.DAY_THURSDAY);
		if (fridays.isSelected())
			plan.setDeliveredOn(Plan.DAY_FRIDAY);
		if (saturdays.isSelected())
			plan.setDeliveredOn(Plan.DAY_SATURDAY);
		if (sundays.isSelected())
			plan.setDeliveredOn(Plan.DAY_SUNDAY);
		try {
			plan.commit();
		}
		catch (SQLException e) {
			Services.showError("Could not save the changes to the database.");
			e.printStackTrace();
		}
	}
}
