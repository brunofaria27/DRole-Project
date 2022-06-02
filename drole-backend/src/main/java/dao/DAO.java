package dao;

import java.sql.*;

public class DAO {
	public static Connection connection;

	public static boolean connect() {
		final String driverName = "*";
		final String serverName = "*";
		final String database = "*";

		final int port = *;
		
		final String url = "*;
		final String username = "*";
		final String password = "*";

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
	
	public static boolean close() {
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
