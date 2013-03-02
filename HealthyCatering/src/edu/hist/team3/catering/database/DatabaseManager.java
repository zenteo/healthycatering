package edu.hist.team3.catering.database;

import java.lang.ref.WeakReference;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Used to manage the database.
 */
public class DatabaseManager {
	/*
	 * These HashMaps are to avoid duplicates of loaded objects with same id.
	 * 	Example:
	 * 		Dish a = databaseManager.getDish(1); // Loading a object with id = 1 and stores it in 'dishes'.
	 * 		Dish b = databaseManager.getDish(1); // Getting the object with id = 1 from 'dishes'.
	 * 		a.setName("test string");			 // Sets a and b's name to "test string".
	 * 		// b.getName().equals("test string") is true because a and b are the same instance.
	 * 
	 * WeakReference is used to avoid inhibition of the garbage collection.
	 * 	Example:
	 * 		Dish a = databaseManager.getDish(1); // Loading a object with id = 1 and stores it in 'dishes'.
	 * 		a = null;	// Allows a to be removed by garbage collection.
	 * 		System.gc(); // Hints the garbage collector that it's time to do his job.
	 * 		// We don't know when the collector runs, but let say that System.gc() did more than just hinting.
	 * 		// Since the collector ran, the instance of a Dish we got from getDish(1) would be deleted.
	 * 		// It would not be deleted if we didn't use WeakReference in our HashMap.
	 * 
	 * Maybe TODO: Add this solution for links too?
	 */
	private final HashMap<Integer, WeakReference<Customer>> customers;
	private final HashMap<Integer, WeakReference<Employee>> employees;
	private final HashMap<Integer, WeakReference<Delivery>> deliveries;
	private final HashMap<Integer, WeakReference<Dish>> dishes;
	private final HashMap<Integer, WeakReference<Job>> jobs;
	
	// Our connection to the database.
	private final Connection connection;
	
	/**
	 * Constructs a DatabaseManager with a new SQL connection.
	 * @param url				The URL we want to connect to
	 * @param username			The username required for the connection
	 * @param password			The password required for the connection
	 * @throws SQLException		If it failed to connect to the database
	 */
	public DatabaseManager(String url, String username, String password) throws SQLException {
		this(DriverManager.getConnection(url, username, password));
	}
	
	/**
	 * Constructs a DatabaseManager with an old SQL connection.
	 * @param connection		The connection to be used
	 * @throws SQLException		If it failed to connect to the database
	 */
	public DatabaseManager(Connection connection) throws SQLException {
		
		customers = new HashMap<Integer, WeakReference<Customer>>();
		employees = new HashMap<Integer, WeakReference<Employee>>();
		deliveries = new HashMap<Integer, WeakReference<Delivery>>();
		dishes = new HashMap<Integer, WeakReference<Dish>>();
		jobs = new HashMap<Integer, WeakReference<Job>>();
		
		this.connection = connection;
	}
	
	/**
	 * Creates a new customer.
	 * @return A instance of Customer
	 * @throws SQLException
	 */
	public Customer createCustomer() throws SQLException {
		return Customer.createDefault(this);
	}
	
	/**
	 * Get a customer with a specific ID.
	 * @param id	The ID of the customer
	 * @return 		A instance of Customer
	 */
	public Customer getCustomer(int id) {
		Customer ret = null;
		// Checks if it is already loaded.
		if (customers.containsKey(id)) {
			WeakReference<Customer> ref = customers.get(id);
			ret = ref.get(); // returns 'null' if it is deleted by garbage collector.
		}
		// Checks if it is not loaded or removed(by user and by collector).
		if (ret == null || ret.isRemoved()) {
			ret = new Customer(this, id);
			customers.put(id, new WeakReference<Customer>(ret));
		}
		return ret;
	}
	
	/**
	 * Creates a new job.
	 * @return A instance of Job
	 * @throws SQLException 
	 */
	public Job createJob() throws SQLException {
		return Job.createDefault(this);
	}
	
	/**
	 * Get a job with a specific ID.
	 * @param id	The ID of the job
	 * @return A instance of Job
	 */
	public Job getJob(int id) {
		Job ret = null;
		// Checks if it is already loaded.
		if (jobs.containsKey(id)) {
			WeakReference<Job> ref = jobs.get(id);
			ret = ref.get(); // returns 'null' if it is deleted by garbage collector.
		}
		// Checks if it is not loaded or removed(by user and by collector).
		if (ret == null || ret.isRemoved()) {
			ret = new Job(this, id);
			jobs.put(id, new WeakReference<Job>(ret));
		}
		return ret;
	}
	
	/**
	 * Creates a new employee based on a new customer, with a specific job.
	 * @param job The job of the employee
	 * @return A instance of Employee
	 * @throws SQLException 
	 */
	public Employee createEmployee(Job job) throws SQLException { // Creates a new customer too
		return createEmployee(createCustomer(), job);
	}
	
	/**
	 * Creates a new employee based on an already existing customer, with a specific job.
	 * @param customer An existing customer
	 * @param job The job of the employee
	 * @return A new instance of Employee
	 * @throws SQLException 
	 */
	public Employee createEmployee(Customer customer, Job job) throws SQLException {
		assert(customer != null);
		return Employee.createDefault(this, customer, job);
	}
	
	/**
	 * Get a employee based on a customer.
	 * @param customer	The returning employee is also this customer
	 * @return 			A instance of Employee
	 */
	public Employee getEmployee(Customer customer) {
		return getEmployee(customer.getId());
	}
	
