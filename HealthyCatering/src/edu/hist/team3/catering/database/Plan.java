package edu.hist.team3.catering.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class Plan extends DatabaseRow {
	// The different flags for the 'daysOfWeek' attribute:
	public static final int DAY_NONE = 0;
	public static final int DAY_MONDAY = 1;
	public static final int DAY_TUESDAY = 2;
	public static final int DAY_WEDNESDAY = 4;
	public static final int DAY_THURSDAY = 8;
	public static final int DAY_FRIDAY = 16;
	public static final int DAY_SATURDAY = 32;
	public static final int DAY_SUNDAY = 64;
	private static final int MAX_DAY = DAY_MONDAY | DAY_TUESDAY | DAY_WEDNESDAY | DAY_THURSDAY |
										DAY_FRIDAY | DAY_SATURDAY | DAY_SUNDAY;
	
	// Different columns of the Plan-table in the database:
	private final int id;		// id				INT		GENERATED BY DEFAULT AS IDENTITY
	private Customer customer;	// customer_id		INT		NOT NULL
	private Date startDate;		// start_date		DATE	NOT NULL
	private Date endDate;		// end_date			DATE
	private int daysOfWeek;		// days_of_week		INT		NOT NULL
	private double sumIncome;	// sum_income		DOUBLE	NOT NULL
	private double sumOutcome; 	// sum_outcome		DOUBLE	NOT NULL
	
	// This represents all of the dishes linked to this plan:
	private final PlanDishList dishes = new PlanDishList(this);
	
	/**
	 * Creates a new instance of Plan
	 * @param manager The DatabaseManager for a connection to a database
	 * @param id Primary key of the plan
	 */
	protected Plan(DatabaseManager manager, int id) {
		super(manager);
		this.id = id;
	}
	
	/**
	 * Create a new default row in the Plan-table.
	 * @param manager	The DatabaseManager for a connection to a database
	 * @param customer	The Customer owning the plan
	 * @return			A instance of a Plan
	 * @throws SQLException
	 */
	public static Plan createDefault(DatabaseManager manager, Customer customer) throws SQLException {
		String sql = "INSERT INTO Plan (customer_id, start_date, end_date, days_of_week, sum_income, sum_outcome) " +
						"VALUES (" + customer.getId() + ", '2000-01-01', NULL, 0, 0, 0)";
		Plan ret = null;
		try (PreparedStatement ps = manager.prepareStatement(sql, "id")) {
			ps.executeUpdate();
			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					ret = manager.getPlan(rs.getInt(1));
				}
			}
		}
		return ret;
	}
	
	@Override
	public void fetch() throws SQLException {
		super.fetch();
		String sql = "SELECT customer_id, start_date, end_date, days_of_week, sum_income, sum_outcome FROM Plan WHERE id = " + id;
		try (PreparedStatement ps = getManager().prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					this.customer = getManager().getCustomer(rs.getInt(1));
					this.startDate = rs.getDate(2);
					this.endDate = rs.getDate(3);
					this.daysOfWeek = rs.getInt(4);
					this.sumIncome = rs.getDouble(5);
					this.sumOutcome = rs.getDouble(6);
				}
			}
		}
	}
	
	/**
	 * Commit the content of this row to the database-table. <br/>
	 * Note: Added and removed dishes to a plan are auto-committed, and
	 * 			does not require a execution of this method.
	 */
	@Override
	public void commit() throws SQLException {
		super.commit();
		String sql = "UPDATE Plan SET ";
		sql += "customer_id = " + customer.getId();
		sql += ", start_date = '" + startDate.toString() + "'";
		sql += ", end_date = '" + endDate.toString() + "'";
		sql += ", days_of_week = " + daysOfWeek;
		sql += ", sum_income = " + sumIncome;
		sql += ", sum_outcome = " + sumOutcome;
		sql += " WHERE id = " + id;
		try (PreparedStatement ps = getManager().prepareStatement(sql)) {
			ps.executeUpdate();
		}
	}
	
	@Override
	public void remove() throws SQLException {
		super.remove();
		String sql = "DELETE FROM Plan WHERE id = " + id;
		try (PreparedStatement ps = getManager().prepareStatement(sql)) {
			ps.executeUpdate();
		}
	}

	/**
	 * Gets the ID.
	 * @return The ID
	 */
	public int getId() {
		super.tryFetch();
		return id;
	}

	/**
	 * Gets the Customer requesting this plan.
	 * @return The Customer
	 */
	public Customer getCustomer() {
		super.tryFetch();
		return customer;
	}

	/**
	 * Sets the Customer requesting this plan.
	 * @param customer The new Customer
	 */
	public void setCustomer(Customer customer) {
		assert(customer != null); 			// NOT NULL
		super.setChanged();
		this.customer = customer;
	}

	/**
	 * Gets the start date.
	 * @return The start date
	 */
	public Date getStartDate() {
		super.tryFetch();
		return startDate;
	}

	/**
	 * Sets the new start date.
	 * @param startDate The new start date
	 */
	public void setStartDate(Date startDate) {
		assert(startDate != null); 			// NOT NULL
		super.setChanged();
		this.startDate = startDate;
	}
	
	/**
	 * Gets the days of week to be delivered on.
	 * @return The days of week to be delivered on
	 */
	public int getDaysOfWeek() {
		super.tryFetch();
		return daysOfWeek;
	}

	/**
	 * Sets the days of week to be delivered on.
	 * @param daysOfWeek The new days of week to be delivered on
	 */
	public void setDaysOfWeek(int daysOfWeek) {
		assert (daysOfWeek >= 0);
		assert (daysOfWeek <= MAX_DAY);
		super.setChanged();
		this.daysOfWeek = daysOfWeek;
	}
	
	/**
	 * Checks if this will be delivered on the specific days.
	 * @param daysOfWeek The specific days
	 * @return <i>true</i> if all of the specific days are to be delivered on, otherwise <i>false</i>.
	 */
	public boolean isDeliveredOn(int daysOfWeek) {
		return (getDaysOfWeek() & daysOfWeek) == daysOfWeek;
	}
	
	/**
	 * Sets some specific days to be delivered on
	 * @param daysOfWeek Some specific days
	 */
	public void setDeliveredOn(int daysOfWeek) {
		setDaysOfWeek(getDaysOfWeek() | daysOfWeek);
	}
	
	/**
	 * Sets some specific days not to be delivered on
	 * @param daysOfWeek Some specific days
	 */
	public void setNotDeliveredOn(int daysOfWeek) {
		setDaysOfWeek(getDaysOfWeek() & (~daysOfWeek));
	}

	/**
	 * Gets the end date.
	 * @return The end date
	 */
	public Date getEndDate() {
		super.tryFetch();
		return endDate;
	}

	/**
	 * Sets the new end date.
	 * @param endDate The new end date
	 */
	public void setEndDate(Date endDate) {
		super.setChanged();
		this.endDate = endDate;
	}

	/**
	 * Gets the sum income from this plan.
	 * @return Sum of the income
	 */
	public double getSumIncome() {
		super.tryFetch();
		return sumIncome;
	}

	/**
	 * Sets the new sum income from this plan.
	 * @param sumIncome New sum of the income
	 */
	public void setSumIncome(double sumIncome) {
		super.setChanged();
		this.sumIncome = sumIncome;
	}

	/**
	 * Gets the sum outcome from this plan.
	 * @return Sum of the outcome
	 */
	public double getSumOutcome() {
		super.tryFetch();
		return sumOutcome;
	}

	/**
	 * Sets the new sum outcome from this plan.
	 * @param sumOutcome New sum of the outcome
	 */
	public void setSumOutcome(double sumOutcome) {
		super.setChanged();
		this.sumOutcome = sumOutcome;
	}
	
	/**
	 * Gets the list of dishes linked to this plan.
	 * @return A list of PlanDish-links
	 */
	public PlanDishList getDishes() {
		assert(!isRemoved());
		return dishes;
	}
	
	public boolean equals(Object other){
		if(other == null || !(other instanceof Plan)) {
			return false;
		}
		return getId() == ((Plan)other).getId();
	}
	
	@Override
	public String toString() {
		super.tryFetch(); // We use customerId instead of customer.toString(), otherwise would lead to a infinite loop.
		return "Planid = " + id  + " From " + startDate.toString()
				+ " to " + endDate.toString() + "; dishes: " + dishes.toString();
	
	}
}
	

