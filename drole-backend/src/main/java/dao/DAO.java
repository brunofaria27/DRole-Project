package dao;

import java.sql.*;

public class DAO {
	protected static Connection connection;

	protected static boolean connect() {
		final String driverName = "org.postgresql.Driver";
		final String serverName = "drole.postgres.database.azure.com";
		final String database = "drole";

		final int port = 5432;
		
		final String url = "jdbc:postgresql://" + serverName + ":" + port + "/" + database;
		final String username = "adm@drole";
		final String password = "drole@#2021";

		boolean status = false;

		try {
			Class.forName(driverName);
			connection = DriverManager.getConnection(url, username, password);
			status = (connection == null);
			System.out.println("Successfully connected to " + serverName + ":" + port);
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found: " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("Connection failed: " + e.getMessage());
		}
		
		return status;
	}
	
	protected static boolean close() {
		boolean status = false;
		
		try {
			connection.close();
			status = true;
			System.out.println("Successfully disconnected from DATABASE!");
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return status;
	}
}
