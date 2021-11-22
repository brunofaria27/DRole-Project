package services;

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

		User user = new User(username, userType, email, password);

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
		
		//int current = Integer.parseInt(request.queryParams("current_id"));
		
		boolean status = true;
				//ScoreDAO.hasScore(current, id);

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
					+ status + "</currentLike>" + "</user>\n";
		} else {
			response.status(404); // 404 Not found
			return "User " + id + " n�o encontrado.";
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
			return "User não encontrado";
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
			return "User não encontrado.";
		}
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<users type=\"array\">");

		for (User user : UserDAO.getUsers()) {
			returnValue.append("<user>\n" + "\t<id>" + user.getUser_id() + "</id>\n" + "\t<username>"
					+ user.getUsername() + "</username>\n" + "\t<userType>" + user.getUser_type() + "</userType>\n"
					+ "\t<userPhoto>" + user.getPhoto_path() + "</userPhoto>\n" + "\t<email>" + user.getEmail()
					+ "</email>\n" + "\t<profileName>" + user.getProfile_name() + "</profileName>\n"
					+ "\t<localization>" + user.getProfile_localization() + "</localization>\n" + "\t<description>"
					+ user.getProfile_description() + "</description>\n" + "\t<userLikes>"
					+ user.getUser_likes() + "\t</userLikes>" + "</user>\n");
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
		
		String hard = "[{\"type\":\"\", \"value_capacity\":1, \"value_formality\": 2, \"value_target\": 4, \"value_hour\": 4, \"value_price\": 2}]";
		Recommender recommender = new Recommender();
		recommender.classify(hard);

		returnValue.append("</users>");
		response.header("Content-Type", "application/xml");
		response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}
