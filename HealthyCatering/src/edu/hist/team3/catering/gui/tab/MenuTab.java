package edu.hist.team3.catering.gui.tab;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.hist.team3.catering.database.manager.Services;


/*
 * 
 *
 *Menu GUI
 *--
 *--
 *+ findResources()
 *+ editResources()
 *+ addResources()
 *+ removeResource()
 *+ findDishes()
 *+ editDises()
 *+ addDish()
 *+ removeDish()
 *+ findMenus()
 *+ addMenu()
 *+ editMenu()
 *+ removeMenu()
 */
@SuppressWarnings("serial")
public class MenuTab extends JPanel{
	private Services services;
	
	public MenuTab(Services services) {
		this.services = services;
		
		Dimension buttonDimension = new Dimension(190, 70);

		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new FlowLayout());
		leftPanel.setPreferredSize(new Dimension(190, 640));
		JPanel fillPanel1 = new JPanel();
		fillPanel1.setLayout(new FlowLayout());
		fillPanel1.setPreferredSize(new Dimension(190, 640));
		JPanel fillPanel2 = new JPanel();
		fillPanel2.setLayout(new FlowLayout());
		fillPanel2.setPreferredSize(new Dimension(190, 640));
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout());
		centerPanel.setPreferredSize(new Dimension(190, 640));
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new FlowLayout());
		rightPanel.setPreferredSize(new Dimension(190, 640));
		
		JButton findResourcesButton = new JButton("Get Resources");
		findResourcesButton.setPreferredSize(buttonDimension);
		findResourcesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
			
		});
		JButton editResourcesButton = new JButton("Edit Resources");
		editResourcesButton.setPreferredSize(buttonDimension);
		editResourcesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
			
		});
		JButton addResourcesButton = new JButton("Add Resources");
		addResourcesButton.setPreferredSize(buttonDimension);
		addResourcesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
			
		});
		JButton removeResourcesButton = new JButton("Remove Resources");
		removeResourcesButton.setPreferredSize(buttonDimension);
		removeResourcesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
			
		});
		JButton findDishButton = new JButton("Find Dish");
		findDishButton.setPreferredSize(buttonDimension);
		findDishButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
			
		});
		JButton EditDishButton = new JButton("Edit Dish");
		EditDishButton.setPreferredSize(buttonDimension);
		EditDishButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
			
		});
		JButton AddDishButton = new JButton("Add Dish");
		AddDishButton.setPreferredSize(buttonDimension);
		AddDishButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
			
		});
		JButton RemoveDishButton = new JButton("Remove Dish");
		RemoveDishButton.setPreferredSize(buttonDimension);
		RemoveDishButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
			
		});
		JButton findMenuButton = new JButton("Find Menu");
		findMenuButton.setPreferredSize(buttonDimension);
		findMenuButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
			
		});
		JButton editMenuButton = new JButton("Edit Menu");
		editMenuButton.setPreferredSize(buttonDimension);
		editMenuButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
			
		});
		JButton addMenuButton = new JButton("Add Menu");
		addMenuButton.setPreferredSize(buttonDimension);
		addMenuButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
			
		});
		JButton removeMenuButton = new JButton("Remove Menu");
		removeMenuButton.setPreferredSize(buttonDimension);
		removeMenuButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
			
		});
		leftPanel.add(findResourcesButton);
		leftPanel.add(Box.createRigidArea(buttonDimension));
		leftPanel.add(editResourcesButton);
		leftPanel.add(Box.createRigidArea(buttonDimension));
		leftPanel.add(addResourcesButton);
		leftPanel.add(Box.createRigidArea(buttonDimension));
		leftPanel.add(removeResourcesButton);
		centerPanel.add(findDishButton);
		centerPanel.add(Box.createRigidArea(buttonDimension));
		centerPanel.add(EditDishButton);
		centerPanel.add(Box.createRigidArea(buttonDimension));
		centerPanel.add(AddDishButton);
		centerPanel.add(Box.createRigidArea(buttonDimension));
		centerPanel.add(RemoveDishButton);
		rightPanel.add(findMenuButton);
		rightPanel.add(Box.createRigidArea(buttonDimension));
		rightPanel.add(editMenuButton);
		rightPanel.add(Box.createRigidArea(buttonDimension));
		rightPanel.add(addMenuButton);
		rightPanel.add(Box.createRigidArea(buttonDimension));
		rightPanel.add(removeMenuButton);
		fillPanel1.add(Box.createRigidArea(buttonDimension));
		fillPanel2.add(Box.createRigidArea(buttonDimension));
		
		add(leftPanel);
		add(fillPanel1);
		add(centerPanel);
		add(fillPanel2);
		add(rightPanel);
		
	}
	
}
