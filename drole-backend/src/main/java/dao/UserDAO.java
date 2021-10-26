package dao;

import java.sql.*;

import model.User;

public class UserDAO extends DAO {

	public static boolean createUser(User user) {
		boolean status = false;

		try {
			connect();
			Statement st = connection.createStatement();
			String query = "INSERT INTO users(username, user_type, photo_path, email, hashpassword, "
					+ "profile_localization, profile_description,profile_name) VALUES " + "('" + user.getUsername()
					+ "'," + user.getUser_type() + "," + "NULL" + ",'" + user.getEmail() + "','"
					+ user.getHashPassword() + "'," + "NULL" + "," + "NULL" + ",'" + user.getProfile_name() + "');";

			st.executeQuery(query);
			st.close();
			status = true;
		} catch (SQLException e) {
			close();
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}

		close();

		return status;
	}

	public static boolean deleteUser(int id) {
		boolean status = false;

		try {
			connect();
			Statement st = connection.createStatement();
			String query = "DELETE FROM users WHERE user_id = " + id;
			st.executeUpdate(query);
			st.close();
			status = true;

		} catch (SQLException e) {
			close();
			throw new RuntimeException(e);
		}

		close();
		return status;
	}

	public static boolean updateUser(User user) {
		boolean status = false;

		try {
			connect();
			Statement st = connection.createStatement();
			String query = "UPDATE livros SET " 
					+ "username = '" + user.getUsername()
					+ "', user_type = " + user.getUser_type() 
					+ ", photo_path = '" + user.getPhoto_path() 
					+ "', email = '" + user.getEmail() 
					+ "', hashpassword = '" + user.getHashPassword()
					+ "', profile_localization = '" + user.getProfile_localization() 
					+ "', 'profile_description = '" + user.getProfile_description()
					+ "', profile_name = '" + user.getProfile_name()
					+ " WHERE user_id = " + user.getUser_id();

			st.executeUpdate(query);
			st.close();
			status = true;

		} catch (SQLException e) {
			close();
			throw new RuntimeException(e);
		}

		close();
		return status;
	}

	public static User getUser(int id) {
		User user = null;
		try {
			connect();
			Statement st = connection.createStatement();
			String query = "SELECT * FROM users where user_id = " + id;
			ResultSet rs = st.executeQuery(query);

			if (rs.next()) {
				user = new User(rs.getInt("user_id"), rs.getString("username"), rs.getInt("user_type"),
						rs.getString("photo_path"), rs.getString("email"), rs.getString("hashpassword"),
						rs.getString("profile_localization"), rs.getString("profile_description"),
						rs.getString("profile_name"));
			}

			st.close();

		} catch (Exception e) {
			close();
			System.out.println(e.getMessage());
		}

		close();
		return user;
	}

	public static User[] getUsers() {
		User[] users = null;
		try {
			connect();
			Statement st = DAO.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM users");

			if (rs.next()) {
				rs.last();
				users = new User[rs.getRow()];
				rs.beforeFirst();

				for (int i = 0; rs.next(); i++) {
					users[i] = new User(rs.getInt("user_id"), rs.getString("username"), rs.getInt("user_type"),
							rs.getString("photo_path"), rs.getString("email"), rs.getString("hashpassword"),
							rs.getString("profile_localization"), rs.getString("profile_description"),
							rs.getString("profile_name"));
				}
			}

			st.close();
		} catch (Exception e) {
			close();
			System.err.println(e.getMessage());
		}

		close();
		return users;
	}
}