package edu.hist.team3.catering.gui.tabs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.Plan;
import edu.hist.team3.catering.database.managers.CustomerManager;
import edu.hist.team3.catering.database.managers.PlanManager;
import edu.hist.team3.catering.database.managers.Services;

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
public class CustomerGUI extends JPanel {
	private JScrollPane customerScrollPane;
	private JScrollPane planScrollPane;
	private JList<Customer> customerList;
	private JList<Plan> planList;
	private CustomerManager cManager;
	private PlanManager pManager;
	private JTextField searchBar;
	
	// Textfield for editing the customer:
	private Customer selectedCustomer;
	private JTextField firstName;
	private JTextField lastName;
	private JTextField phone;
	private JTextField address;
	
	// Textfields for editing a plan
	private Plan selectedPlan;
	private JCheckBox deliverOnMonday;
	private JCheckBox deliverOnTuesday;
	private JCheckBox deliverOnWednesday;
	private JCheckBox deliverOnThursday;
	private JCheckBox deliverOnFriday;
	private JCheckBox deliverOnSaturday;
	private JCheckBox deliverOnSunday;
	private JTextField planStartDate;
	private JTextField planEndDate;

	private Services services;
	
	/**
	 * GUI for editing customer information and adding orders.
	 * Extends a JPanel to be used in a tabbed pane.
	 */
	public CustomerGUI(Services services) {
		this.services = services;
		setLayout(new BorderLayout());
	
		cManager = services.getCustomerManager();
		pManager = services.getPlanManager();
		
		Customer[] list = cManager.getCustomers();
		customerList = new JList<Customer>(list);
		planList = new JList<Plan>();

		
		// Left panel with customer list and search bar
		JPanel leftPanel = new JPanel();
		JPanel leftInnerPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(1000, 640));
		leftPanel.setLayout(new FlowLayout());
		customerScrollPane = new JScrollPane(customerList);
		customerScrollPane.setPreferredSize(new Dimension(600, 600));
		planScrollPane = new JScrollPane(planList);
		planScrollPane.setPreferredSize(new Dimension(400, 600));
		searchBar = new JTextField("Search");
		searchBar.setToolTipText("Search through customers");
		searchBar.setPreferredSize(new Dimension(300, 30));
		leftInnerPanel.add(customerScrollPane);
		leftInnerPanel.add(planScrollPane);
		leftPanel.add(searchBar);
		leftPanel.add(leftInnerPanel);
		
