package edu.hist.team3.catering;

import java.sql.SQLException;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.DatabaseManager;
import edu.hist.team3.catering.database.manager.CustomerManager;
import edu.hist.team3.catering.database.manager.Services;

public class NeedersTestingGrounds {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DatabaseManager databaseManager = null;
		try {
			databaseManager = new DatabaseManager(
					"jdbc:derby://db.stud.aitel.hist.no:1527/13ing1gr3",
					"team3", "Ikj721");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (databaseManager != null) {
			Services services = new Services(databaseManager);

			CustomerManager hello = services.getCustomerManager();
			Customer[] list = hello.getCustomers();

			for (Customer cus : list) {
				System.out.println(cus);
			}
		}
	}

}
