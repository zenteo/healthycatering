package edu.hist.team3.catering.gui.panel;

import java.awt.BorderLayout;
import java.sql.Date;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.hist.team3.catering.database.Plan;
import edu.hist.team3.catering.database.manager.Services;

public class PlanEditPanel extends JPanel {
	private Plan plan;
	private JTextField startDate;
	private JTextField endDate;
	private JCheckBox mondays;
	private JCheckBox tuesdays;
	private JCheckBox wednesdays;
	private JCheckBox thurdays;
	private JCheckBox fridays;
	private JCheckBox saturdays;
	private JCheckBox sundays;
	
	public PlanEditPanel(Plan plan) {
		this.plan = plan;
		startDate = new JTextField();
		endDate = new JTextField();
		mondays = new JCheckBox("Mondays");
		tuesdays = new JCheckBox("Tuesdays");
		wednesdays = new JCheckBox("Wednesdays");
		thurdays = new JCheckBox("Thurdays");
		fridays = new JCheckBox("Fridays");
		saturdays = new JCheckBox("Saturdays");
		sundays = new JCheckBox("Sundays");
		
		JPanel options = new JPanel();
		options.setLayout(new BoxLayout(options, BoxLayout.PAGE_AXIS));
		options.add(startDate);
		options.add(endDate);
		options.add(mondays);
		options.add(tuesdays);
		options.add(wednesdays);
		options.add(thurdays);
		options.add(fridays);
		options.add(saturdays);
		options.add(sundays);
		
		setLayout(new BorderLayout());
		add(options, BorderLayout.NORTH);
		
		fillInfo();
	}
	
	public void fillInfo() {
		startDate.setText(plan.getStartDate().toString());
		if (plan.getEndDate() != null) {
			endDate.setText(plan.getEndDate().toString());
		}
		mondays.setSelected(plan.isDeliveredOn(Plan.DAY_MONDAY));
		tuesdays.setSelected(plan.isDeliveredOn(Plan.DAY_TUESDAY));
		wednesdays.setSelected(plan.isDeliveredOn(Plan.DAY_WEDNESDAY));
		thurdays.setSelected(plan.isDeliveredOn(Plan.DAY_THURSDAY));
		fridays.setSelected(plan.isDeliveredOn(Plan.DAY_FRIDAY));
		saturdays.setSelected(plan.isDeliveredOn(Plan.DAY_SATURDAY));
		sundays.setSelected(plan.isDeliveredOn(Plan.DAY_SUNDAY));
	}
	
	public void apply() {
		try {
			Date sDate = Date.valueOf(startDate.getText());
			plan.setStartDate(sDate);
		}
		catch(IllegalArgumentException ex) {
		}
		try {
			Date eDate = Date.valueOf(endDate.getText());
			plan.setEndDate(eDate);
		}
		catch(IllegalArgumentException ex) {
		}
		plan.setDaysOfWeek(Plan.DAY_NONE);
		if (mondays.isSelected())
			plan.setDeliveredOn(Plan.DAY_MONDAY);
		if (tuesdays.isSelected())
			plan.setDeliveredOn(Plan.DAY_TUESDAY);
		if (wednesdays.isSelected())
			plan.setDeliveredOn(Plan.DAY_WEDNESDAY);
		if (thurdays.isSelected())
			plan.setDeliveredOn(Plan.DAY_THURSDAY);
		if (fridays.isSelected())
			plan.setDeliveredOn(Plan.DAY_FRIDAY);
		if (saturdays.isSelected())
			plan.setDeliveredOn(Plan.DAY_SATURDAY);
		if (sundays.isSelected())
			plan.setDeliveredOn(Plan.DAY_SUNDAY);
		try {
			plan.commit();
		}
		catch (SQLException e) {
			Services.showError("Could not save the changes to the database.");
			e.printStackTrace();
		}
	}
}
