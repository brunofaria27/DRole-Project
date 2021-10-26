package app;

import static spark.Spark.get;
import static spark.Spark.port;

import java.util.Calendar;

import model.Events;
import model.Score;
import model.User;

public class Application {
	private static Calendar calendario = Calendar.getInstance();
	private static Events eventTest = new Events(1, "Baile do teste", 3, "Pop", 18, 5, "Pendente", "2022-11-11 00:00:00");
	private static Score scoreTest = new Score(1, 4, calendario.getTime(), 3);
	private static User userTest = new User("mit", 1, "foto", "mit@gmail.com", "asfgsdfh",
			"bh", "linda", "Laura Xavier");
	
	public static void main(String[] args) {
		port(4568);
		get("/eventos", (request, response) -> eventTest.toString());
		get("/score", (request, response) -> scoreTest.toString());
		get("/user", (request, response) -> userTest.toString());
		System.out.println(eventTest.toString());
		System.out.println(scoreTest.toString());
		System.out.println(userTest.toString());
	}
}