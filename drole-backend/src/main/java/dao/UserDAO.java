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
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}
		
		close();

		return status;
	}

	public static boolean deleteUser(int id) {
		boolean status = false;

		try {
			Statement st = connection.createStatement();
			String query = "DELETE FROM users WHERE user_id = " + id;
			st.executeUpdate(query);
			st.close();
			status = true;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return status;
	}
	
	
	
	public static User getUser(int id){
		User user = null;
		try{
			Statement st = connection.createStatement();
			String query = "SELECT * FROM livros where id = " + id;
			ResultSet rs = st.executeQuery(query);


			if(rs.next()){
				user = new User(rs.getInt("user_id"), rs.getString("username"), rs.getInt("user_type"),
						rs.getString("photo_path"), rs.getString("email"), rs.getString("hashpassword"),
						rs.getString("profile_localization"), rs.getString("profile_description"),
						rs.getString("profile_name"));
			}

			st.close();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return user;
	}
	
	

	public static User[] getUsers() {
		User[] users = null;
		try {
			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
			System.err.println(e.getMessage());
		}

		return users;
	}
}