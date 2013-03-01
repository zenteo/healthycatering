package edu.hist.team3.catering.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class Delivery extends DatabaseRow {
	public static final int DAY_NONE = 0;
	public static final int DAY_MONDAY = 1;
	public static final int DAY_TUESDAY = 2;
	public static final int DAY_WEDNESDAY = 4;
	public static final int DAY_THURSDAY = 8;
	public static final int DAY_FRIDAY = 16;
	public static final int DAY_SATURDAY = 32;
	public static final int DAY_SUNDAY = 64;
	
	private final int id;		// id				INT		GENERATED BY DEFAULT AS IDENTITY
	private Customer customer;	// customer_id		INT		NOT NULL
	private Date startDate;		// start_date		DATE	NOT NULL
	private Date endDate;		// end_date			DATE
	private int daysOfWeek;		// days_of_week		INT		NOT NULL
	private int numDelivered;	// num_delivered	INT		NOT NULL
	private double sumIncome;	// sum_income		DOUBLE	NOT NULL
	private double sumOutcome; 	// sum_outcome		DOUBLE	NOT NULL
	private final DeliveryDishList dishes = new DeliveryDishList(this);
	
	/**
	 *  A representation of a delivery
	 * @param id Primary key of the delivery
	 */
	protected Delivery(DatabaseManager manager, int id) {
		super(manager);
		this.id = id;
	}
	
	public static Delivery createDefault(DatabaseManager manager, Customer customer) throws SQLException {
		String sql = "INSERT INTO Delivery (customer_id, start_date, end_date, days_of_week, num_delivered, sum_income, sum_outcome) " +
						"VALUES (" + customer.getId() + ", '2000-01-01', NULL, 0, 0, 0, 0)";
		Delivery ret = null;
		try (PreparedStatement ps = manager.prepareStatement(sql, "id")) {
			ps.executeUpdate();
			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					ret = new Delivery(manager, rs.getInt(1));
				}
			}
		}
		return ret;
	}
	
	@Override
	public void fetch() throws SQLException {
		super.fetch();
		String sql = "SELECT customer_id, start_date, end_date, days_of_week, num_delivered, sum_income, sum_outcome FROM Delivery WHERE id = " + id;
		try (PreparedStatement ps = getManager().prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					this.customer = getManager().getCustomer(rs.getInt(1));
					this.startDate = rs.getDate(2);
					this.endDate = rs.getDate(3);
					this.daysOfWeek = rs.getInt(4);
					this.numDelivered = rs.getInt(5);
					this.sumIncome = rs.getDouble(6);
					this.sumOutcome = rs.getDouble(7);
				}
			}
		}
	}
	
	@Override
	public void commit() throws SQLException {
		super.commit();
		String sql = "UPDATE Delivery SET ";
		sql += "customer_id = " + customer.getId();
		sql += ", start_date = '" + startDate.toString() + "'";
		sql += ", end_date = '" + endDate.toString() + "'";
		sql += ", days_of_week = " + daysOfWeek;
		sql += ", num_delivered = " + numDelivered;
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
		String sql = "DELETE FROM Delivery WHERE id = " + id;
		try (PreparedStatement ps = getManager().prepareStatement(sql)) {
			ps.executeUpdate();
		}
	}

	public int getId() {
		super.tryFetch();
		return id;
	}

	public Customer getCustomer() {
		super.tryFetch();
		return customer;
	}

	public void setCustomer(Customer customer) {
		assert(customer != null); 			// NOT NULL
		super.setChanged();
		this.customer = customer;
	}

	public Date getStartDate() {
		super.tryFetch();
		return startDate;
	}

	public void setStartDate(Date startDate) {
		assert(startDate != null); 			// NOT NULL
		super.setChanged();
		this.startDate = startDate;
	}
	
	public int getDaysOfWeek() {
		super.tryFetch();
		return daysOfWeek;
	}

	public void setDaysOfWeek(int daysOfWeek) {
		super.setChanged();
		this.daysOfWeek = daysOfWeek;
	}
	
	public boolean isDeliveredOn(int daysOfWeek) {
		return (getDaysOfWeek() & daysOfWeek) == daysOfWeek;
	}
	
	public void setDeliveredOn(int daysOfWeek) {
		setDaysOfWeek(getDaysOfWeek() | daysOfWeek);
	}
	
	public void setNotDeliveredOn(int daysOfWeek) {
		setDaysOfWeek(getDaysOfWeek() & (~daysOfWeek));
	}

	public Date getEndDate() {
		super.tryFetch();
		return endDate;
	}

	public void setEndDate(Date endDate) {
		super.setChanged();
		this.endDate = endDate;
	}

	public int getNumDelivered() {
		super.tryFetch();
		return numDelivered;
	}

	public void setNumDelivered(int numDelivered) {
		super.setChanged();
		this.numDelivered = numDelivered;
	}

	public double getSumIncome() {
		super.tryFetch();
		return sumIncome;
	}

	public void setSumIncome(double sumIncome) {
		super.setChanged();
		this.sumIncome = sumIncome;
	}

	public double getSumOutcome() {
		super.tryFetch();
		return sumOutcome;
	}

	public void setSumOutcome(double sumOutcome) {
		super.setChanged();
		this.sumOutcome = sumOutcome;
	}
	
	public DeliveryDishList getDishes() {
		assert(!isRemoved());
		return dishes;
	}
	
	public boolean equals(Object other){
		if(other == null || !(other instanceof Delivery)) {
			return false;
		}
		return getId() == ((Delivery)other).getId();
	}
	
	@Override
	public String toString() {
		super.tryFetch(); // We use customerId instead of customer.toString(), otherwise would lead to a infinite loop.
		return "Delivery[id = " + id + "; customerId = " + customer.getId() + "; startDate = " + startDate.toString()
				+ "; endDate = " + endDate.toString() + "; numDelivered = " + numDelivered + "; sumIncome = " + sumIncome + "; sumOutcome = " + sumOutcome + "; dishes = " + dishes.toString() + "]";
	
	}
}
	