		customerList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				selectedCustomer = customerList.getSelectedValue();
				if(selectedCustomer != null)
					planList.setListData(pManager.getPlansFor(selectedCustomer.getId()));
				else
					planList.setListData(new Plan[0]);
			}
			
		});
		
		planList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				selectedPlan = planList.getSelectedValue();
			}
			
		});
		
		// Center panel for buttons
		Dimension dim = new Dimension(150, 70);
		JPanel rightPanel = new JPanel();
		JPanel rightInnerPanel = new JPanel();
		rightPanel.setLayout(new FlowLayout());
		rightInnerPanel.setLayout(new FlowLayout());
		rightPanel.setPreferredSize(new Dimension(150, 640));
		rightInnerPanel.setPreferredSize(new Dimension(150, 640));
		
		JButton refreshList = new JButton("Refresh list");
		refreshList.setPreferredSize(dim);
		refreshList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				refreshList();
			}
			
		});
		
		JButton editCustomer = new JButton("Edit customer");
		editCustomer.setPreferredSize(dim);
		editCustomer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editCustomer();
			}
			
		});
		
		JButton addCustomer = new JButton("New customer");
		addCustomer.setPreferredSize(dim);
		addCustomer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addCustomer();
			}
			
		});
		
		JButton removeCustomer = new JButton("Remove customer");
		removeCustomer.setPreferredSize(dim);
		removeCustomer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				removeCustomer();
			}
			
		});
		
		JButton addPlan = new JButton("Add plan");
		addPlan.setPreferredSize(dim);
		addPlan.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addPlan();
			}
			
		});
		
		JButton editPlan = new JButton("Edit plan");
		editPlan.setPreferredSize(dim);
		editPlan.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editPlan();
			}
			
		});
		
		
		rightInnerPanel.add(refreshList);
		rightInnerPanel.add(Box.createRigidArea(dim));
		rightInnerPanel.add(addCustomer);
		rightInnerPanel.add(editCustomer);
		rightInnerPanel.add(addPlan);
		rightInnerPanel.add(editPlan);
		rightInnerPanel.add(Box.createRigidArea(dim));
		rightInnerPanel.add(removeCustomer);

		rightPanel.add(rightInnerPanel);
		
		add(leftPanel, BorderLayout.WEST);
		add(rightPanel, BorderLayout.CENTER);
	}
	
	public void refreshList() {
		customerList.setListData(cManager.getCustomers());
	}
	
	private void editCustomer() {
		
		if (customerList.getSelectedValue() != null) {
			final JFrame editCustomerWindow = new JFrame("Edit customer");
			editCustomerWindow.setLayout(new FlowLayout());
			editCustomerWindow.setSize(new Dimension(500, 500));
			editCustomerWindow.setPreferredSize(new Dimension(500, 500));
			editCustomerWindow.setLocationRelativeTo(null);
			
			Dimension dim = new Dimension(300, 50);
			
			JPanel firstNamePanel = new JPanel();
			firstNamePanel.setPreferredSize(dim);
			firstNamePanel.add(new JLabel("First name: "));
			firstName = new JTextField();
			firstName.setPreferredSize(new Dimension(160, 30));
			firstName.setText(selectedCustomer.getFirstName());
			firstNamePanel.add(firstName);
			
			JPanel lastNamePanel = new JPanel();
			lastNamePanel.setPreferredSize(dim);
			lastNamePanel.add(new JLabel("Last name: "));
			lastName = new JTextField();
			lastName.setPreferredSize(new Dimension(160, 30));
			lastName.setText(selectedCustomer.getLastName());
			lastNamePanel.add(lastName);
			
			JPanel addressPanel = new JPanel();
			addressPanel.setPreferredSize(dim);
			addressPanel.add(new JLabel("Address: "));
			address = new JTextField();
			address.setPreferredSize(new Dimension(160, 30));
			address.setText(selectedCustomer.getAddress());
			addressPanel.add(address);
			
			JPanel phonePanel = new JPanel();
			phonePanel.setPreferredSize(dim);
			phonePanel.add(new JLabel("Phone number: "));
			phone = new JTextField();
			phone.setPreferredSize(new Dimension(160, 30));
			phone.setText(selectedCustomer.getPhone());
			phonePanel.add(phone);

			JButton commit = new JButton("Commit");
			commit.setPreferredSize(new Dimension(300, 50));
			commit.addActionListener(new ActionListener() {
						
				@Override
				public void actionPerformed(ActionEvent arg0) {
					commitEditCustomer(editCustomerWindow);
				}
				
			});
			
			editCustomerWindow.add(firstNamePanel);
			editCustomerWindow.add(lastNamePanel);
			editCustomerWindow.add(addressPanel);
			editCustomerWindow.add(phonePanel);
			editCustomerWindow.add(commit);
			
			editCustomerWindow.setVisible(true);
		}
	}
	
	private void commitEditCustomer(JFrame window) {
		Object[] options = {"Yes", "Cancel"};
		
		int choice = JOptionPane.showOptionDialog(window, 
				"Do you want to save changes?", 
				"Warning", 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.WARNING_MESSAGE, 
				null, 
				options, 
				options[1]);
		
		if(choice == 0) {
			if (!cManager.editCustomer(selectedCustomer.getId(),
					firstName.getText(),
					lastName.getText(),
					phone.getText(),
					address.getText()
					)) {
				JOptionPane.showMessageDialog(window, "Was unable to save changes");
			}
			else
				window.dispose();
		}
	}
	
	private void addCustomer() {
		final JFrame window = new JFrame("Add new customer");
		window.setSize(new Dimension(500, 500));
		window.setLocationRelativeTo(null);
		window.setLayout(new FlowLayout());
		
		Dimension panelSize = new Dimension(500, 70);
		Dimension dim = new Dimension(300, 30);
		
		JPanel namePanel = new JPanel();
		namePanel.setPreferredSize(panelSize);
		namePanel.add(new JLabel("First name: "));
		firstName = new JTextField("");
		firstName.setPreferredSize(dim);
		namePanel.add(firstName);

		JPanel lastNamePanel = new JPanel();
		lastNamePanel.setPreferredSize(panelSize);
		lastNamePanel.add(new JLabel("Last name: "));
		lastName = new JTextField("");
		lastName.setPreferredSize(dim);
		lastNamePanel.add(lastName);

		JPanel addressPanel = new JPanel();
		addressPanel.setPreferredSize(panelSize);
		addressPanel.add(new JLabel("Address: "));
		address = new JTextField("");
		address.setPreferredSize(dim);
		addressPanel.add(address);

		JPanel phonePanel = new JPanel();
		phonePanel.setPreferredSize(panelSize);
		phonePanel.add(new JLabel("Phone number: "));
		phone = new JTextField("");
		phone.setPreferredSize(dim);
		phonePanel.add(phone);
		
		JButton commit = new JButton("Commit");
		commit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				commitNewCustomer(window);
			}
			
		});
		
		window.add(namePanel);
		window.add(lastNamePanel);
		window.add(addressPanel);
		window.add(phonePanel);
		window.add(commit);
		
		window.setVisible(true);
	}
	
	private void commitNewCustomer(JFrame window) {
		Object[] options = {"Yes", "Cancel"};
		
		int choice = JOptionPane.showOptionDialog(window, 
				"Do you want to save changes?", 
				"Warning", 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.WARNING_MESSAGE, 
				null, 
				options, 
				options[1]);
		
		if(choice == 0) {
			if(!cManager.addCustomer(firstName.getText(), 
					lastName.getText(), 
					phone.getText(), 
					address.getText())) {
				JOptionPane.showMessageDialog(window, "The new settings could not be saved");
			}
			else {
				window.dispose();
			}
		}
	}
	
	private void removeCustomer() {
		
	}
	
	private void addPlan() {
		
		if (selectedCustomer != null) {
			final JFrame window = new JFrame("New plan for " + selectedCustomer.getFirstName() + " " + selectedCustomer.getLastName());
			window.setSize(new Dimension(500, 500));
			window.setLayout(new FlowLayout(FlowLayout.CENTER));
			window.setLocationRelativeTo(null);
			
			JPanel checkboxPanel = new JPanel();
			checkboxPanel.setLayout(new FlowLayout(FlowLayout.LEFT));			
			checkboxPanel.setPreferredSize(new Dimension(100, 300));
			deliverOnMonday = new JCheckBox();
			deliverOnTuesday = new JCheckBox();
			deliverOnWednesday = new JCheckBox();
			deliverOnThursday = new JCheckBox();
			deliverOnFriday = new JCheckBox();
			deliverOnSaturday = new JCheckBox();
			deliverOnSunday = new JCheckBox();
			deliverOnMonday.add(new JLabel("     Monday"));
			deliverOnTuesday.add(new JLabel("     Tuesday"));
			deliverOnWednesday.add(new JLabel("     Wednesday"));
			deliverOnThursday.add(new JLabel("     Thursday"));
			deliverOnFriday.add(new JLabel("     Friday"));
			deliverOnSaturday.add(new JLabel("     Saturday"));
			deliverOnSunday.add(new JLabel("     Sunday"));
			checkboxPanel.add(new JLabel("Deliver on: "));
			checkboxPanel.add(deliverOnMonday);
			checkboxPanel.add(deliverOnTuesday);
			checkboxPanel.add(deliverOnWednesday);
			checkboxPanel.add(deliverOnThursday);
			checkboxPanel.add(deliverOnFriday);
			checkboxPanel.add(deliverOnSaturday);
			checkboxPanel.add(deliverOnSunday);
			window.add(checkboxPanel);
			
			JPanel datePanel = new JPanel();
			datePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			datePanel.setPreferredSize(new Dimension(100, 300));
			planStartDate = new JTextField("2013-12-31");
			planEndDate = new JTextField("2013-12-31");
			planStartDate.setPreferredSize(new Dimension(90, 30));
			planEndDate.setPreferredSize(new Dimension(90, 30));
			datePanel.add(new JLabel("Start date:"));
			datePanel.add(planStartDate);
			datePanel.add(new JLabel("End date:"));
			datePanel.add(planEndDate);
			window.add(datePanel);
			
			JButton commit = new JButton("Commit");
			commit.setPreferredSize(new Dimension(150, 70));
			commit.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					commitNewPlan(window);
				}
				
			});
			window.add(commit);
			
			window.setVisible(true);
		}
	}
	
	private void editPlan() {
		if (selectedCustomer != null && selectedPlan != null) {
			final JFrame window = new JFrame("Plan for " + selectedCustomer.getFirstName() + " " + selectedCustomer.getLastName());
			window.setSize(new Dimension(500, 500));
			window.setLayout(new FlowLayout(FlowLayout.CENTER));
			window.setLocationRelativeTo(null);
			
			JPanel checkboxPanel = new JPanel();
			checkboxPanel.setLayout(new FlowLayout(FlowLayout.LEFT));			
			checkboxPanel.setPreferredSize(new Dimension(100, 300));
			deliverOnMonday = new JCheckBox();
			deliverOnMonday.setSelected(selectedPlan.isDeliveredOn(Plan.DAY_MONDAY));
			deliverOnTuesday = new JCheckBox();
			deliverOnTuesday.setSelected(selectedPlan.isDeliveredOn(Plan.DAY_TUESDAY));
			deliverOnWednesday = new JCheckBox();
			deliverOnWednesday.setSelected(selectedPlan.isDeliveredOn(Plan.DAY_WEDNESDAY));
			deliverOnThursday = new JCheckBox();
			deliverOnThursday.setSelected(selectedPlan.isDeliveredOn(Plan.DAY_THURSDAY));
			deliverOnFriday = new JCheckBox();
			deliverOnFriday.setSelected(selectedPlan.isDeliveredOn(Plan.DAY_FRIDAY));
			deliverOnSaturday = new JCheckBox();
			deliverOnSaturday.setSelected(selectedPlan.isDeliveredOn(Plan.DAY_SATURDAY));
			deliverOnSunday = new JCheckBox();
			deliverOnSunday.setSelected(selectedPlan.isDeliveredOn(Plan.DAY_SUNDAY));
			
			deliverOnMonday.add(new JLabel("     Monday"));
			deliverOnTuesday.add(new JLabel("     Tuesday"));
			deliverOnWednesday.add(new JLabel("     Wednesday"));
			deliverOnThursday.add(new JLabel("     Thursday"));
			deliverOnFriday.add(new JLabel("     Friday"));
			deliverOnSaturday.add(new JLabel("     Saturday"));
			deliverOnSunday.add(new JLabel("     Sunday"));
			checkboxPanel.add(new JLabel("Deliver on: "));
			checkboxPanel.add(deliverOnMonday);
			checkboxPanel.add(deliverOnTuesday);
			checkboxPanel.add(deliverOnWednesday);
			checkboxPanel.add(deliverOnThursday);
			checkboxPanel.add(deliverOnFriday);
			checkboxPanel.add(deliverOnSaturday);
			checkboxPanel.add(deliverOnSunday);
			window.add(checkboxPanel);
			
			JPanel datePanel = new JPanel();
			datePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			datePanel.setPreferredSize(new Dimension(100, 300));
			planStartDate = new JTextField(selectedPlan.getStartDate().toString());
			planEndDate = new JTextField(selectedPlan.getEndDate().toString());
			planStartDate.setPreferredSize(new Dimension(90, 30));
			planEndDate.setPreferredSize(new Dimension(90, 30));
			datePanel.add(new JLabel("Start date:"));
			datePanel.add(planStartDate);
			datePanel.add(new JLabel("End date:"));
			datePanel.add(planEndDate);
			window.add(datePanel);
			
			JButton commit = new JButton("Commit");
			commit.setPreferredSize(new Dimension(150, 70));
			commit.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					commitEditedPlan(window);
				}
				
			});
			window.add(commit);
			
			window.setVisible(true);
		}
	}
	
	private void commitNewPlan(JFrame window) {
		int deliverOnDays = 0;
		if(deliverOnMonday.isSelected())
			deliverOnDays = deliverOnDays | Plan.DAY_MONDAY;
		if(deliverOnTuesday.isSelected())
			deliverOnDays = deliverOnDays | Plan.DAY_TUESDAY;
		if(deliverOnWednesday.isSelected())
			deliverOnDays = deliverOnDays | Plan.DAY_WEDNESDAY;
		if(deliverOnThursday.isSelected())
			deliverOnDays = deliverOnDays | Plan.DAY_THURSDAY;
		if(deliverOnFriday.isSelected())
			deliverOnDays = deliverOnDays | Plan.DAY_FRIDAY;
		if(deliverOnSaturday.isSelected())
			deliverOnDays = deliverOnDays | Plan.DAY_SATURDAY;
		if(deliverOnSunday.isSelected())
			deliverOnDays = deliverOnDays | Plan.DAY_SUNDAY;
		
		if(!pManager.createPlan(selectedCustomer.getId(), deliverOnDays, planStartDate.getText(), planEndDate.getText(), 0, 0))
			JOptionPane.showMessageDialog(window, "Unable to add new plan");
	}
	
	private void commitEditedPlan(JFrame window) {
		int deliverOnDays = 0;
		if(deliverOnMonday.isSelected())
			deliverOnDays = deliverOnDays | Plan.DAY_MONDAY;
		if(deliverOnTuesday.isSelected())
			deliverOnDays = deliverOnDays | Plan.DAY_TUESDAY;
		if(deliverOnWednesday.isSelected())
			deliverOnDays = deliverOnDays | Plan.DAY_WEDNESDAY;
		if(deliverOnThursday.isSelected())
			deliverOnDays = deliverOnDays | Plan.DAY_THURSDAY;
		if(deliverOnFriday.isSelected())
			deliverOnDays = deliverOnDays | Plan.DAY_FRIDAY;
		if(deliverOnSaturday.isSelected())
			deliverOnDays = deliverOnDays | Plan.DAY_SATURDAY;
		if(deliverOnSunday.isSelected())
			deliverOnDays = deliverOnDays | Plan.DAY_SUNDAY;
		
		selectedPlan.setDeliveredOn(deliverOnDays);
		selectedPlan.setStartDate(Date.valueOf(planStartDate.getText()));
		selectedPlan.setEndDate(Date.valueOf(planEndDate.getText()));
		try {
			selectedPlan.commit();
		}
		catch (SQLException e) {
			JOptionPane.showMessageDialog(window, "Unable to save changes");
		}
	}
	
	private void editDishes() {
		
	}

}
