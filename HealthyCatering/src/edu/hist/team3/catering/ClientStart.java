package edu.hist.team3.catering;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import edu.hist.team3.catering.database.DatabaseManager;
import edu.hist.team3.catering.database.manager.Services;
import edu.hist.team3.catering.gui.LoginFrame;

public class ClientStart {

	/**
	 * This is the main function of the program. Everything will start from here.
	 */
	public static void main(String[] args) {
		DatabaseManager databaseManager = null;
		try {
			databaseManager = new DatabaseManager("jdbc:derby://db.stud.aitel.hist.no:1527/13ing1gr3", "team3", "Ikj721");
			new LoginFrame(new Services(databaseManager)).setVisible(true);
		}
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Unable to connect to the database, try again");
		}
	}

}
