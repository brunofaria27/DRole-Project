package app;

import static spark.Spark.get;
import static spark.Spark.port;

import model.Events;

public class Application {
	private static Events eventTest = new Events(1, "Baile do teste", 3, "Pop", 18, 5, "Pendente", "2022-11-11 00:00:00");
	
	public static void main(String[] args) {
		port(4568);
		get("/eventos", (request, response) -> eventTest.toString());
		System.out.println(eventTest.toString());
		
	}
}
