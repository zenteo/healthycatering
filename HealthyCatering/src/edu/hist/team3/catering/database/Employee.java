package edu.hist.team3.catering.database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
/**
 * This class is to describe the employee
 */
public class Employee extends Customer {	// customer_id		INT				NOT NULL
	private Job job;						// job_id			INT				NOT NULL
	private String username;				// username			VARCHAR(32)		NOT NULL
	private String password;				// password			VARCHAR(32)		NOT NULL
	private String email;					// email			VARCHAR(256)	NOT NULL
	private Calendar employmentDate;		// employment_date	DATE			NOT NULL
	private double sessionHours;			// session_hours	DOUBLE			NOT NULL
	
	/**
	 * This is the constructor for this class
	 * @param id is an integer used as the primary key for employee/Customer
	 */
	protected Employee(DatabaseManager manager, int id) {
		super(manager, id);
	}
	
	public static Employee createDefault(DatabaseManager manager, int id, Job job) throws SQLException {
		String sql = "INSERT INTO Employee" +
				"(CUSTOMER_ID, JOB_ID, USERNAME, PASSWORD, EMAIL, EMPLOYMENT_DATE, SESSION_HOURS)" +
				"VALUES (" + id + ", " + job.getId() + ", '', '', '', '2000-01-01', 0)";
		try (PreparedStatement ps = manager.prepareStatement(sql)) {
			ps.executeUpdate();
		}
		Employee ret = new Employee(manager, id);
		return ret;
	}
	
	@Override
	public void fetch() {
		//TODO: Give me a body!!!
	}
	
	@Override
	public void commit() {
		//TODO: Give me a body!!!
	}
	
	@Override
	public void remove() throws SQLException {
		super.setRemoved(); // Wont to call super.remove(), because then customer would be removed.
		String sql = "DELETE FROM Employee WHERE CUSTOMER_ID = " + super.getId();
		try (PreparedStatement ps = getManager().prepareStatement(sql)) {
			ps.executeUpdate();
		}
	}
	
	/**
	 * Gives us the Job ID
	 * @return jobId
	 */
    public Job getJob() {
    	super.tryFetch();
		return job;
	}
    
    /**
     * Sets the Job ID
     * @param jobId
     */
	public void setJob(Job job) {
		assert(job != null); 			// NOT NULL
		super.setChanged();
		this.job = job;
	}
	
	/**
	 * Gives us the Username
	 * @return username
	 */
    public String getUsername() {
    	super.tryFetch();
		return username;
	}
    
    /**
     * Sets the Username
     * @param username
     */
	public void setUsername(String username) {
		assert(username != null); 			// NOT NULL
		assert(username.length() <= 32); 	// Max length = 32
		super.setChanged();
		this.username = username;
	}
	
	/**
	 * Gives us the Password
	 * @return password
	 */
    public String getPassword() {
    	super.tryFetch();
		return password;
	}
    
    /**
     * Sets the Password
     * @param password
     */
	public void setPassword(String password) {
		assert(password != null); 			// NOT NULL
		assert(password.length() <= 32); 	// Max length = 32
		super.setChanged();
	    this.password = password;
	}
	
	/**
	 * Gives us the Email
	 * @return email
	 */
    public String getEmail() {
    	super.tryFetch();
		return email;
	}
    
    /**
     * Sets the Email
     * @param email
     */
	public void setEmail(String email) {
		assert(password != null); 			// NOT NULL
		assert(password.length() <= 256); 	// Max length = 256
		super.setChanged();
		this.email = email;
	}
	
	/**
	 * Gives us the startdate of employment
	 * @return employmentDate 
	 */
    public Calendar getEmploymentDate() {
    	super.tryFetch();
		return employmentDate;
	}
    
    /**
     * Sets the startdate of employment
     * @param employmentDate
     */
	public void setEmploymentDate(Calendar employmentDate) {
		assert(employmentDate != null);
		super.setChanged();				// NOT NULL
	   	this.employmentDate = employmentDate;
	}
	
	/**
	 * Gives us the number of hours worked
	 * @return sessionHours
	 */
	public double getSessionHours() {
		super.tryFetch();
		return sessionHours;
	}
	
	/**
	 * Sets the number of hours worked
	 * @param sessionHours
	 */
    public void setSessionHours(double sessionHours) {
    	super.setChanged();
		this.sessionHours = sessionHours;
	}

    public boolean equals(Object other){
    	if (other == null || !(other instanceof Employee))
    		return false;
		return getId() == ((Employee)other).getId();
	}

	public String toString() {
		super.tryFetch();
		return "Employee[" + super.toString() + "; job = " + getJob().toString() + "; username = '"
				+ username + "'; email = '" + email + "'; employmentDate = "
				+ employmentDate.toString() + "; sessionHours = " + sessionHours + "]";
	}
}