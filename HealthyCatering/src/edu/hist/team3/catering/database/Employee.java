package edu.hist.team3.catering.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
/**
 * This class is to describe the employee
 */
public class Employee extends DatabaseRow {
	// Different columns of the Employee-table in the database:
	private Customer customer;				// customer_id			INT				NOT NULL
	private Job job;						// job_id				INT				NOT NULL
	private String username;				// username				VARCHAR(32)		NOT NULL
	private String password;				// password				VARCHAR(32)		NOT NULL
	private String email;					// email				VARCHAR(256)	NOT NULL
	private Date employmentDate;			// employment_date		DATE			NOT NULL
	private double sessionHours;			// session_hours		DOUBLE			NOT NULL

	/**
	 * This is the constructor for this class
	 * @param id is an integer used as the primary key for employee/Customer
	 */
	public Employee(DatabaseManager manager, Customer customer) {
		super(manager);
		this.customer = customer;
	}
	
	/**
	 * Create a new default row in the Employee-table.
	 * @param manager	The DatabaseManager for a connection to a database
	 * @return A instance of Employee
	 * @throws SQLException
	 */
	public static Employee createDefault(DatabaseManager manager, Customer customer, Job job) throws SQLException {
		String sql = "INSERT INTO Employee" +
				"(customer_id, job_id, username, password, email, employment_date, session_hours)" +
				"VALUES (" + customer.getId() + ", " + job.getId() + ", '', '', '', '2000-01-01', 0)";
		try (PreparedStatement ps = manager.prepareStatement(sql)) {
			ps.executeUpdate();
		}
		Employee ret = manager.getEmployee(customer);
		return ret;
	}
	
	@Override
	public void fetch() throws SQLException {
		super.fetch();
		String sql = "SELECT job_id, username, password, email, employment_date, session_hours FROM Employee WHERE customer_id = " + customer.getId();
		try (PreparedStatement ps = getManager().prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					this.job = getManager().getJob(rs.getInt(1));
					this.username = rs.getString(2);
					this.password = rs.getString(3);
					this.email = rs.getString(4);
					this.employmentDate = rs.getDate(5);
					this.sessionHours = rs.getDouble(6);
				}
			}
		}
	}
	
	@Override
	public void commit() throws SQLException {
		super.commit();
		String sql = "UPDATE Employee SET ";
		sql += "job_id = " + job.getId();
		sql += ", username = '" + username + "'";
		sql += ", password = '" + password + "'";
		sql += ", email = '" + email + "'";
		sql += ", employment_date = '" + employmentDate.toString() + "'";
		sql += ", session_hours = " + sessionHours;
		sql += " WHERE customer_id = " + customer.getId();
		try (PreparedStatement ps = getManager().prepareStatement(sql)) {
			ps.executeUpdate();
		}
	}

	@Override
	public void remove() throws SQLException {
		super.setRemoved(); // Wont to call super.remove(), because then customer would be removed from database.
		String sql = "DELETE FROM Employee WHERE CUSTOMER_ID = " + customer.getId();
		try (PreparedStatement ps = getManager().prepareStatement(sql)) {
			ps.executeUpdate();
		}
	}
	
	/**
	 * Gets the customer.
	 * @return The customer
	 */
    public Customer getCustomer() {
    	return customer;
    }
	
	/**
	 * Gets the job.
	 * @return The job
	 */
    public Job getJob() {
    	super.tryFetch();
		return job;
	}
    
    /**
     * Sets the Job to a new value.
     * @param job The new job
     */
	public void setJob(Job job) {
		assert(job != null); 			// NOT NULL
		super.setChanged();
		this.job = job;
	}
	
	/**
	 * Gets the username.
	 * @return The username
	 */
    public String getUsername() {
    	super.tryFetch();
		return username;
	}
    
    /**
     * Sets the username to a new value.
     * @param username The new username
     */
	public void setUsername(String username) {
		assert(username != null); 			// NOT NULL
		assert(username.length() <= 32); 	// Max length = 32
		super.setChanged();
		this.username = username;
	}
	
	/**
	 * Gets the password.
	 * @return The password
	 */
    public String getPassword() {
    	super.tryFetch();
		return password;
	}
    
    /**
     * Sets the password to a new value.
     * @param password The new password
     */
	public void setPassword(String password) {
		assert(password != null); 			// NOT NULL
		assert(password.length() <= 32); 	// Max length = 32
		super.setChanged();
	    this.password = password;
	}
	
	/**
	 * Gets the e-mail.
	 * @return The e-mail
	 */
    public String getEmail() {
    	super.tryFetch();
		return email;
	}
    
    /**
     * Sets the e-mail to a new value.
     * @param email The new e-mail
     */
	public void setEmail(String email) {
		assert(password != null); 			// NOT NULL
		assert(password.length() <= 256); 	// Max length = 256
		super.setChanged();
		this.email = email;
	}
	
	/**
	 * Gets the startdate of employment.
	 * @return The startdate of employment
	 */
    public Date getEmploymentDate() {
    	super.tryFetch();
		return employmentDate;
	}
    
    /**
     * Sets the startdate of employment to a new value.
     * @param employmentDate The new startdate of employment
     */
	public void setEmploymentDate(Date employmentDate) {
		assert(employmentDate != null);
		super.setChanged();				// NOT NULL
	   	this.employmentDate = employmentDate;
	}
	
	/**
	 * Gets the number of hours worked.
	 * @return The number of hours worked
	 */
	public double getSessionHours() {
		super.tryFetch();
		return sessionHours;
	}
	
	/**
	 * Sets the number of hours worked to a new value.
	 * @param sessionHours New number of hours worked
	 */
    public void setSessionHours(double sessionHours) {
    	super.setChanged();
		this.sessionHours = sessionHours;
	}
    
    public boolean equals(Object other){
    	if (other == null || !(other instanceof Employee))
    		return false;
		return customer.equals(((Employee)other).customer);
	}

	public String toString() {
		super.tryFetch();
		return "Employee[" + super.toString() + "; job = " + getJob().toString() + "; username = '"
				+ username + "'; email = '" + email + "'; employmentDate = "
				+ employmentDate.toString() + "; sessionHours = " + sessionHours + "]";
	}
}