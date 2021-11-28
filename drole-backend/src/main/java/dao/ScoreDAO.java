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
			String query = "INSERT INTO score(rate, valued_id, valuer_id) " + "VALUES " + "('" + score.isRate() + "',"
					+ score.getValued_id() + ",'" + score.getValuer_id() + "');";
			st.executeUpdate(query);
			st.close();
			
			Statement st2 = DAO.connection.createStatement();
			st2.executeUpdate(
					"UPDATE users SET user_likes = (SELECT COUNT(*) FROM score WHERE valued_id = " + score.getValued_id() + ") WHERE user_id = " + score.getValued_id() + ";");
			st2.close();

			status = true;
		} catch (SQLException e) {
			close();
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}

		close();

		return status;
	}

	public static boolean deleteScore(int current_id, int profile_id) {
		boolean status = false;

		try {
			connect();
			Statement st = connection.createStatement();
			String query = "DELETE FROM score WHERE valued_id = " + profile_id + " AND valuer_id = " + current_id + ";";
			st.executeUpdate(query);
			st.close();

			Statement st2 = DAO.connection.createStatement();
			st2.executeUpdate(
					"UPDATE users SET user_likes = (SELECT COUNT(*) FROM score WHERE valued_id = " + profile_id + ") WHERE user_id = " + profile_id + ";");
			st2.close();

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

			Statement st2 = DAO.connection.createStatement();
			st2.executeUpdate(
					"UPDATE users SET user_likes = likes FROM (SELECT likes, valued_id FROM users JOIN (SELECT valued_id, COUNT (rate) AS \"likes\" FROM score WHERE rate = true GROUP BY valued_id) as likes_table ON user_id = valued_id AND user_id = valued_id) AS B WHERE B.valued_id = user_id");
			st2.close();

			status = true;

		} catch (SQLException e) {
			close();
			throw new RuntimeException(e);
		}

		close();
		return status;
	}

	public static Score getScore(int valuer, int valued) {
		Score score = null;
		try {
			connect();
			Statement st = connection.createStatement();
			String query = "SELECT * FROM score WHERE valuer_id = " + valuer + " AND valued_id = " + valued;
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

	public static boolean hasScore(int current_id, int profile_id) {
		boolean status = false;
		try {
			connect();
			Statement st = connection.createStatement();
			String query = "SELECT rate FROM score WHERE valuer_id = " + current_id + " AND valued_id = " + profile_id;
			ResultSet rs = st.executeQuery(query);

			if (rs.next()) {
				status = rs.getBoolean("rate");
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
