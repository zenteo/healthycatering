package edu.hist.team3.catering.gui.tabs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.managers.CustomerManager;

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
	private JList<Customer> customerList;
	private CustomerManager cManager;
	private JTextField searchBar;
	
	// Textfield for editing the customer:
	private Customer selectedCustomer;
	private JTextField firstName;
	private JTextField lastName;
	private JTextField phone;
	private JTextField address;
	
	public CustomerGUI() {
		setLayout(new BorderLayout());
		
		cManager = CustomerManager.getInstance();
		
		Customer[] list = cManager.getCustomers();
		customerList = new JList<Customer>(list);

		
		// Left panel with customer list and search bar
		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(700, 640));
		leftPanel.setLayout(new FlowLayout());
		customerScrollPane = new JScrollPane(customerList);
		customerScrollPane.setPreferredSize(new Dimension(700, 600));
		searchBar = new JTextField("Search");
		searchBar.selectAll();
		searchBar.setPreferredSize(new Dimension(300, 30));
		leftPanel.add(searchBar);
		leftPanel.add(customerScrollPane);
		
		// Center panel for buttons
		JPanel rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(200, 640));
		rightPanel.setLayout(new BorderLayout());
		JButton refreshList = new JButton("Refresh list");
		refreshList.setPreferredSize(new Dimension(100, 70));
		refreshList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				refreshList();
			}
			
		});
		JButton editCustomer = new JButton("Edit customer");
		editCustomer.setPreferredSize(new Dimension(100, 70));
		editCustomer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editCustomer(customerList.getSelectedValue());
			}
			
		});
		JPanel rightInnerPanel = new JPanel();
		rightInnerPanel.setLayout(new FlowLayout());
		rightInnerPanel.add(refreshList);
		rightInnerPanel.add(editCustomer);
		rightPanel.add(rightInnerPanel, BorderLayout.CENTER);
		
		add(leftPanel, BorderLayout.WEST);
		add(rightPanel, BorderLayout.CENTER);
	}
	
	public void refreshList() {
		customerList.setListData(cManager.getCustomers());
	}
	
	private void editCustomer(Customer customer) {
		
		if (customer != null) {
			selectedCustomer = customer;
			JFrame editCustomerWindow = new JFrame("Edit customer");
			editCustomerWindow.setLayout(new FlowLayout());
			editCustomerWindow.setSize(new Dimension(500, 500));
			editCustomerWindow.setPreferredSize(new Dimension(500, 500));
			editCustomerWindow.setLocationRelativeTo(null);
			
			firstName = new JTextField();
			firstName.setPreferredSize(new Dimension(130, 30));
			firstName.setText(customer.getFirstName());
			
			lastName = new JTextField();
			lastName.setPreferredSize(new Dimension(130, 30));
			lastName.setText(customer.getLastName());
			
			address = new JTextField();
			address.setPreferredSize(new Dimension(100, 30));
			address.setText(customer.getAddress());
			
			phone = new JTextField();
			phone.setPreferredSize(new Dimension(100, 30));
			phone.setText(customer.getPhone());
			
			JButton commit = new JButton("Commit");
			commit.setPreferredSize(new Dimension(100, 50));
			commit.addActionListener(new ActionListener() {
						
				@Override
				public void actionPerformed(ActionEvent arg0) {
					commitCustomer();
				}
				
			});
			
			editCustomerWindow.add(firstName);
			editCustomerWindow.add(lastName);
			editCustomerWindow.add(address);
			editCustomerWindow.add(phone);
			editCustomerWindow.add(commit);
			
			editCustomerWindow.setVisible(true);
		}
	}
	
	private void commitCustomer() {
		Customer customer = cManager.getCustomer(selectedCustomer.getId());
		customer.setFirstName(firstName.getText());
		customer.setLastName(lastName.getText());
		customer.setPhone(phone.getText());
		customer.setAddress(address.getText());
		try {
			customer.commit();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "The new settings could not be saved");
		}
	}

}
