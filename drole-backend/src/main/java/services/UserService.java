package services;

import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONArray;
import org.json.JSONException;

import dao.ScoreDAO;
import dao.UserDAO;
import lib.Recommender;
import model.User;
import spark.*;

public class UserService extends UserDAO {

	public Object add(Request request, Response response) {

		String username = request.queryParams("username");
		int userType = Integer.parseInt(request.queryParams("user_type"));
		String email = request.queryParams("email");
		String password = request.queryParams("password");
		
		MessageDigest md;
		String newPass = "";
		try {
			md = MessageDigest.getInstance("MD5");
			BigInteger hash = new BigInteger(1, md.digest(password.getBytes()));
			newPass = hash.toString(16);
		} catch (NoSuchAlgorithmException e) {}

		User user = new User(username, userType, email, newPass);

		User[] existentUsers = UserDAO.getUsers();

		for (User u : existentUsers) {
			if (u.getEmail().equals(email) || u.getUsername().equals(username)) {
				response.status(409);
				return "Email or usename already exist";
			}
		}

		UserDAO.createUser(user);

		response.status(201);

		return "OK";
	}

	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		
		User user = UserDAO.getUser(id);

		if (user != null) {
			response.header("Content-Type", "application/xml");
			response.header("Content-Encoding", "UTF-8");

			return "<user>\n" + "\t<id>" + user.getUser_id() + "</id>\n" + "\t<username>" + user.getUsername()
					+ "</username>\n" + "\t<userType>" + user.getUser_type() + "</userType>\n" + "\t<userPhoto>"
					+ user.getPhoto_path() + "</userPhoto>\n" + "\t<email>" + user.getEmail() + "</email>\n"
					+ "\t<profileName>" + user.getProfile_name() + "</profileName>\n" + "\t<localization>"
					+ user.getProfile_localization() + "</localization>\n" + "\t<description>"
					+ user.getProfile_description() + "</description>\n" + "\t<userLikes>"
					+ user.getUser_likes() + "</userLikes>" + "\t<currentLike>"
					+ "false" + "</currentLike>" + "</user>\n";
		} else {
			response.status(404); // 404 Not found
			return "User " + id + " não encontrado.";
		}

	}

	public Object update(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));

		User user = UserDAO.getUser(id);

		if (user != null) {
//			user.setUsername(request.queryParams("username"));
			user.setPhoto_path(request.queryParams("photo_path"));
			user.setProfile_name(request.queryParams("profile_name"));
			user.setProfile_localization(request.queryParams("profile_localization"));
			user.setProfile_description(request.queryParams("profile_description"));

			UserDAO.updateUser(user);
			return id;
		} else {
			response.status(404);
			return "User nÃ£o encontrado";
		}
	}

	public Object remove(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));

		User user = UserDAO.getUser(id);

		if (user != null) {

			UserDAO.deleteUser(id);
			response.status(200); // success
			return id;
		} else {
			response.status(404); // 404 Not found
			return "User nÃ£o encontrado.";
		}
	}

	public Object getAll(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		
		StringBuffer returnValue = new StringBuffer("<users type=\"array\">");

		for (User user : UserDAO.getUsers()) {
			returnValue.append("<user>\n" + "\t<id>" + user.getUser_id() + "</id>\n" + "\t<username>"
					+ user.getUsername() + "</username>\n" + "\t<userType>" + user.getUser_type() + "</userType>\n"
					+ "\t<userPhoto>" + user.getPhoto_path() + "</userPhoto>\n" + "\t<email>" + user.getEmail()
					+ "</email>\n" + "\t<profileName>" + user.getProfile_name() + "</profileName>\n"
					+ "\t<localization>" + user.getProfile_localization() + "</localization>\n" + "\t<description>"
					+ user.getProfile_description() + "</description>\n" + "\t<userLikes>"
					+ user.getUser_likes() + "</userLikes>\n" + "\t<currentLike>"
					+ ScoreDAO.hasScore(id, user.getUser_id()) + "</currentLike>\n" + "</user>\n");
		}

		returnValue.append("</users>");
		response.header("Content-Type", "application/xml");
		response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}

	public Object getMusicians(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<users type=\"array\">");

		for (User user : UserDAO.getUsers()) {
			if (user.getUser_type() == 3) {
				returnValue.append("<user>\n" + "\t<id>" + user.getUser_id() + "</id>\n" + "\t<username>"
						+ user.getUsername() + "</username>\n" + "\t<userType>" + user.getUser_type() + "</userType>\n"
						+ "\t<userPhoto>" + user.getPhoto_path() + "</userPhoto>\n" + "\t<email>" + user.getEmail()
						+ "</email>\n" + "\t<profileName>" + user.getProfile_name() + "</profileName>\n"
						+ "\t<localization>" + user.getProfile_localization() + "</localization>\n" + "\t<description>"
						+ user.getProfile_description() + "</description>\n" + "\t<userLikes>"
						+ user.getUser_likes() + "\t</userLikes>"+ "</user>\n");
			}
		}

	
		returnValue.append("</users>");
		response.header("Content-Type", "application/xml");
		response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
	
	public Object SistemaInteligente(Request request, Response response) {
		int event_capacity = Integer.parseInt(request.queryParams("event_capacity"));
		int event_formality = Integer.parseInt(request.queryParams("event_formality"));
		int event_target = Integer.parseInt(request.queryParams("event_target"));
		int event_hour = Integer.parseInt(request.queryParams("event_hour"));
		int event_price = Integer.parseInt(request.queryParams("event_price"));
		
		String hard = "[{\"type\":\"\", \"value_capacity\":" + event_capacity + ", \"value_formality\":" + event_formality + ", \"value_target\":" + event_target + ", \"value_hour\":" + event_hour + ", \"value_price\":" + event_price + "}]";
		Recommender recommender = new Recommender();
		String recomenda = recommender.classify(hard);
		JSONObject jsonObject = new JSONObject(recomenda);
//		System.out.println(jsonObject.get("result"));
		JSONArray jsonArray = (JSONArray) jsonObject.get("result");
//		System.out.println(((JSONObject) jsonArray.get(0)).get("Scored Labels"));
		
		return ((JSONObject) jsonArray.get(0)).get("Scored Labels");
	}

	public Object getEventType(Request request, Response response) {
		return SistemaInteligente(request, response);
	}

}

