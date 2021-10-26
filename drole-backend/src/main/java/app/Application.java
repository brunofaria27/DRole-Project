package app;

import static spark.Spark.*;

import model.Events;
import services.LoginService;
import services.UserService;

public class Application {
	private static Events eventTest = new Events(1, "Baile do teste", 3, "Pop", 18, 5, "Pendente", "2022-11-11 00:00:00");
	private static UserService userService = new UserService();
	public static LoginService loginService = new LoginService();
	
	public static void main(String[] args) {
		port(4568);
		get("/eventos", (request, response) -> eventTest.toString());
		System.out.println(eventTest.toString());
		
		post("/user/", (request, response) -> userService.add(request, response));
		post("/user/", (request, response) -> userService.update(request, response));
		get("/user/", (request, response) -> userService.getAll(request, response));
		get("/user/:id", (request, response) -> userService.get(request, response));
		get("/user/:id", (request, response) -> userService.remove(request, response));
		
		post("/login/", (request, response) -> loginService.tryLogin(request, response));
	}
}