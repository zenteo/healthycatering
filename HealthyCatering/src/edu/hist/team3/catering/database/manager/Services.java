package edu.hist.team3.catering.database.manager;

import javax.swing.JOptionPane;

import edu.hist.team3.catering.database.DatabaseManager;

public class Services {
	private final CustomerManager customer;
	private final EmployeeManager employee;
	private final ResourceManager resource;
	private final DeliveryManager delivery;
	private final DishManager dish;
	private final PlanManager plan;
	private final JobManager job;

	public Services(DatabaseManager manager) {
		customer = new CustomerManager(manager);
		employee = new EmployeeManager(manager);
		resource = new ResourceManager(manager);
		delivery = new DeliveryManager(manager, this);
		dish = new DishManager(manager);
		plan = new PlanManager(manager);
		job = new JobManager(manager);
	}

	public CustomerManager getCustomerManager() {
		return customer;
	}

	public EmployeeManager getEmployeeManager() {
		return employee;
	}

	public ResourceManager getResourceManager() {
		return resource;
	}

	public DeliveryManager getDeliveryManager() {
		return delivery;
	}

	public DishManager getDishManager() {
		return dish;
	}

	public PlanManager getPlanManager() {
		return plan;
	}

	public JobManager getJobManager() {
		return job;
	}

	public static void showError(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	public static void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	public static boolean choiceMessage(String message, String title) {
		Object[] options = { "Yes", "No" };

		int choice = JOptionPane.showOptionDialog(null, message, title,
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
				options, options[1]);
		return choice == 0;
	}
}
