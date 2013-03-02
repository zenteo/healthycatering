package edu.hist.team3.catering.database;

import java.sql.SQLException;

public abstract class DatabaseRow {
	public static final int STATUS_NONE = 0;	//000
	public static final int STATUS_FETCHED = 1;	//001
	public static final int STATUS_CHANGED = 3;	//011
	public static final int STATUS_REMOVED = 4;	//100
	
	private DatabaseManager manager;
	private int status = 0;
	
	public DatabaseRow(DatabaseManager connection) {
		this.manager = connection;
	}
	
	public DatabaseManager getManager() {
		return manager;
	}
	
	public void setManager(DatabaseManager manager) {
		this.manager = manager;
	}
	
	public boolean isFetched() {
		return (status & STATUS_FETCHED) != 0;
	}
	
	public boolean isChanged() {
		return status == STATUS_CHANGED;
	}
	
	public boolean isRemoved() {
		return status == STATUS_REMOVED;
	}
	
	/**
	 * Removes this row from the database-table.
	 */
	public void remove() throws SQLException {
		setRemoved();
	}
	
	/**
	 * Fetches the content of this row from the database-table.
	 */
	public void fetch() throws SQLException {
		assert(status != STATUS_REMOVED);
		status = STATUS_FETCHED;
	}

	/**
	 * Commits the content of this row to the database-table.
	 */
	public void commit() throws SQLException {
		assert(status != STATUS_REMOVED);
		status = STATUS_FETCHED;
	}
	
	/**
	 * Tries to fetch() if it hasn't been done already.
	 */
	protected void tryFetch() {
		assert(status != STATUS_REMOVED);
		try {
			if (!isFetched()) {
				fetch();
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Sets the status of this row to changed.
	 */
	protected void setChanged() {
		assert(status != STATUS_REMOVED);
		tryFetch();
		status = STATUS_CHANGED;
	}
	
	/**
	 * Sets the status of this row to removed.
	 */
	protected void setRemoved() {
		status = STATUS_REMOVED;
	}
}
