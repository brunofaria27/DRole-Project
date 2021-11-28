package services;

import dao.ScoreDAO;
import model.Score;
import spark.*;

public class ScoreService extends ScoreDAO {

	public Object add(Request request, Response response) {

		int valued_id = Integer.parseInt(request.queryParams("profile_id"));
		int valuer_id = Integer.parseInt(request.queryParams("user_id"));

		Score score = new Score(true, valuer_id, valued_id);

		ScoreDAO.createScore(score);

		response.status(201);

		return "OK";
	}

	public Object get(Request request, Response response) {
		int valuer = Integer.parseInt(request.params(":id"));
		int valued = Integer.parseInt(request.params(":id2"));

		Score score = ScoreDAO.getScore(valuer, valued);
		if (score != null) {
			response.header("Content-Type", "application/xml");
			response.header("Content-Encoding", "UTF-8");

			return "<score>\n" + "\t<id>" + score.getScore_id() + "</id>\n" + "\t<rate>" + score.isRate() + "</rate>\n"
					+ "\t<valued_id>" + score.getValued_id() + "</valued_id>\n" + "\t<valuer_id>" + score.getValuer_id()
					+ "</valuer_id>\n" + "</score>\n";
		} else {
			response.status(404); // 404 Not found
			return "Score não encontrado.";
		}

	}

	public Object update(Request request, Response response) {
		int valuer = Integer.parseInt(request.params(":id"));
		int valued = Integer.parseInt(request.params(":id2"));

		Score score = ScoreDAO.getScore(valuer, valued);

		if (score != null) {
			score.setScore(!(score.isRate()));

			ScoreDAO.updateScore(score);
			return score.getScore_id();
		} else {
			response.status(404);
			return "Score não encontrado";
		}
	}

	public Object remove(Request request, Response response) {
		int valuer = Integer.parseInt(request.params(":id"));
		int valued = Integer.parseInt(request.params(":id2"));
		
		Score score = ScoreDAO.getScore(valuer, valued);

		if (score != null) {

			ScoreDAO.deleteScore(valuer, valued);
			response.status(200); // success
			return score.getScore_id();
		} else {
			response.status(404); // 404 Not found
			return "Score não encontrado.";
		}
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<scores type=\"array\">");

		for (Score score : ScoreDAO.getScores()) {
			returnValue.append("<score>\n" + "\t<id>" + score.getScore_id() + "</id>\n" + "\t<rate>" + score.isRate()
					+ "</rate>\n" + "\t<valued_id>" + score.getValued_id() + "</valued_id>\n" + "\t<valuer_id>"
					+ score.getValuer_id() + "</valuer_id>\n" + "</score>\n");
		}

		returnValue.append("</scores>");
		response.header("Content-Type", "application/xml");
		response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}

	

}
