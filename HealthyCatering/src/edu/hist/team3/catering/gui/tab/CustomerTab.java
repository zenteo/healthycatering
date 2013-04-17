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
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.Plan;
import edu.hist.team3.catering.database.managers.Services;
import edu.hist.team3.catering.gui.frame.CustomerEditFrame;
import edu.hist.team3.catering.gui.frame.PlanEditFrame;
import edu.hist.team3.catering.gui.panel.CustomerPlanSearch;
import edu.hist.team3.catering.gui.panel.CustomerSearch;
import edu.hist.team3.catering.gui.panel.LabeledObject;

/*
 * Customer GUI
 --
 --
 + findPlans()
 + addPlan()
 + editPlan()
 + removePlan()
 + findCostumers()
 + addCostumer()
 + editCostumer()
 + removeCostumer()
 */

@SuppressWarnings("serial")
public class CustomerTab extends JPanel {
	private final Services services;
	private CustomerSearch customerSearch;
	private CustomerPlanSearch planSearch;
	private JList<LabeledObject<Plan>> planList;

	/**
	 * GUI for editing customer information and adding orders. Extends a JPanel
	 * to be used in a tabbed pane.
	 */
	public CustomerTab(final Services services) {
		this.services = services;

		JPanel customerPlanPanel = new JPanel();
		customerPlanPanel.setLayout(new GridLayout(1, 2));
		customerSearch = new CustomerSearch(services);
		planSearch = new CustomerPlanSearch();

		customerSearch.getResultList().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						planSearch.setCustomer(customerSearch.getSelected());
					}
				});

		customerPlanPanel.add(customerSearch);
		customerPlanPanel.add(planSearch);

		JPanel editPanel = new JPanel();
		editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.PAGE_AXIS));

		JButton button = new JButton("Add customer");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CustomerEditFrame frame = new CustomerEditFrame(services);
				frame.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentHidden(ComponentEvent arg0) {
						customerSearch.doSearch();
					}
				});
				frame.setVisible(true);
			}
		});
		editPanel.add(button);

		button = new JButton("Edit customer");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CustomerEditFrame frame = new CustomerEditFrame(customerSearch
						.getSelected());
				frame.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentHidden(ComponentEvent arg0) {
						customerSearch.doSearch();
					}
				});
				frame.setVisible(true);
				customerSearch.doSearch();
			}
		});
		editPanel.add(button);

		button = new JButton("Remove customer");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Customer customer = customerSearch.getSelected();
				if (customer != null) {
					try {
						customer.remove();
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null,
								"Error: Could not remove customer!");
						e.printStackTrace();
					}
					customerSearch.doSearch();
				}
			}
		});
		editPanel.add(button);

		button = new JButton("Add plan");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Customer customer = customerSearch.getSelected();
				if (customer != null) {
					PlanEditFrame pf;
					pf = new PlanEditFrame(services.getPlanManager().createPlan(customer), services);
					pf.addComponentListener(new ComponentAdapter() {
						@Override
						public void componentHidden(ComponentEvent arg0) {
							planSearch.doSearch();
						}
					});
					pf.setVisible(true);
				}
			}
		});
		editPanel.add(button);

		button = new JButton("Edit plan");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Customer customer = customerSearch.getSelected();
				if (customer != null) {
					Plan plan = planSearch.getSelected();
					if (plan != null) {
						plan = services.getPlanManager().editPlan(plan);
						if (plan != null) {
							PlanEditFrame pf = new PlanEditFrame(plan, services);
							pf.addComponentListener(new ComponentAdapter() {
								@Override
								public void componentHidden(ComponentEvent arg0) {
									planSearch.doSearch();
								}
							});
							pf.setVisible(true);
							planSearch.doSearch();
						}
					}
				}
			}
		});
		editPanel.add(button);

		button = new JButton("Remove plan");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Customer customer = customerSearch.getSelected();
				if (customer != null) {
					Plan plan = planSearch.getSelected();
					if (plan != null) {
						services.getPlanManager().removePlan(plan);
						planSearch.doSearch();
					}
				}
			}
		});
		editPanel.add(button);

		setLayout(new BorderLayout());
		add(customerPlanPanel, BorderLayout.CENTER);
		add(editPanel, BorderLayout.EAST);
	}
}
