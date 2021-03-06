package edu.hist.team3.catering.database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Delivery extends DatabaseRow{
	public static short STATUS_NONE = 0;
	public static short STATUS_COOKED = 1;
	public static short STATUS_DELIVERED = 2;
	
	private final int id;		// id			INT		    GENERATED BY DEFAULT AS IDENTITY
	private Plan plan;			// plan_id		INT		    NOT NULL
	private Date date;			// date			DATE        NOT NULL
	private short status;		// status		SMALLINT    NOT NULL
	private double sumIncome;	// sum_income		DOUBLE    NOT NULL
	private double sumOutcome;	// sum_outcome		DOUBLE    NOT NULL
	
	public Delivery(DatabaseManager connection, int id) {
		super(connection);
		this.id = id;
	}
	
	/**
	 * Create a new default row in the Delivery-table.
	 * @param manager	The DatabaseManager for a connection to a database
	 * @param plan		The plan
	 * @return A instance of Delivery
	 * @throws SQLException
	 */
	public static Delivery createDefault(DatabaseManager manager, Plan plan) throws SQLException {
		String sql = "INSERT INTO Delivery (plan_id, date, status, sum_income, sum_outcome)" +
				"VALUES (" + plan.getId() + ", '2000-01-01', 0, 0, 0)";
		Delivery ret = null;
		try (PreparedStatement ps = manager.prepareStatement(sql, "id")) {
			ps.executeUpdate();
			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					ret = manager.getDelivery(rs.getInt(1));
				}
			}
		}
		return ret;
	}
	
	@Override
	public void fetch() throws SQLException {
		super.fetch();
		String sql = "SELECT plan_id, date, status, sum_income, sum_outcome FROM Delivery WHERE id = " + id;
		try (PreparedStatement ps = getManager().prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					this.plan = getManager().getPlan(rs.getInt(1));
					this.date = rs.getDate(2);
					this.status = rs.getShort(3);
					this.sumIncome = rs.getDouble(4);
					this.sumOutcome = rs.getDouble(5);
				}
			}
		}
	}
	
	@Override
	public void commit() throws SQLException {
		super.commit();
		String sql = "UPDATE Delivery SET ";
		sql += "plan_id = " + plan.getId();
		sql += ", date = '" + date.toString() + "'";
		sql += ", status = " + status;
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

	/**
	 * Gets the ID.
	 * @return The ID
	 */
	public int getId() {
		super.tryFetch();
		return id;
	}

	/**
	 * Gets the plan.
	 * @return The plan
	 */
	public Plan getPlan() {
		super.tryFetch();
		return plan;
	}

	/**
	 * Sets the plan to a new value.
	 * @param plan The new plan
	 */
	public void setPlan(Plan plan) {
		assert(plan != null);	// NOT NULL
		super.setChanged();
		this.plan = plan;
	}

	/**
	 * Gets the date.
	 * @return The date
	 */
	public Date getDate() {
		super.tryFetch();
		return date;
	}
	
	/**
	 * Sets the date to a new value.
	 * @param date The new date
	 */
	public void setDate(Date date) {
		assert(date != null);	// NOT NULL
		super.setChanged();
		this.date = date;
	}

	/**
	 * Gets the status.
	 * @return The status
	 */
	public short getStatus() {
		super.tryFetch();
		return status;
	}

	/**
	 * Sets the status to a new value.
	 * @param status The new status
	 */
	public void setStatus(short status) {
		super.setChanged();
		this.status = status;
	}
	
	/**
	 * Gets the sum income
	 * @return the sum income
	 */
	public double getSumIncome() {
		super.tryFetch();
		return sumIncome;
	}
	
	/**
	 * Sets the sum income
	 * @param value the new sum income
	 */
	public void setSumIncome(double value) {
		super.setChanged();
		this.sumIncome = value;
	}

	/**
	 * Gets the sum outcome
	 * @return the sum outcome
	 */
	public double getSumOutcome() {
		super.tryFetch();
		return sumOutcome;
	}
	
	/**
	 * Sets the sum outcome
	 * @param value the new sum outcome
	 */
	public void setSumOutcome(double value) {
		super.setChanged();
		this.sumOutcome = value;
	}
	
	/**
	 * Cooks the delivery
	 */
	public void cook() {
		if (getStatus() != Delivery.STATUS_NONE)
			return;
		setStatus(Delivery.STATUS_COOKED);
		super.setChanged();
		sumOutcome = getPlan().cook();
	}
	
	/**
	 * Delivers the delivery
	 */
	public void deliver() {
		if (getStatus() == Delivery.STATUS_DELIVERED)
			return;
		setStatus(Delivery.STATUS_DELIVERED);
		super.setChanged();
		sumIncome = getPlan().deliver();
	}
}
