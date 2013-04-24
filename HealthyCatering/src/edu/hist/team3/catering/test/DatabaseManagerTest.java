package edu.hist.team3.catering.test;

import java.sql.Date;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.DatabaseManager;
import edu.hist.team3.catering.database.Delivery;
import edu.hist.team3.catering.database.Dish;
import edu.hist.team3.catering.database.DishResource;
import edu.hist.team3.catering.database.Employee;
import edu.hist.team3.catering.database.Job;
import edu.hist.team3.catering.database.Plan;
import edu.hist.team3.catering.database.PlanDish;
import edu.hist.team3.catering.database.Resource;

public class DatabaseManagerTest {
	private DatabaseManager manager;
	private Customer customer;
	private Job job;
	private Employee employee;
	private Plan plan;
	private Dish dish;
	private Resource resource;
	private Delivery delivery;
	
	// We will use JUnit to test our employee, job and customer classes.
	@Before
	public void testConnection() throws SQLException {
		// First we setup a connection to the database
		DatabaseManager databaseManager = null;
		manager = new DatabaseManager("jdbc:derby://db.stud.aitel.hist.no:1527/13ing1gr3", "team3", "Ikj721");
	}
	
	@After
	public void testCloseConnection() throws SQLException {
		manager.close();
	}
	
	// This test is for fetching allready existing data form the database
	@Test
	public void testFetch() {
		// Get administrator as customer
		Customer customer = manager.getCustomer(1);
		System.out.println(customer.toString());
		// Get administrator as employee
		Employee employee = manager.getEmployee(customer);
		System.out.println(employee.toString());
		// Get the administrator job
		Job job = employee.getJob();
		System.out.println(job.toString());
	}
	
	public void testCreate() throws SQLException {
		customer = manager.createCustomer();
		customer.setFirstName("Ola");
		customer.setLastName("Nordmann");
		customer.setAddress("Kongsbakken 1");
		customer.setCreationDate(Date.valueOf("2013-04-24"));
		customer.setPhone("+4712345678");
		customer.commit();
		
		job = manager.createJob();
		job.setName("Working ant");
		job.setPercentSales(Job.PRIVILEGE_DRIVER);
		job.setHourlySalary(100);
		job.setYearlySalary(100000);
		job.commit();
		
		employee = manager.createEmployee(customer, job);
		employee.setEmail("ola.nordmann@online.no");
		employee.setUsername("ola");
		employee.setPassword("1235");
		employee.commit();
		
		resource = manager.createResource();
		resource.setName("Porkchops");
		resource.setCategory("Red meat");
		resource.setDescription("Delicious porkchops, try Minecraft!");
		resource.setProducer("Gilde Norsk Kjøtt");
		resource.setSource("Coop Obs! Hypermarked");
		resource.setStockCount(10.0); // We have 10.0 packs of porkchops.
		resource.setAmount(10.0); // 10 in one pack
		resource.setCalories(1000.0);
		resource.setWeight(0.1356);
		resource.setVolume(0.3); // Doesn't make sense to give it a volume, but whatever...
		resource.setHealthiness(0.4); // 40% healthy
		resource.setCosts(140);	// 140 NOK
		resource.commit();
		
		dish = manager.createDish();
		dish.setName("Awesome Porkchops");
		dish.setCategory("Meat");
		dish.setHealthiness(1.0);
		dish.setPrice(100);
		dish.setDefaultDiscount(0.1);
		dish.setLongtermDiscount(0.2);
		dish.commit();
		
		DishResource dishResource = dish.getResources().add(resource); // Add porkchops
		dishResource.setAmount(4.0); // 4 porkchops
		dishResource.commit();
		
		plan = manager.createPlan(customer);
		plan.setDaysOfWeek(Plan.DAY_MONDAY);
		plan.setSumIncome(14);
		plan.setSumOutcome(43);
		plan.setStartDate(Date.valueOf("1234-11-23"));
		plan.setEndDate(Date.valueOf("1234-12-24"));
		plan.commit();
		
		PlanDish planDish = plan.getDishes().add(dish);
		planDish.setCount(10);
		planDish.setDiscount(0.6);
		planDish.commit();
		
		delivery = manager.createDelivery(plan);
		delivery.setDate(Date.valueOf("1234-11-23"));
		delivery.setStatus((short) 1);
		delivery.commit();
	}
	
	public void testRemoval() throws SQLException {
		employee.remove();
		job.remove();
		delivery.remove();
		plan.remove();
		customer.remove();
		dish.remove();
		resource.remove();
	}
	
	@Test
	public void testCreateAndRemove() throws SQLException {
		testCreate();
		testRemoval();
	}
}
