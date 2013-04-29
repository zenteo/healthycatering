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

	/** 
	 * Creates a new Services
	 * @param manager
	 */
	public Services(DatabaseManager manager) {
		customer = new CustomerManager(manager);
		employee = new EmployeeManager(manager);
		resource = new ResourceManager(manager);
		delivery = new DeliveryManager(manager, this);
		dish = new DishManager(manager);
		plan = new PlanManager(manager);
		job = new JobManager(manager);
	}

	/**
	 * Returns the instance of the CustomerManager.
	 * @return
	 */
	public CustomerManager getCustomerManager() {
		return customer;
	}

	/**
	 * Returns the instance of the EmployeeManager.
	 * @return
	 */
	public EmployeeManager getEmployeeManager() {
		return employee;
	}

	/**
	 * Returns the instance of the ResourceManager.
	 * @return
	 */
	public ResourceManager getResourceManager() {
		return resource;
	}

	/**
	 * Returns the instance of the DeliveryManager.
	 * @return
	 */
	public DeliveryManager getDeliveryManager() {
		return delivery;
	}

	/**
	 * Returns the instance of the DishManager.
	 * @return
	 */
	public DishManager getDishManager() {
		return dish;
	}

	/**
	 * Returns the instance of the PlanManager.
	 * @return
	 */
	public PlanManager getPlanManager() {
		return plan;
	}
	
	/**
	 * Returns the instance of the JobManager.
	 * @return
	 */
	public JobManager getJobManager() {
		return job;
	}

	/**
	 * Creates a window with an error message with selected message.
	 * @param message
	 */
	public static void showError(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	/**
	 * Creates a window with selected message.
	 * @param message
	 */
	public static void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	/**
	 * Creates a window with multiple choices.
	 * @param message
	 * @param title
	 * @return
	 */
	public static boolean choiceMessage(String message, String title) {
		Object[] options = { "Yes", "No" };

		int choice = JOptionPane.showOptionDialog(null, message, title,
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
				options, options[1]);
		return choice == 0;
	}
}
