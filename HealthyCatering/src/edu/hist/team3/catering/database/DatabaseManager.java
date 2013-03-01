package edu.hist.team3.catering.database;

import java.lang.ref.WeakReference;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class DatabaseManager {
	/*
	 * These HashMaps are to avoid duplicates of the different loaded objects.
	 * WeakReference is used to avoid inhibition of garbage collection.
	 * 
	 * Maybe TODO: Add this solution for links too.
	 */
	private final HashMap<Integer, WeakReference<Customer>> customers;
	private final HashMap<Integer, WeakReference<Employee>> employees;
	private final HashMap<Integer, WeakReference<Delivery>> deliveries;
	private final HashMap<Integer, WeakReference<Dish>> dishes;
	private final HashMap<Integer, WeakReference<Job>> jobs;
	
	private final Connection connection;
	
	public DatabaseManager(String url, String username, String password) throws SQLException {
		this(DriverManager.getConnection(url, username, password));
	}
	
	public DatabaseManager(Connection connection) throws SQLException {
		customers = new HashMap<Integer, WeakReference<Customer>>();
		employees = new HashMap<Integer, WeakReference<Employee>>();
		deliveries = new HashMap<Integer, WeakReference<Delivery>>();
		dishes = new HashMap<Integer, WeakReference<Dish>>();
		jobs = new HashMap<Integer, WeakReference<Job>>();
		this.connection = connection;
		this.connection.setAutoCommit(true);
	}
	
	/**
	 * Creates a new customer.
	 * @return a new instance of Customer
	 * @throws SQLException 
	 */
	public Customer createCustomer() throws SQLException {
		return Customer.createDefault(this);
	}
	
	public Customer getCustomer(int id) {
		Customer ret = null;
		if (customers.containsKey(id)) {
			WeakReference<Customer> ref = customers.get(id);
			ret = ref.get();
		}
		if (ret == null || ret.isRemoved()) {
			ret = new Customer(this, id);
			customers.put(id, new WeakReference<Customer>(ret));
		}
		return ret;
	}
	
	/**
	 * Creates a new job.
	 * @return a new instance of Job
	 * @throws SQLException 
	 */
	public Job createJob() throws SQLException {
		return Job.createDefault(this);
	}
	
	public Job getJob(int id) {
		Job ret = null;
		if (jobs.containsKey(id)) {
			WeakReference<Job> ref = jobs.get(id);
			ret = ref.get();
		}
		if (ret == null || ret.isRemoved()) {
			ret = new Job(this, id);
			jobs.put(id, new WeakReference<Job>(ret));
		}
		return ret;
	}
	
	/**
	 * Creates a new employee based on a new customer.
	 * @return a new instance of Employee
	 * @throws SQLException 
	 */
	public Employee createEmployee(Job job) throws SQLException { // Creates a new customer too
		return createEmployee(createCustomer(), job);
	}
	
	/**
	 * Creates a new employee based on an already existing customer.
	 * @param customer An existing customer
	 * @return a new instance of Employee
	 * @throws SQLException 
	 */
	public Employee createEmployee(Customer customer, Job job) throws SQLException {
		assert(customer != null);
		return Employee.createDefault(this, customer.getId(), job);
	}
	
	public Employee getEmployee(Customer customer) {
		
		return getEmployee(customer.getId());
	}
	
	public Employee getEmployee(int id) {
		Employee ret = null;
		if (employees.containsKey(id)) {
			WeakReference<Employee> ref = employees.get(id);
			ret = ref.get();
		}
		if (ret == null || ret.isRemoved()) {
			ret = new Employee(this, id);
			employees.put(id, new WeakReference<Employee>(ret));
		}
		return ret;
	}
	
	/**
	 * Creates a new dish.
	 * @return a new instance of Dish
	 * @throws SQLException 
	 */
	public Dish createDish() throws SQLException {
		return Dish.createDefault(this);
	}
	
	public Dish getDish(int id) {
		Dish ret = null;
		if (dishes.containsKey(id)) {
			WeakReference<Dish> ref = dishes.get(id);
			ret = ref.get();
		}
		if (ret == null || ret.isRemoved()) {
			ret = new Dish(this, id);
			dishes.put(id, new WeakReference<Dish>(ret));
		}
		return ret;
	}
	
	/**
	 * Creates a new delivery.
	 * @return a new instance of Delivery
	 * @throws SQLException 
	 */
	public Delivery createDelivery(Customer customer) throws SQLException {
		return Delivery.createDefault(this, customer);
	}
	
	public Delivery getDelivery(int id) {
		Delivery ret = null;
		if (deliveries.containsKey(id)) {
			WeakReference<Delivery> ref = deliveries.get(id);
			ret = ref.get();
		}
		if (ret == null || ret.isRemoved()) {
			ret = new Delivery(this, id);
			deliveries.put(id, new WeakReference<Delivery>(ret));
		}
		return ret;
	}
	
	/**
	 * Creates a new delivery-dish link.
	 * @return a new instance of DeliveryDish
	 * @throws SQLException 
	 */
	public DeliveryDish createDeliveryDish(Delivery delivery, Dish dish) throws SQLException {
		return DeliveryDish.createDefault(this, delivery, dish);
	}
	
	public DeliveryDish getDeliveryDish(Delivery delivery, Dish dish) {
		return new DeliveryDish(this, delivery, dish);
	}
	
	/**
	 * Creates a new dish-resource link.
	 * @return a new instance of DishResouce
	 * @throws SQLException 
	 */
	public DishResource createDishResource(Dish dish, Resource resource) throws SQLException {
		return DishResource.createDefault(this, dish, resource);
	}
	
	public DishResource getDishResource(Dish dish, Resource resource) {
		return new DishResource(this, dish, resource);
	}
	
	/**
	 * Creates a new resource.
	 * @return a new instance of Resource
	 * @throws SQLException 
	 */
	public Resource createResource() throws SQLException {
		return Resource.createDefault(this);
	}
	
	public Resource getResource(int id) {
		return new Resource(this, id);
	}
	
	protected PreparedStatement prepareStatement(String sql) throws SQLException {
		return connection.prepareStatement(sql);
	}
	
	protected PreparedStatement prepareStatement(String sql, String autoGeneratedKey) throws SQLException {
		return connection.prepareStatement(sql, new String[]{autoGeneratedKey});
	}
	
	protected PreparedStatement prepareStatement(String sql, String[] autoGeneratedKeys) throws SQLException {
		return connection.prepareStatement(sql, autoGeneratedKeys);
	}
	
	public void close() throws SQLException {
		connection.close();
	}
}
