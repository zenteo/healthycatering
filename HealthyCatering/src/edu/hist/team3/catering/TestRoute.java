package edu.hist.team3.catering;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;

import edu.hist.team3.catering.gui.panel.ImagePanel;
import edu.hist.team3.catering.route.RouteToolkit;
import edu.hist.team3.catering.route.Routes;

@SuppressWarnings("serial")
public class TestRoute extends JFrame implements ActionListener {
	private ImagePanel map;
	private JList<String> directionsList;
	private JButton findButton;
	
	public TestRoute() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setSize(800, 600);
		setLocationRelativeTo(null);
		setTitle("Test Route Toolkit");
		
		map = new ImagePanel();
		
		directionsList = new JList<String>();
		
		findButton = new JButton("Find route");
		findButton.addActionListener(this);
		
		add(findButton, BorderLayout.NORTH);
		add(directionsList, BorderLayout.WEST);
		add(map, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		ArrayList<String> locations = new ArrayList<String>();
		locations.add("Klæbuveien 125, 7034 Trondheim");
		locations.add("Kong Inges gate 21, Trondheim");
		locations.add("Hans Finnes gate 31, Trondheim");
		locations.add("Bugges veg 3, Trondheim");
		locations.add("Åkervegen 5, Trondheim");
		
		RouteToolkit routeKit = RouteToolkit.getInstance();
		
		ArrayList<String> shortest = routeKit.computeShortestLoop(locations);
		Routes routes = routeKit.fetchDirections(shortest);
		BufferedImage image = routeKit.fetchMap(routes);
		
		map.setImage(image);
		
		DefaultListModel<String> dlm = new DefaultListModel<String>();
		for (int i = 1; i < shortest.size(); i++) {
			dlm.addElement(i + ": " + shortest.get(i));
		}
		directionsList.setModel(dlm);
	}
	
	public static void main(String[] args) {
		TestRoute tr = new TestRoute();
		tr.setVisible(true);
	}
}
