package edu.hist.team3.catering.gui.panel;

import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.hist.team3.catering.database.Resource;

/*	private String name;		// name			VARCHAR(64)		NOT NULL
 *	private String category;	// category		VARCHAR(64)
 *	private String description;	// description	VARCHAR(256)
 *	private String producer;	// producer		VARCHAR(64)
 *	private String source;		// source		VARCHAR(64)
 *	private double stockCount;	// stock_count	DOUBLE			NOT NULL
 *	private double costs;		// costs		DOUBLE
 *	private double amount;		// amount		DOUBLE
 *	private double weight;		// weight		DOUBLE
 *	private double volume;		// volume		DOUBLE
 *	private double calories;	// calories		DOUBLE
 *	private double healthiness;	// healthiness	DOUBLE
*/

public class ResourceEditPanel extends JPanel {
	private JTextField name;
	private JTextField category;
	private JTextField description;
	private JTextField producer;
	private JTextField source;
	private JFormattedTextField costs;
	private JFormattedTextField amount;
	private JFormattedTextField weight;
	private JFormattedTextField volume;
	private JFormattedTextField calories;
	private JFormattedTextField healthiness;
	
	public ResourceEditPanel() {
		this.name = new JTextField();
		this.category = new JTextField();
		this.description = new JTextField();
		this.producer = new JTextField();
		this.source = new JTextField();
		this.costs = new JFormattedTextField();
		this.costs.setValue(new Double(0.0));
		this.amount = new JFormattedTextField();
		this.amount.setValue(new Double(0.0));
		this.weight = new JFormattedTextField();
		this.weight.setValue(new Double(0.0));
		this.volume = new JFormattedTextField();
		this.volume.setValue(new Double(0.0));
		this.calories = new JFormattedTextField();
		this.calories.setValue(new Double(0.0));
		this.healthiness = new JFormattedTextField();
		this.healthiness.setValue(new Double(0.0));
		
		setLayout(new GridLayout(11, 2));
		add(new JLabel("Name:"));
		add(name);
		add(new JLabel("Category:"));
		add(category);
		add(new JLabel("Description:"));
		add(description);
		add(new JLabel("Producer:"));
		add(producer);
		add(new JLabel("Source:"));
		add(source);
		add(new JLabel("Costs:"));
		add(costs);
		add(new JLabel("Amount:"));
		add(amount);
		add(new JLabel("Weight:"));
		add(weight);
		add(new JLabel("Volume:"));
		add(volume);
		add(new JLabel("Calories:"));
		add(calories);
		add(new JLabel("Healthiness:"));
		add(healthiness);
	}
	
	public void fillInfo(Resource res) {
		name.setText(res.getName());
		category.setText(res.getCategory());
		description.setText(res.getDescription());
		producer.setText(res.getProducer());
		source.setText(res.getSource());
		costs.setValue(res.getCosts());
		amount.setValue(res.getAmount());
		weight.setValue(res.getWeight());
		volume.setValue(res.getVolume());
		calories.setValue(res.getCalories());
		healthiness.setValue(res.getHealthiness() * 100.0);
	}
	
	public void apply(Resource res) throws SQLException {
		res.setName(name.getText());
		res.setCategory(category.getText());
		res.setDescription(description.getText());
		res.setProducer(producer.getText());
		res.setSource(source.getText());
		res.setCosts(((Number)costs.getValue()).doubleValue());
		res.setAmount(((Number)amount.getValue()).doubleValue());
		res.setWeight(((Number)weight.getValue()).doubleValue());
		res.setVolume(((Number)volume.getValue()).doubleValue());
		res.setCalories(((Number)calories.getValue()).doubleValue());
		res.setHealthiness(((Number)healthiness.getValue()).doubleValue());
		res.commit();
	}
}
