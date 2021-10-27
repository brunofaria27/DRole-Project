package app;

import static spark.Spark.*;

import model.Events;
import services.LoginService;
import services.UserService;

public class Application {
	private static Events eventTest = new Events(1, "Baile do teste", 3, "Pop", 18, 5, "Pendente",
			"2022-11-11 00:00:00");
	private static UserService userService = new UserService();
	public static LoginService loginService = new LoginService();
	

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
		
		get("/eventos", (request, response) -> eventTest.toString());
		System.out.println(eventTest.toString());
		
		post("/hello", (request, response) -> methodString(request.body()));
		

		post("/user/create", (request, response) -> userService.add(request, response)); // Avaliar método
		post("/user/", (request, response) -> userService.update(request, response));
		get("/user/", (request, response) -> userService.getAll(request, response));
		get("/user/:id", (request, response) -> userService.get(request, response));
		get("/user/remove/:id", (request, response) -> userService.remove(request, response));

		get("/login/", (request, response) -> loginService.tryLogin(request, response)); // Avaliar método
	}
	
	private static Object methodString(String string) {
		System.out.println(string);
		return null;
	}
}