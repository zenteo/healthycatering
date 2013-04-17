package edu.hist.team3.catering.database.manager;

import javax.swing.JOptionPane;

import edu.hist.team3.catering.database.DatabaseManager;

public class Services {
	private final CustomerManager customer;
	private final EmployeeManager employee;
	private final ResourceManager resource;
	private final DishManager dish;
	private final PlanManager plan;
	private final JobManager job;

	public Services(DatabaseManager manager) {
		customer = new CustomerManager(manager);
		employee = new EmployeeManager(manager);
		resource = new ResourceManager(manager);
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
}
