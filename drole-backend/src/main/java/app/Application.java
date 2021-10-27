package app;

import static spark.Spark.*;

import services.LoginService;
import services.UserService;
import services.EventsService;
import services.ScoreService;

public class Application {
	private static UserService userService = new UserService();
	public static LoginService loginService = new LoginService();
	public static EventsService eventService = new EventsService();
	public static ScoreService scoreService = new ScoreService();
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
		get("/eventosR/:id", (request, response) -> eventService.remove(request, response));
		
		//post("/score/", (request, response) -> scoreService.add(request, response));
		get("/scoreUp/:id", (request, response) -> scoreService.update(request, response));
		get("/scores/", (request, response) -> scoreService.getAll(request, response));
		get("/score/:id", (request, response) -> scoreService.get(request, response));
		get("/likes/:id", (request, response) -> scoreService.getLikes(request, response));
		get("/scoreR/:id", (request, response) -> scoreService.remove(request, response));
	}
}