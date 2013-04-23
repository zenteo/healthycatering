package edu.hist.team3.catering.gui.tab;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.Delivery;
import edu.hist.team3.catering.database.PlanDish;
import edu.hist.team3.catering.database.manager.Services;
import edu.hist.team3.catering.gui.panel.ImagePanel;
import edu.hist.team3.catering.gui.panel.LabeledObject;
import edu.hist.team3.catering.route.RouteToolkit;
import edu.hist.team3.catering.route.Routes;

/*
 * Delivery GUI
--
--
+ getDeliveres()
+ computeDeliveryRoute()
+ changeDeliveryStatus()

 */
@SuppressWarnings("serial")
public class DeliveryTab extends JPanel {
	private Services services;
	
	public DeliveryTab(final Services services) {
		this.services = services;
		
		final JList<LabeledObject<LabeledObject<ArrayList<Delivery>>>> addressList;
		final JList<String> dishList;
		final ImagePanel mapPanel;
		
		addressList = new JList<LabeledObject<LabeledObject<ArrayList<Delivery>>>>();
		addressList.setModel(new DefaultListModel<LabeledObject<LabeledObject<ArrayList<Delivery>>>>());
		
		dishList = new JList<String>();
		dishList.setModel(new DefaultListModel<String>());
		
		mapPanel = new ImagePanel();
		
		addressList.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				final LabeledObject<LabeledObject<ArrayList<Delivery>>> lo = addressList.getSelectedValue();
				DefaultListModel<String> model;
				model = (DefaultListModel<String>)dishList.getModel();
				model.clear();
				mapPanel.setImage(null);
				if (lo != null) {
					for (Delivery delivery : lo.getObject().getObject()) {
						Iterator<PlanDish> it = delivery.getPlan().getDishes().iterator();
						while (it.hasNext()) {
							PlanDish planDish = it.next();
							model.addElement(planDish.getCount() + " x " + planDish.getDish().getName());
						}
					}
					final int index = addressList.getSelectedIndex();
					if (index > 0) {
						new Thread(new Runnable(){
							@Override
							public void run() {
								DefaultListModel<LabeledObject<LabeledObject<ArrayList<Delivery>>>> addressModel;
								addressModel = (DefaultListModel<LabeledObject<LabeledObject<ArrayList<Delivery>>>>)addressList.getModel();
								String from = addressModel.get(index - 1).getObject().getLabel();
								String to = lo.getObject().getLabel();
								RouteToolkit routeTool = RouteToolkit.getInstance();
								Routes directions = routeTool.fetchDirections(from, to);
								mapPanel.setImage(routeTool.fetchMap(directions));
							}
							
						}).start();
					}
				}
			}
		});
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		
		JButton button = new JButton("Get deliveries");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Calendar now = Calendar.getInstance();
				Date date = Date.valueOf(now.get(Calendar.YEAR) + "-"
						+ (now.get(Calendar.MONTH) + 1) + "-"
						+ now.get(Calendar.DAY_OF_MONTH));
				HashMap<String, ArrayList<Delivery>> deliveryMap = new HashMap<String, ArrayList<Delivery>>();
				ArrayList<Delivery> deliveries = services.getDeliveryManager().getDeliveries(date);
				ArrayList<String> addresses = new ArrayList<String>();
				Customer admin = services.getCustomerManager().getCustomer(1);
				String firstAddress = admin.getAddress();
				deliveryMap.put(firstAddress, new ArrayList<Delivery>());
				addresses.add(firstAddress);
				for (Delivery delivery : deliveries) {
					if (delivery.getStatus() == Delivery.STATUS_COOKED) {
						String address = delivery.getPlan().getCustomer().getAddress();
						if (!deliveryMap.containsKey(address)) {
							deliveryMap.put(address, new ArrayList<Delivery>());
							addresses.add(address);
							System.out.println(address);
						}
						deliveryMap.get(address).add(delivery);
					}
				}
				RouteToolkit routeTool = RouteToolkit.getInstance();
				ArrayList<String> route = routeTool.computeShortestLoop(addresses);
				route = routeTool.rearrange(route, firstAddress);
				DefaultListModel<LabeledObject<LabeledObject<ArrayList<Delivery>>>> model;
				model = (DefaultListModel<LabeledObject<LabeledObject<ArrayList<Delivery>>>>)addressList.getModel();
				model.clear();
				for (String address : route) {
					model.addElement(new LabeledObject<LabeledObject<ArrayList<Delivery>>>(address, new LabeledObject<ArrayList<Delivery>>(address, deliveryMap.get(address))));
				}
			}
		});
		leftPanel.add(button, BorderLayout.NORTH);
		
		button = new JButton("Marked as delivered");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				final LabeledObject<LabeledObject<ArrayList<Delivery>>> lo = addressList.getSelectedValue();
				if (lo != null) {
					boolean failed = false;
					lo.setLabel("[Delivered]");
					addressList.repaint();
					for (Delivery delivery : lo.getObject().getObject()) {
						try {
							delivery.deliver();
							delivery.commit();
						}
						catch (SQLException ex) {
							failed = true;
						}
					}
					if (failed) {
						Services.showError("Error: Could not mark as delivered!");
					}
				}
				else {
					Services.showError("Select an address first!");
				}
			}
		});
		leftPanel.add(button, BorderLayout.SOUTH);
		
		leftPanel.add(addressList, BorderLayout.CENTER);
		
		setLayout(new BorderLayout());
		add(leftPanel, BorderLayout.WEST);
		add(mapPanel, BorderLayout.CENTER);
		add(dishList, BorderLayout.EAST);
	}
	
}
