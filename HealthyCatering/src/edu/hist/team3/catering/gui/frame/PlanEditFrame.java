package edu.hist.team3.catering.gui.frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.hist.team3.catering.database.Dish;
import edu.hist.team3.catering.database.Plan;
import edu.hist.team3.catering.database.PlanDish;
import edu.hist.team3.catering.database.manager.Services;
import edu.hist.team3.catering.gui.panel.PlanDishSearch;
import edu.hist.team3.catering.gui.panel.PlanEditPanel;
import edu.hist.team3.catering.gui.panel.PropertyPanel;

public class PlanEditFrame extends JFrame {
	private PlanEditPanel planPanel;
	private PlanDishSearch dishSearch;
	private JFormattedTextField count;
	private JFormattedTextField discount;
	private JButton dishSelector;
	private Dish selectedDish;
	private DishSearchFrame dishSearchFrame;

	public PlanEditFrame(final Plan plan, final Services services) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				planPanel.apply();
			}
		});

		dishSearchFrame = new DishSearchFrame(services);
		dishSearchFrame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentHidden(ComponentEvent arg0) {
				selectedDish = dishSearchFrame.getSelected();
			}
		});

		planPanel = new PlanEditPanel(plan);
		dishSearch = new PlanDishSearch(plan);

		dishSearch.getResultList().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						PlanDish selected = dishSearch.getSelected();
						if (selected != null) {
							selectedDish = selected.getDish();
							count.setValue(selected.getCount());
							discount.setValue(selected.getDiscount());
						}
					}
				});

		JPanel editPanel = new JPanel();
		editPanel.setLayout(new GridLayout(6, 1));

		count = new JFormattedTextField();
		count.setValue(new Integer(0));
		editPanel.add(new PropertyPanel<JFormattedTextField>("Count: ", count));

		discount = new JFormattedTextField();
		discount.setValue(new Double(0.0));
		editPanel.add(new PropertyPanel<JFormattedTextField>("Discount: ", discount));

		dishSelector = new JButton("Select dish");
		dishSelector.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dishSearchFrame.setVisible(true);
			}
		});
		editPanel.add(dishSelector);

		JButton button = new JButton("Add");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				double discountValue;
				int countValue;
				discountValue = ((Number)discount.getValue()).doubleValue();
				countValue = ((Number)count.getValue()).intValue();
				if (selectedDish != null) {
					try {
						PlanDish pd = plan.getDishes().add(selectedDish);
						pd.setCount(countValue);
						pd.setDiscount(discountValue);
						pd.commit();
						dishSearch.doSearch();
					} catch (SQLException e) {
						// TODO Rollback?
						Services.showError("Could not save the changes to the database.");
						e.printStackTrace();
					}
				} else {
					Services.showError("No dishes selected!");
				}
			}
		});
		editPanel.add(button);

		button = new JButton("Save");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				double discountValue;
				int countValue;
				discountValue = ((Number)discount.getValue()).doubleValue();
				countValue = ((Number)count.getValue()).intValue();
				PlanDish dp = dishSearch.getSelected();
				if (dp != null && selectedDish != null) {
					try {
						plan.getDishes().remove(dp);
						dp = plan.getDishes().add(selectedDish);
						dp.setCount(countValue);
						dp.setDiscount(discountValue);
						dp.commit();
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
					try {
						plan.getDishes().remove(pd);
						dishSearch.doSearch();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					Services.showError("Select a dish first!");
				}
			}
		});
		editPanel.add(button);

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(planPanel, BorderLayout.WEST);
		contentPane.add(dishSearch, BorderLayout.CENTER);
		contentPane.add(editPanel, BorderLayout.EAST);

		add(new JScrollPane(contentPane));

		setTitle("Plan editor");
		setSize(600, 400);
		setAlwaysOnTop(true);
		setLocationRelativeTo(null);
	}
}