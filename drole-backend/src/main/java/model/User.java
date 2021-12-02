package model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
	private int user_id;
	private String username;
	private int user_type;
	private String photo_path;
	private String email;
	private String hashPassword;
	private String profile_localization;
	private String profile_description;
	private String profile_name;
	private int user_likes;
	private boolean like_received;

	public User(String username, int user_type, String photo_path, String email, String hashPassword,
			String profile_localization, String profile_description, String profile_name, int user_likes) {
		this.username = username;
		this.user_type = user_type;
		this.photo_path = photo_path;
		this.email = email;
		this.hashPassword = hashPassword;
		this.profile_localization = profile_localization;
		this.profile_description = profile_description;
		this.profile_name = profile_name;
		this.user_likes = user_likes;
		this.like_received = false;
	}
	
	
	public User(int user_id, String username, int user_type, String photo_path, String email, String hashPassword,
			String profile_localization, String profile_description, String profile_name, int user_likes,
			boolean like_received) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.user_type = user_type;
		this.photo_path = photo_path;
		this.email = email;
		this.hashPassword = hashPassword;
		this.profile_localization = profile_localization;
		this.profile_description = profile_description;
		this.profile_name = profile_name;
		this.user_likes = user_likes;
		this.like_received = like_received;
	}




	public User(int user_id, String username, int user_type, String photo_path, String email, String hashPassword,
			String profile_localization, String profile_description, String profile_name, int user_likes) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.user_type = user_type;
		this.photo_path = photo_path;
		this.email = email;
		this.hashPassword = hashPassword;
		this.profile_localization = profile_localization;
		this.profile_description = profile_description;
		this.profile_name = profile_name;
		this.user_likes = user_likes;
		this.like_received = false; 
	}


	public User(String username, int user_type, String email, String hashPassword) {
		super();
		this.username = username;
		this.user_type = user_type;
		this.email = email;
		this.hashPassword = hashPassword;
	}



	public User() {
		this("", 0, "", "", "", "", "", "", 0);
	}

	public int getUser_id() {
		return user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getUser_type() {
		return user_type;
	}

	public void setUser_type(int user_type) {
		this.user_type = user_type;
	}

	public String getPhoto_path() {
		return photo_path;
	}

	public void setPhoto_path(String photo_path) {
		this.photo_path = photo_path;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHashPassword() {
		return hashPassword;
	}

	public void setHashPassword(String hashPassword) {
		MessageDigest md;
		String newPass = "";
		try {
			md = MessageDigest.getInstance("MD5");
			BigInteger hash = new BigInteger(1, md.digest(hashPassword.getBytes()));
			newPass = hash.toString(16);
		} catch (NoSuchAlgorithmException e) {}
		
		this.hashPassword = newPass;
	}

	public String getProfile_localization() {
		return profile_localization;
	}

	public void setProfile_localization(String profile_localization) {
		this.profile_localization = profile_localization;
	}

	public String getProfile_description() {
		return profile_description;
	}

	public void setProfile_description(String profile_description) {
		this.profile_description = profile_description;
	}

	public String getProfile_name() {
		return profile_name;
	}

	public void setProfile_name(String profile_name) {
		this.profile_name = profile_name;
	}

	public int getUser_likes() {
		return user_likes;
	}

	public void setUser_likes(int user_likes) {
		this.user_likes = user_likes;
	}

	public boolean getLike_received() {
		return like_received;
	}


	public void setLike_received(boolean like_received) {
		this.like_received = like_received;
	}


	@Override
	public String toString() {
		return "Users [user_id=" + user_id + ", username=" + username + ", user_type=" + user_type + ", photo_path="
				+ photo_path + ", email=" + email + ", hashPassword=" + hashPassword + ", profile_localization="
				+ profile_localization + ", profile_description=" + profile_description + ", profile_name="
				+ profile_name + "]";
	}

}
