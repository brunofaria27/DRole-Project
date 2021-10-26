package services;

import dao.UserDAO;
import model.User;
import spark.*;

public class UserService extends UserDAO{
	
	public Object add(Request request, Response response) {
		
		String username = request.queryParams("username");
		int userType = Integer.parseInt(request.queryParams("user_type"));
		String email = request.queryParams("email");
		String password = request.queryParams("password");
		
		User user = new User(username, userType, email, password);
		
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

			return "<user>\n" +
					"\t<id>" + user.getUser_id() + "</id>\n" +
					"\t<username>" + user.getUsername() + "</username>\n" +
					"\t<userType>" + user.getUser_type() + "</userType>\n" +
					"\t<userPhoto>" + user.getPhoto_path() + "</userPhoto>\n" +
					"\t<email>" + user.getEmail() + "</email>\n" +
					"\t<profileName>" + user.getProfile_name() + "</profileName>\n" +					
					"\t<localization>" + user.getProfile_localization() + "</localization>\n" +
					"\t<description>" + user.getProfile_description() + "</description>\n" +					
					"</user>\n";
		} else {
			response.status(404); // 404 Not found
			return "User " + id + " não encontrado.";
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
			return "Livro não encontrado.";
		}
	}
	
	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<users type=\"array\">");

		for (User user : UserDAO.getUsers()) {
			returnValue.append("<user>\n" +
					"\t<id>" + user.getUser_id() + "</id>\n" +
					"\t<username>" + user.getUsername() + "</username>\n" +
					"\t<userType>" + user.getUser_type() + "</userType>\n" +
					"\t<userPhoto>" + user.getPhoto_path() + "</userPhoto>\n" +
					"\t<email>" + user.getEmail() + "</email>\n" +
					"\t<profileName>" + user.getProfile_name() + "</profileName>\n" +					
					"\t<localization>" + user.getProfile_localization() + "</localization>\n" +
					"\t<description>" + user.getProfile_description() + "</description>\n" +					
					"</user>\n");
		}

		returnValue.append("</users>");
		response.header("Content-Type", "application/xml");
		response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}

}
