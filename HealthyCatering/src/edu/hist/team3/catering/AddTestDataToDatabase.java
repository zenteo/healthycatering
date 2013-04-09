package edu.hist.team3.catering;

import java.sql.Date;
import java.sql.SQLException;

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

public class AddTestDataToDatabase {
	
	/**
	 * This is just a test, and not to be used.
	 */
	public AddTestDataToDatabase(){
		
	}
	
	/**
	 * @param args This does nothing
	 */
	public static void main(String[] args) {
		try {
			DatabaseManager manager = DatabaseManager.getInstance();
			
			System.out.println("Test creation of database rows:");
			
			Resource resource = manager.createResource();
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

			Dish dish = manager.createDish();
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
			
			Customer customer = manager.createCustomer();
			customer.setFirstName("Ola");
			customer.setLastName("Nordmann");
			customer.setPhone("+4712345678");
			customer.setCreationDate(Date.valueOf("1234-12-24")); // Just a test-date
			customer.setAddress("Gokkgata 01, Gokk");
			customer.commit();

			Job job = manager.createJob();
			job.setName("Chilling, looking cool");
			job.setHourlySalary(400.0); // 400 NOK / hour
			job.setYearlySalary(660000.0); // 660 000 NOK / year
			job.setPercentSales(1.0); // 100% of all sales
			job.commit();
			
			Employee employee = manager.createEmployee(customer, job);
			employee.setUsername("test");
			employee.setPassword("test");
			employee.setEmail("horny_pussy@pimp.no");
			employee.setEmploymentDate(Date.valueOf("1234-11-23")); // Also test-date
			employee.setSessionHours(1.0); // Worked for 1 hour in his whole life.
			employee.setPrivileges(Employee.PRIVILEGE_ALL);
			employee.commit();
			
			Plan plan = manager.createPlan(customer);
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
			
			Delivery delivery = manager.createDelivery(plan);
			delivery.setDate(Date.valueOf("1234-11-23"));
			delivery.setStatus((short) 1);
			delivery.commit();
			
			System.out.println("Done.");
			
			System.out.println("Test removal of database rows:");
			
			// Comment the following code to see changes in database:
			/*
			delivery.remove();
			planDish.remove();
			plan.remove();
			employee.remove();
			job.remove();
			customer.remove();
			dishResource.remove();
			resource.remove();
			*/
			System.out.println("Done");
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
