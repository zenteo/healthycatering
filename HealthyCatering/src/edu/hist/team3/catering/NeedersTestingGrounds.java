package edu.hist.team3.catering;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.managers.CustomerManager;

public class NeedersTestingGrounds {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CustomerManager hello = CustomerManager.getInstance();
		Customer[] list = hello.getCustomers();
		
		for (Customer cus : list) {
			System.out.println(cus);
		}
	}

}
