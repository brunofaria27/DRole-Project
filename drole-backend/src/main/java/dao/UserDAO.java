package dao;

import java.sql.*;

import model.User;

public class UserDAO extends DAO {

	public static boolean createUser(User user) {
		boolean status = false;

		try {
			PreparedStatement stmt = null;
			connect();

			String query = "INSERT INTO users(username, user_type, photo_path, email, hashpassword, profile_localization, profile_description, profile_name, user_likes)"
					+ " VALUES (?, ?, '../images/noimg.png', ?, ?, ?, null, null, 0);";
			stmt = connection.prepareStatement(query);
			stmt.setString(1, user.getUsername());
			stmt.setInt(2, user.getUser_type());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getHashPassword());
			stmt.setString(5, user.getProfile_name());
			stmt.executeUpdate();
			
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
			PreparedStatement stmt = null;
			connect();

			String query = "UPDATE users SET username = ?, photo_path = ?, profile_localization = ?, profile_description = ?"
			+ ", profile_name = ? WHERE user_id = ?;";

			stmt = connection.prepareStatement(query);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPhoto_path());
			stmt.setString(3, user.getProfile_localization());
			stmt.setString(4, user.getProfile_description());
			stmt.setString(5, user.getProfile_name());
			stmt.setInt(6, user.getUser_id());
			stmt.executeUpdate();
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
	
	public static User[] getUsersCompleteInfo(int current_id) {
		User[] users = null;
		
		try {
			connect();
			Statement st = DAO.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ResultSet rs = st.executeQuery("SELECT * FROM USERS");
			
			
			if (rs.next()) {
				rs.last();
				users = new User[rs.getRow()];
				rs.beforeFirst();

				for (int i = 0; rs.next(); i++) {
					boolean likesReceived = false;
					
					Statement st2 = connection.createStatement();
					String queryLikes = "SELECT rate FROM score WHERE valuer_id = " + current_id + " AND valued_id = " + rs.getInt("user_id");
					ResultSet rsLikes = st2.executeQuery(queryLikes);

					if (rsLikes.next()) {
						likesReceived = rsLikes.getBoolean("rate");
					}

					st.close();
					
					users[i] = new User(rs.getInt("user_id"), rs.getString("username"), rs.getInt("user_type"),
							rs.getString("photo_path"), rs.getString("email"), rs.getString("hashpassword"),
							rs.getString("profile_localization"), rs.getString("profile_description"),
							rs.getString("profile_name"), rs.getInt("user_likes"), likesReceived);
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
	
	

	public static User[] getUsers() {
		User[] users = null;
		try {
			connect();
			Statement st = DAO.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

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