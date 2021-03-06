package app;

import static spark.Spark.*;

import lib.Recommender;
import services.LoginService;
import services.UserService;
import services.EventsService;
import services.ScoreService;

public class Application {
	private static UserService userService = new UserService();
	public static LoginService loginService = new LoginService();
	public static EventsService eventService = new EventsService();
	public static ScoreService scoreService = new ScoreService();
	public static Recommender recommender = new Recommender();

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
		put("/user/update/:id", (request, response) -> userService.update(request, response));
		get("/users/:id", (request, response) -> userService.getAll(request, response));
		get("/events/musicians", (request, response) -> userService.getMusicians(request, response));
		post("/events/musicians", (request, response) -> userService.SistemaInteligente(request, response));
		post("/events/create/si", (request, response) -> userService.getEventType(request, response));
		get("/user/:id", (request, response) -> userService.get(request, response));
		//delete("/user/remove/:id", (request, response) -> userService.remove(request, response));

		get("/login/", (request, response) -> loginService.tryLogin(request, response));
	
    	post("/events/create", (request, response) -> eventService.add(request, response));
    	get("/events/", (request, response) -> eventService.getAll(request, response));
    	get("/events/data", (request, response) -> eventService.getAllData(request, response));
    	put("/events/:id", (request, response) -> eventService.update(request, response));
    	get("/events/:id", (request, response) -> eventService.get(request, response));
    	delete("/events/:id", (request, response) -> eventService.remove(request, response));
    	
		post("/score/", (request, response) -> scoreService.add(request, response));
		put("/score/:id/:id2", (request, response) -> scoreService.update(request, response));
		get("/scores/", (request, response) -> scoreService.getAll(request, response));
		get("/score/:id/:id2", (request, response) -> scoreService.get(request, response));
		post("/score/remove", (request, response) -> scoreService.remove(request, response));
	}
}