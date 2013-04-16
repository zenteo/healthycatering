package edu.hist.team3.catering.gui.tabs;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.Plan;
import edu.hist.team3.catering.database.PlanDish;
import edu.hist.team3.catering.database.managers.Services;
import edu.hist.team3.catering.gui.panel.CustomerPanel;
import edu.hist.team3.catering.gui.panel.CustomerPlanSearch;
import edu.hist.team3.catering.gui.panel.CustomerSearch;
import edu.hist.team3.catering.gui.panel.LabeledObject;
import edu.hist.team3.catering.gui.panel.PlanDishSearch;
import edu.hist.team3.catering.gui.panel.PlanPanel;
import edu.hist.team3.catering.gui.panel.PropertyPanel;

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
		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (customer == null) {
			customer = services.getCustomerManager().createCustomer();
			if (customer == null) {
				JOptionPane.showMessageDialog(null,
						"Error: Could not create customer!");
				return;
			}
		}
		customerPanel.apply(customer);
		try {
			customer.commit();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Error: Could not change customer!");
		}
		this.setVisible(false);
	}
}

class PlanFrame extends JFrame implements ComponentListener {
	private PlanPanel planPanel;
	private PlanDishSearch dishSearch;
	private JTextField count;
	private JTextField discount;
	private JButton dishSelector;

	public PlanFrame(Plan plan) {
		super("Plan editor");
		
		planPanel = new PlanPanel(plan);
		dishSearch = new PlanDishSearch(plan);
		
		JPanel editPanel = new JPanel();
		editPanel.setLayout(new GridLayout(6, 1));
		
		count = new JTextField();
		editPanel.add(new PropertyPanel("Count: ", count));
		
		discount = new JTextField();
		editPanel.add(new PropertyPanel("Discount: ", discount));
		
		dishSelector = new JButton("Select dish");
		dishSelector.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		editPanel.add(dishSelector);
		
		JButton button = new JButton("Add");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		editPanel.add(button);
		
		button = new JButton("Save");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		editPanel.add(button);
		
		button = new JButton("Remove");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PlanDish pd = dishSearch.getSelected();
				if (pd != null) {
					try {
						pd.remove();
					}
					catch (SQLException e) {
						e.printStackTrace();
					}
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
		
		setSize(600, 400);
		setLocationRelativeTo(null);
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		planPanel.apply();
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
	}
}

@SuppressWarnings("serial")
public class CustomerGUI extends JPanel {
	private final Services services;
	private CustomerSearch customerSearch;
	private CustomerPlanSearch planSearch;
	private JList<LabeledObject<Plan>> planList;

	/**
	 * GUI for editing customer information and adding orders. Extends a JPanel
	 * to be used in a tabbed pane.
	 */
	public CustomerGUI(final Services services) {
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
				CustomerFrame frame = new CustomerFrame(customerSearch
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
					PlanFrame pf;
					pf = new PlanFrame(services.getPlanManager().createPlan(customer));
					pf.setVisible(true);
					planSearch.doSearch();
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
							PlanFrame pf = new PlanFrame(plan);
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
