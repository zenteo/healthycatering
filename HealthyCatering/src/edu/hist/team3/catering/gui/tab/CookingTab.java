package edu.hist.team3.catering.gui.tab;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.Delivery;
import edu.hist.team3.catering.database.DishResource;
import edu.hist.team3.catering.database.PlanDish;
import edu.hist.team3.catering.database.Resource;
import edu.hist.team3.catering.database.manager.Services;
import edu.hist.team3.catering.gui.panel.LabeledObject;

/*
 * Cooking GUI
 --
 --
 + getDeliveries()
 + getDishesOfADelivery()
 + getResourcesOfADish()
 + changeDeliveryStatus()
 */
@SuppressWarnings("serial")
public class CookingTab extends JPanel {
	private Services services;

	public CookingTab(final Services services) {
		this.services = services;

		final JList<LabeledObject<Delivery>> deliveryList = new JList<LabeledObject<Delivery>>();
		final JList<LabeledObject<PlanDish>> dishList = new JList<LabeledObject<PlanDish>>();
		final JList<LabeledObject<DishResource>> resourceList = new JList<LabeledObject<DishResource>>();
		
		deliveryList.setModel(new DefaultListModel<LabeledObject<Delivery>>());
		dishList.setModel(new DefaultListModel<LabeledObject<PlanDish>>());
		resourceList.setModel(new DefaultListModel<LabeledObject<DishResource>>());

		deliveryList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				LabeledObject<Delivery> lo = deliveryList.getSelectedValue();
				DefaultListModel<LabeledObject<PlanDish>> model;
				model = (DefaultListModel<LabeledObject<PlanDish>>)dishList.getModel();
				model.clear();
				if (lo != null) {
					Delivery selected = lo.getObject();
					Iterator<PlanDish> it = selected.getPlan().getDishes().iterator();
					while (it.hasNext()) {
						PlanDish planDish = it.next();
						String label = planDish.getCount() + " x " + planDish.getDish().getName();
						model.addElement(new LabeledObject<PlanDish>(label, planDish));
					}
				}
			}
		});
		
		dishList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				LabeledObject<PlanDish> lo = dishList.getSelectedValue();
				DefaultListModel<LabeledObject<DishResource>> model;
				model = (DefaultListModel<LabeledObject<DishResource>>)resourceList.getModel();
				model.clear();
				if (lo != null) {
					PlanDish selected = lo.getObject();
					Iterator<DishResource> it = selected.getDish().getResources().iterator();
					while (it.hasNext()) {
						DishResource dishResource = it.next();
						Resource res = dishResource.getResource();
						String label = "";
						double count = selected.getCount() * dishResource.getAmount();
						if (res.getWeight() != 0.0) {
							double weight = count * res.getWeight();
							if (weight < 1.0) {
								weight *= 1000.0;
								label += weight + " g, ";
							}
							else {
								label += weight + " kg, ";
							}
						}
						if (res.getVolume() != 0.0) {
							double volume = count * res.getVolume();
							if (volume < 1.0) {
								volume *= 1000.0;
								label += volume + " ml, ";
							}
							else {
								label += volume + " l, ";
							}
						}
						if (res.getAmount() != 0.0) {
							label += count * res.getAmount() + " pieces of ";
						}
						else if (label.equals("")) {
							label += count + " pieces of ";
						}
						label += res.getName() + " from " + res.getProducer();
						model.addElement(new LabeledObject<DishResource>(label, dishResource));
					}
				}
			}
		});
		
		JPanel centerContent = new JPanel();
		centerContent.setLayout(new GridLayout(1, 2));
		centerContent.add(deliveryList);
		centerContent.add(dishList);
		centerContent.add(resourceList);
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(2, 1));

		JButton button = new JButton("Get deliveries");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Calendar now = Calendar.getInstance();
				Date date = Date.valueOf(now.get(Calendar.YEAR) + "-"
						+ (now.get(Calendar.MONTH) + 1) + "-"
						+ now.get(Calendar.DAY_OF_MONTH));
				services.getDeliveryManager().createDeliveries(date);
				ArrayList<Delivery> deliveries = services.getDeliveryManager().getDeliveries(date);
				DefaultListModel<LabeledObject<Delivery>> model;
				model = (DefaultListModel<LabeledObject<Delivery>>)deliveryList.getModel();
				model.clear();
				for (Delivery delivery : deliveries) {
					if (delivery.getStatus() == Delivery.STATUS_NONE) {
						Customer customer = delivery.getPlan().getCustomer();
						String label = customer.getFirstName() + " " + customer.getLastName();
						model.addElement(new LabeledObject<Delivery>(label, delivery));
					}
				}
			}
		});
		leftPanel.add(button);

		button = new JButton("Mark as cooked");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				LabeledObject<Delivery> lo = deliveryList.getSelectedValue();
				if (lo != null) {
					try {
						Delivery selected = lo.getObject();
						selected.cook();
						selected.commit();
						DefaultListModel<LabeledObject<Delivery>> model;
						model = (DefaultListModel<LabeledObject<Delivery>>)deliveryList.getModel();
						model.remove(deliveryList.getSelectedIndex());
					}
					catch (SQLException e) {
						e.printStackTrace();
						Services.showError("Error: Could not mark delivery as cooked!");
					}
				}
				else {
					Services.showError("Select a delivery first!");
				}
			}
		});
		leftPanel.add(button);

		JPanel leftHolder = new JPanel();
		leftHolder.setLayout(new BorderLayout());
		leftHolder.add(leftPanel, BorderLayout.NORTH);
		
		setLayout(new BorderLayout());
		add(centerContent, BorderLayout.CENTER);
		add(leftHolder, BorderLayout.WEST);
	}
}
