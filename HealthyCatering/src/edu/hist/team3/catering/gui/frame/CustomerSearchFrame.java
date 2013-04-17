package edu.hist.team3.catering.gui.frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.manager.Services;
import edu.hist.team3.catering.gui.panel.CustomerSearch;

public class CustomerSearchFrame extends JFrame {
	private CustomerSearch customerSearch;
	private Customer selected;
	
	public CustomerSearchFrame(Services services) {
		super("Select customer");
		this.customerSearch = new CustomerSearch(services);
		setLayout(new BorderLayout());
		add(customerSearch, BorderLayout.CENTER);
		JButton select = new JButton("Select");
		select.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setSelected(customerSearch.getSelected());
				setVisible(false);
			}
		});
		add(select, BorderLayout.SOUTH);
		setSize(300, 200);
	}

	public Customer getSelected() {
		return selected;
	}

	public void setSelected(Customer selected) {
		this.selected = selected;
	}
}
