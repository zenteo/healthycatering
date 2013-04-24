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
import edu.hist.team3.catering.database.manager.Services;

public class CreateDefaultDatabase {
	
	/**
	 * This is just a test, and not to be used.
	 */
	public CreateDefaultDatabase(){
		
	}
	
	/**
	 * @param args This does nothing
	 */
	public static void main(String[] args) {
		try {
			DatabaseManager manager = null;
			
			manager = new DatabaseManager("jdbc:derby://db.stud.aitel.hist.no:1527/13ing1gr3", "team3", "Ikj721");
			
			System.out.println("Create default data:");
			
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
			customer.setFirstName("Admin");
			customer.setLastName("Administrator");
			customer.setPhone("+4712345678");
			customer.setCreationDate(Date.valueOf("1234-12-24")); // Just a test-date
			customer.setAddress("Kl�buveien 125, 7034 Trondheim");
			customer.commit();

			Job job = manager.createJob();
			job.setName("Administrator");
			job.setHourlySalary(400.0); // 400 NOK / hour
			job.setYearlySalary(660000.0); // 660 000 NOK / year
			job.setPercentSales(1.0); // 100% of all sales
			job.setPrivileges(Job.PRIVILEGE_ALL);
			job.commit();
			
			Employee employee = manager.createEmployee(customer, job);
			employee.setUsername("admin");
			employee.setPassword("admin");
			employee.setEmail("horny_pussy@pimp.no");
			employee.setEmploymentDate(Date.valueOf("2013-01-23")); // Also test-date
			employee.setSessionHours(0.0); // Worked for 0.0 hour in his whole life.
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
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
