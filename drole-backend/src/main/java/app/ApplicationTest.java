package app;

import static spark.Spark.*;

import dao.DAO;

public class ApplicationTest {
	public static void main(String[] args) {
		port(4568);
		get("/hello", (request, response) -> "Hello World! Teste para todos os PCs");
		
		// Para testes, tornar os métodos públicos e depois retorná-los para protected
		DAO dao = new DAO();
		dao.connect();
		dao.close();
	}
}