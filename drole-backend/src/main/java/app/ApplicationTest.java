package app;

import static spark.Spark.*;

public class ApplicationTest {
	public static void main(String[] args) {
		port(4568);
		get("/hello", (request, response) -> "Hello World! Teste para todos os PCs");
	}
}