package app;

import static spark.Spark.*;

import model.Events;
import services.LoginService;
import services.UserService;

public class Application {
	private static UserService userService = new UserService();
	private static LoginService loginService = new LoginService();
	

	public static void main(String[] args) {

		port(4568);
		
		options("/*", (request, response) -> {

			String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
			if (accessControlRequestHeaders != null) {
				response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
			}

			String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
			if (accessControlRequestMethod != null) {
				response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
			}

			return "OK";
		});

		before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
	

		post("/user/create", (request, response) -> userService.add(request, response));
		post("/user/", (request, response) -> userService.update(request, response));
		get("/user/", (request, response) -> userService.getAll(request, response));
		get("/user/:id", (request, response) -> userService.get(request, response));
		get("/user/remove/:id", (request, response) -> userService.remove(request, response));

		get("/login/", (request, response) -> loginService.tryLogin(request, response)); // Avaliar método
	}
}