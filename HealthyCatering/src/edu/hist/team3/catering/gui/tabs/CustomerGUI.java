package edu.hist.team3.catering.gui.tabs;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.Plan;
import edu.hist.team3.catering.database.managers.Services;
import edu.hist.team3.catering.gui.panel.CustomerPanel;
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

class CustomerFrame extends JFrame implements ActionListener {
	private Services services;
	private CustomerPanel customerPanel;
	private JButton button;
	private Customer customer = null;
	
	public CustomerFrame(Services services) {
		super("Add customer");
		this.services = services;
		init("Create");
	}
	
	public CustomerFrame(Customer customer) {
		super("Edit customer");
		this.customer = customer;
		init("Save");
		customerPanel.fillInfo(customer);
	}
	
	private void init(String buttonText) {
		this.setLayout(new BorderLayout());
		customerPanel = new CustomerPanel();
		button = new JButton(buttonText);
		button.addActionListener(this);
		add(customerPanel, BorderLayout.CENTER);
		add(button, BorderLayout.SOUTH);
		pack();
		setSize(300, 200);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (customer == null) {
			customer = services.getCustomerManager().createCustomer();
			if (customer == null) {
				// TODO Show error!
			}
		}
		customerPanel.apply(customer);
		try {
			customer.commit();
		}
		catch (SQLException e) {
			// TODO Show error!
			e.printStackTrace();
		}
		this.setVisible(false);
	}
}

@SuppressWarnings("serial")
public class CustomerGUI extends JPanel {
	private final Services services;
	private CustomerSearch customerSearch;
	private JList<LabeledObject<Plan>> planList;
	
	/**
	 * GUI for editing customer information and adding orders.
	 * Extends a JPanel to be used in a tabbed pane.
	 */
	public CustomerGUI(final Services services) {
		this.services = services;
		
		JPanel customerPlanPanel = new JPanel();
		customerPlanPanel.setLayout(new GridLayout(1, 2));
		customerSearch = new CustomerSearch(services);
		planList = new JList<LabeledObject<Plan>>();
		planList.setModel(new DefaultListModel<LabeledObject<Plan>>());
		customerSearch.getResultList().addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				fillPlans();
			}
		});
		customerPlanPanel.add(customerSearch);
		customerPlanPanel.add(planList);
		
		JPanel editPanel = new JPanel();
		editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.PAGE_AXIS));
		
		JButton button = new JButton("Add customer");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CustomerFrame frame = new CustomerFrame(services);
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
				CustomerFrame frame = new CustomerFrame(customerSearch.getSelected());
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
					}
					catch (SQLException e) {
						// TODO Throw error!
						e.printStackTrace();
					}
					customerSearch.doSearch();
				}
			}
		});
		editPanel.add(button);
		
		button = new JButton("Add plan");
		editPanel.add(button);
		
		button = new JButton("Edit plan");
		editPanel.add(button);
		
		button = new JButton("Remove plan");
		editPanel.add(button);
		
		setLayout(new BorderLayout());
		add(customerPlanPanel, BorderLayout.CENTER);
		add(editPanel, BorderLayout.EAST);
	}
	
	private void fillPlans() {
		Customer selected = customerSearch.getSelected();
		if (selected != null) {
			Iterator<Plan> it = selected.getPlans().iterator();
			DefaultListModel<LabeledObject<Plan>> model = (DefaultListModel<LabeledObject<Plan>>)planList.getModel();
			model.clear();
			while (it.hasNext()) {
				Plan plan = it.next();
				model.addElement(new LabeledObject<Plan>(plan.getStartDate().toString(), plan));
			}
		}
	}
}