	/**
	 * Get a employee with a specific ID.
	 * @param id	The ID of the job
	 * @return A instance of Job
	 */
	public Employee getEmployee(int id) {
		Employee ret = null;
		// Checks if it is already loaded.
		if (employees.containsKey(id)) {
			WeakReference<Employee> ref = employees.get(id);
			ret = ref.get(); // returns 'null' if it is deleted by garbage collector.
		}
		// Checks if it is not loaded or removed(by user and by collector).
		if (ret == null || ret.isRemoved()) {
			ret = new Employee(this, getCustomer(id));
			employees.put(id, new WeakReference<Employee>(ret));
		}
		return ret;
	}
	
	/**
	 * Creates a new dish.
	 * @return A instance of Dish
	 * @throws SQLException 
	 */
	public Dish createDish() throws SQLException {
		return Dish.createDefault(this);
	}
	
	/**
	 * Get a dish with a specific ID.
	 * @param id	The ID of the dish
	 * @return 			A instance of Dish
	 */
	public Dish getDish(int id) {
		Dish ret = null;
		// Checks if it is already loaded.
		if (dishes.containsKey(id)) {
			WeakReference<Dish> ref = dishes.get(id);
			ret = ref.get(); // returns 'null' if it is deleted by garbage collector.
		}
		// Checks if it is not loaded or removed(by user and by collector).
		if (ret == null || ret.isRemoved()) {
			ret = new Dish(this, id);
			dishes.put(id, new WeakReference<Dish>(ret));
		}
		return ret;
	}
	
	/**
	 * Creates a new delivery.
	 * @return A instance of Delivery
	 * @throws SQLException 
	 */
	public Delivery createDelivery(Customer customer) throws SQLException {
		return Delivery.createDefault(this, customer);
	}
	
	/**
	 * Get a delivery with a specific ID.
	 * @param id		The id of the delivery
	 * @return 			A instance of Delivery
	 */
	public Delivery getDelivery(int id) {
		Delivery ret = null;
		// Checks if it is already loaded.
		if (deliveries.containsKey(id)) {
			WeakReference<Delivery> ref = deliveries.get(id);
			ret = ref.get(); // returns 'null' if it is deleted by garbage collector.
		}
		// Checks if it is not loaded or removed(by user and by collector).
		if (ret == null || ret.isRemoved()) {
			ret = new Delivery(this, id);
			deliveries.put(id, new WeakReference<Delivery>(ret));
		}
		return ret;
	}
	
	/**
	 * Creates a new DeliveryDish-link.
	 * @return A instance of DeliveryDish
	 * @throws SQLException 
	 */
	public DeliveryDish createDeliveryDish(Delivery delivery, Dish dish) throws SQLException {
		return DeliveryDish.createDefault(this, delivery, dish);
	}
	
	/**
	 * Get a DeliveryDish-link by combining a delivery and a dish.
	 * @param delivery	The first part of a DeliveryDish-link
	 * @param dish		The other part of a DeliveryDish-link
	 * @return 			A instance of DeliveryDish
	 */
	public DeliveryDish getDeliveryDish(Delivery delivery, Dish dish) {
		return new DeliveryDish(this, delivery, dish);
	}
	
	/**
	 * Creates a new dish-resource link.
	 * @return A instance of DishResource
	 * @throws SQLException 
	 */
	public DishResource createDishResource(Dish dish, Resource resource) throws SQLException {
		return DishResource.createDefault(this, dish, resource);
	}
	
	/**
	 * Get a DishResource-link by combining a dish and a resource.
	 * @param dish		The first part of a DeliveryDish-link
	 * @param resource	The other part of a DeliveryDish-link
	 * @return 			A instance of DishResource
	 */
	public DishResource getDishResource(Dish dish, Resource resource) {
		return new DishResource(this, dish, resource);
	}
	
	/**
	 * Creates a new resource.
	 * @return A instance of Resource
	 * @throws SQLException 
	 */
	public Resource createResource() throws SQLException {
		return Resource.createDefault(this);
	}
	
	/**
	 * Get a resource with a specific ID.
	 * @param id		The ID of the resource
	 * @return 			A instance of Resource
	 */
	public Resource getResource(int id) {
		return new Resource(this, id);
	}
	
	/**
	 * Prepare a SQL statement to be executed.
	 * @param sql	The SQL-string to be executed.
	 * @return		A new instance of PreparedStatement
	 * @throws SQLException
	 */
	protected PreparedStatement prepareStatement(String sql) throws SQLException {
		return connection.prepareStatement(sql);
	}
	
	/**
	 * Prepare a SQL statement to be executed, containing auto generated keys.
	 * @param sql				The SQL-string to be executed.
	 * @param autoGeneratedKey	The column-name of the auto generated key.
	 * @return					A new instance of PreparedStatement
	 * @throws SQLException
	 */
	protected PreparedStatement prepareStatement(String sql, String autoGeneratedKey) throws SQLException {
		return connection.prepareStatement(sql, new String[]{autoGeneratedKey});
	}
	
	/**
	 * Prepare a SQL statement to be executed, containing auto generated keys.
	 * @param sql				The SQL-string to be executed.
	 * @param autoGeneratedKeys	The column-names of the auto generated keys.
	 * @return					A new instance of PreparedStatement
	 * @throws SQLException
	 */
	protected PreparedStatement prepareStatement(String sql, String[] autoGeneratedKeys) throws SQLException {
		return connection.prepareStatement(sql, autoGeneratedKeys);
	}
	
	/**
	 * Close the SQL connection.
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		connection.close();
	}
}
