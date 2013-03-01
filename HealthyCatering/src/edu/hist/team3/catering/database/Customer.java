package edu.hist.team3.catering.database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Customer extends DatabaseRow {
	private final int id; 		// id				INT				GENERATED BY DEFAULT AS IDENTITY
	private String firstName;	// first_name		VARCHAR(32)		NOT NULL
	private String lastName; 	// last_name		VARCHAR(32)		NOT NULL
	private String address; 	// address			VARCHAR(128)	NOT NULL
	private String phone;		// phone			VARCHAR(64)		NOT NULL
	private Date creationDate;	// creation_date	DATE			NOT NULL
	
	protected Customer(DatabaseManager manager, int id) {
		super(manager);
		this.id = id;
	}
	
	public static Customer createDefault(DatabaseManager manager) throws SQLException {
		String sql = "INSERT INTO Customer(first_name, last_name, address, phone, creation_date)" +
				"VALUES ('', '', '', '', '2013-03-01')";
		Customer ret = null;
		try (PreparedStatement ps = manager.prepareStatement(sql, "id")) {
			ps.executeUpdate();
			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					ret = new Customer(manager, rs.getInt(1));
				}
			}
		}
		return ret;
	}
	
	@Override
	public void fetch() throws SQLException {
		super.fetch();
		String sql = "SELECT first_name, last_name, address, phone, creation_date FROM Customer WHERE id = " + id;
		try (PreparedStatement ps = getManager().prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					this.firstName = rs.getString(1);
					this.lastName = rs.getString(2);
					this.address = rs.getString(3);
					this.phone = rs.getString(4);
					this.creationDate = rs.getDate(5);
				}
			}
		}
	}
	
	@Override
	public void commit() {
		//TODO: Give me a body!!!
	}
	
	@Override
	public void remove() throws SQLException {
		super.remove();
		String sql = "DELETE FROM Customer WHERE id = " + id;
		try (PreparedStatement ps = getManager().prepareStatement(sql)) {
			ps.executeUpdate();
		}
	}
	
	public int getId() {
		return id;
	}
	
	public String getFirstName() {
		super.tryFetch();
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		assert(firstName != null); 			// NOT NULL
		assert(firstName.length() <= 32); 	// Max length = 32
		super.setChanged();
		this.firstName = firstName;
	}
	
	public String getLastName() {
		super.tryFetch();
		return lastName;
	}
	
	public void setLastName(String lastName) {
		assert(lastName != null); 			// NOT NULL
		assert(lastName.length() <= 32); 	// Max length = 32
		super.setChanged();
		this.lastName = lastName;
	}
	
	public String getAddress() {
		super.tryFetch();
		return address;
	}
	
	public void setAddress(String address) {
		assert(lastName != null); 			// NOT NULL
		assert(lastName.length() <= 128); 	// Max length = 128
		super.setChanged();
		this.address = address;
	}
	
	public String getPhone() {
		super.tryFetch();
		return phone;
	}
	
	public void setPhone(String phone) {
		assert(phone != null); 			// NOT NULL
		assert(phone.length() <= 64); 	// Max length = 64
		super.setChanged();
		this.phone = phone;
	}
	
	public Date getCreationDate() {
		super.tryFetch();
		return creationDate;
	}
	
	public void setCreationDate(Date date) {
		assert(phone != null); 			// NOT NULL
		super.setChanged();
		this.creationDate = date;
	}
	
	public boolean equals(Object other){
		if(other == null || !(other instanceof Customer)) {
			return false;
		}
		return getId() == ((Customer)other).getId();
	}
	
	public String toString() {
		super.tryFetch();
		return "Customer[firstName = '" + firstName + "'; lastName = '" + lastName
				+ "'; address = '" + address + "'; phone = '" + phone + "'; creationDate = " + creationDate.toString() + "]";
	}
}