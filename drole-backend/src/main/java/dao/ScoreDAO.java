package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Score;

public class ScoreDAO extends DAO {
	public static boolean createScore(Score score) {
		boolean status = false;

		try {
			connect();
			Statement st = connection.createStatement();
			String query = "INSERT INTO score(score, valued_id, valuer_id) " + "VALUES " + "('" + score.isRate() + "',"
					+ score.getValued_id() + ",'" + score.getValuer_id() + "');";

			st.executeQuery(query);
			st.close();
			status = true;
		} catch (SQLException e) {
			close();
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}

		close();

		return status;
	}

	public static boolean deleteScore(int id) {
		boolean status = false;

		try {
			connect();
			Statement st = connection.createStatement();
			String query = "DELETE FROM score WHERE score_id = " + id;
			st.executeUpdate(query);
			st.close();
			status = true;

		} catch (SQLException e) {
			close();
			throw new RuntimeException(e);
		}

		close();
		return status;
	}

	public static boolean updateScore(Score score) {
		boolean status = false;

		try {
			connect();
			Statement st = connection.createStatement();
			String query = "UPDATE score SET " + "rate = " + score.isRate() + ", valued_id = " + score.getValued_id()
					+ ", valuer_id = " + score.getValuer_id() + " WHERE score_id = " + score.getScore_id();

			st.executeUpdate(query);
			st.close();
			status = true;

		} catch (SQLException e) {
			close();
			throw new RuntimeException(e);
		}

		close();
		return status;
	}

	public static Score getScore(int id) {
		Score score = null;
		try {
			connect();
			Statement st = connection.createStatement();
			String query = "SELECT * FROM score WHERE score_id = " + id;
			ResultSet rs = st.executeQuery(query);

			if (rs.next()) {
				score = new Score(rs.getInt("score_id"), rs.getBoolean("rate"), rs.getInt("valued_id"),
						rs.getInt("valuer_id"));
			}

			st.close();

		} catch (Exception e) {
			close();
			System.out.println(e.getMessage());
		}

		close();
		return score;
	}
	
	public static boolean hasScore(int id) {
		boolean status = false;
		try {
			connect();
			Statement st = connection.createStatement();
			String query = "SELECT * FROM score WHERE valued_id = " + id;
			ResultSet rs = st.executeQuery(query);

			if (rs.next()) {
				status = true;
			}

			st.close();

		} catch (Exception e) {
			close();
			System.out.println(e.getMessage());
		}

		close();
		return status;
	}


	public static Score[] getScores() {
		Score[] score = null;
		try {
			connect();
			Statement st = DAO.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM score");

			if (rs.next()) {
				rs.last();
				score = new Score[rs.getRow()];
				rs.beforeFirst();

				for (int i = 0; rs.next(); i++) {
					score[i] = new Score(rs.getInt("score_id"), rs.getBoolean("rate"), rs.getInt("valued_id"),
							rs.getInt("valuer_id"));
				}
			}

			st.close();
		} catch (Exception e) {
			close();
			System.err.println(e.getMessage());
		}

		close();
		return score;
	}
	public static int getLikes(int id) {
		int likes = 0;
		try {
			connect();
			Statement st = DAO.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT valued_id, COUNT (rate) AS \"likes\" FROM score "
					+ "WHERE rate = true AND valued_id = " + id + " GROUP BY valued_id");

			if (rs.next()) {
				likes = rs.getInt("likes");
			}

			st.close();
		} catch (Exception e) {
			close();
			System.err.println(e.getMessage());
		}

		close();
		return likes;
	}

}
