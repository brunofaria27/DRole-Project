package services;

import dao.UserDAO;
import model.User;
import spark.Request;
import spark.Response;

public class LoginService {
	public Object tryLogin(Request request, Response response) {
		boolean status = false;
		User[] users = UserDAO.getUsers();
		
		String email = request.queryParams(":email");
		String password = request.queryParams(":password");
		
		for(User u : users) {
			if((u.getEmail().equals(email)) && (u.getHashPassword().equals(password))) {
				status = true;
				response.status(200);
				return status;
			}
		}
		
		response.status(404);
		return status;
	}
}
