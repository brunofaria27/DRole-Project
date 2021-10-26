package app;

import static spark.Spark.*;

import model.Events;
import services.LoginService;
import services.UserService;
import services.EventsService;

public class Application {
	//private static Events eventTest = new Events(1, "Baile do teste", 3, "Pop", 18, 5, "Pendente", "2022-11-11 00:00:00");
	private static UserService userService = new UserService();
	public static LoginService loginService = new LoginService();
	public static EventsService eventService = new EventsService();
	public static void main(String[] args) {
		port(4568);
		
		
		post("/user/", (request, response) -> userService.add(request, response));
		post("/user/", (request, response) -> userService.update(request, response));
		get("/user/", (request, response) -> userService.getAll(request, response));
		get("/user/:id", (request, response) -> userService.get(request, response));
		get("/user/:id", (request, response) -> userService.remove(request, response));
		
		post("/login/", (request, response) -> loginService.tryLogin(request, response));
		
		post("/eventos/", (request, response) -> eventService.add(request, response));
		post("/eventos/", (request, response) -> eventService.update(request, response));
		get("/eventos/", (request, response) -> eventService.getAll(request, response));
		get("/eventos/:id", (request, response) -> eventService.get(request, response));
		get("/eventos/:id", (request, response) -> eventService.remove(request, response));
	}
}