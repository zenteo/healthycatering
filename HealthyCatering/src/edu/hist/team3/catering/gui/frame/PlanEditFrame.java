package edu.hist.team3.catering.gui.frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.hist.team3.catering.database.Dish;
import edu.hist.team3.catering.database.Plan;
import edu.hist.team3.catering.database.PlanDish;
import edu.hist.team3.catering.database.manager.Services;
import edu.hist.team3.catering.gui.panel.PlanDishSearch;
import edu.hist.team3.catering.gui.panel.PlanEditPanel;
import edu.hist.team3.catering.gui.panel.PropertyPanel;

public class PlanEditFrame extends JFrame {
	private PlanEditPanel planPanel;

	public PlanEditFrame(final Plan plan, final Services services) {
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		
		planPanel = new PlanEditPanel(plan, services);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				planPanel.apply();
			}
		});
		content.add(planPanel, BorderLayout.CENTER);
		
		JButton closeButton = new JButton("Close window");
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				planPanel.apply();
				setVisible(false);
			}
		});
		
		content.add(closeButton, BorderLayout.SOUTH);
		
		add(new JScrollPane(content));

		setTitle("Plan editor");
		setSize(600, 400);
		setLocationRelativeTo(null);
	}
}