package edu.hist.team3.catering.gui.tabs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
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
	
	/**
	 * GUI for editing customer information and adding orders.
	 * Extends a JPanel to be used in a tabbed pane.
	 */
	public CustomerGUI() {
		setLayout(new BorderLayout());
	
		cManager = CustomerManager.getInstance();
		pManager = PlanManager.getInstance();
		
		Customer[] list = cManager.getCustomers();
		Plan[] pList = new Plan[0];
		customerList = new JList<Customer>(list);
		planList = new JList<Plan>();

		
		// Left panel with customer list and search bar
		JPanel leftPanel = new JPanel();
		JPanel leftInnerPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(900, 640));
		leftPanel.setLayout(new FlowLayout());
		customerScrollPane = new JScrollPane(customerList);
		customerScrollPane.setPreferredSize(new Dimension(600, 600));
		planScrollPane = new JScrollPane(planList);
		planScrollPane.setPreferredSize(new Dimension(200, 600));
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
				planList.setListData(pManager.getPlansFor(customerList.getSelectedValue().getId()));
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
		
		JButton addPlan = new JButton("Add order");
		addPlan.setPreferredSize(dim);
		addPlan.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addPlan();
			}
			
		});
		
		
		rightInnerPanel.add(refreshList);
		rightInnerPanel.add(Box.createRigidArea(dim));
		rightInnerPanel.add(addCustomer);
		rightInnerPanel.add(editCustomer);
		rightInnerPanel.add(addPlan);
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
			selectedCustomer = customerList.getSelectedValue();
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
		
	}

}
