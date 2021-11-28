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
					+ "profile_localization, profile_description,profile_name, user_likes) VALUES " + "('" + user.getUsername()
					+ "'," + user.getUser_type() + "," + "NULL" + ",'" + user.getEmail() + "','"
					+ user.getHashPassword() + "'," + "NULL" + "," + "NULL" + ",'" + user.getProfile_name() + "0" + "');";

			st.executeUpdate(query);
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
			String query = "UPDATE users SET " + "username = '" + user.getUsername() + "', user_type = "
					+ user.getUser_type() + ", photo_path = '" + user.getPhoto_path() + "', email = '" + user.getEmail()
					+ "', hashpassword = '" + user.getHashPassword() + "', profile_localization = '"
					+ user.getProfile_localization() + "', profile_description = '" + user.getProfile_description()
					+ "', profile_name = '" + user.getProfile_name() + "' WHERE user_id = " + user.getUser_id();

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

	public static User getUser(int profile_id) {
		
		User user = null;
		try {
			connect();
			Statement st = DAO.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Statement st2 = DAO.connection.createStatement();
			String query = "UPDATE users SET user_likes = likes FROM (SELECT likes, valued_id FROM users JOIN (SELECT valued_id, COUNT (rate) AS \"likes\" FROM score WHERE rate = true GROUP BY valued_id) as likes_table ON user_id = " + profile_id + " AND user_id = valued_id) AS B WHERE B.valued_id = user_id";
			st2.executeUpdate(query);
			ResultSet rs = st.executeQuery("SELECT * FROM USERS WHERE user_id = " + profile_id);

			if (rs.next()) {
				user = new User(rs.getInt("user_id"), rs.getString("username"), rs.getInt("user_type"),
						rs.getString("photo_path"), rs.getString("email"), rs.getString("hashpassword"),
						rs.getString("profile_localization"), rs.getString("profile_description"),
						rs.getString("profile_name"), rs.getInt("user_likes"));
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
			Statement st2 = DAO.connection.createStatement();
			
			st2.executeUpdate("UPDATE users SET user_likes = likes FROM (SELECT likes, valued_id FROM users JOIN (SELECT valued_id, COUNT (rate) AS \"likes\" FROM score WHERE rate = true GROUP BY valued_id) as likes_table ON user_id = valued_id AND user_id = valued_id) AS B WHERE B.valued_id = user_id");
			
			ResultSet rs = st.executeQuery("SELECT * FROM USERS");
			
			
			if (rs.next()) {
				rs.last();
				users = new User[rs.getRow()];
				rs.beforeFirst();

				for (int i = 0; rs.next(); i++) {
					users[i] = new User(rs.getInt("user_id"), rs.getString("username"), rs.getInt("user_type"),
							rs.getString("photo_path"), rs.getString("email"), rs.getString("hashpassword"),
							rs.getString("profile_localization"), rs.getString("profile_description"),
							rs.getString("profile_name"), rs.getInt("user_likes"));
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