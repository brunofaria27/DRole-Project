package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Events;

public class EventsDAO extends DAO {

	public static boolean createEvent(Events event) {
		boolean status = false;

		try {
			connect();
			Statement st = connection.createStatement();
			String query = "INSERT INTO events(event_name, event_musician_id, musical_style, minimum_age, event_host_id, event_status, date_event, event_capacity, event_formality, event_target, event_hour, event_price) "
					+ "VALUES " + "('" + event.getEvent_name() + "','" + event.getEvent_musician_id() + "','"
					+ event.getMusical_style() + "','" + event.getMinimum_age() + "','" + event.getEvent_host_id()
					+ "','" + event.getEvent_status() + "','" + event.getDate_event() + "','" + event.getEvent_capacity()
					+ "','" + event.getEvent_formality() + "','" + event.getEvent_target() + "','" + event.getEvent_hour()
					+ "','" + event.getEvent_price() + "');";

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

	public static boolean deleteEvent(int id) {
		boolean status = false;

		try {
			connect();
			Statement st = connection.createStatement();
			String query = "DELETE FROM events WHERE event_id = " + id;
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

	public static boolean updateEvent(Events event) {
		boolean status = false;

		try {
			connect();
			Statement st = connection.createStatement();
			String query = "UPDATE events SET " + "event_name = '" + event.getEvent_name() + "', event_musician_id = "
					+ event.getEvent_musician_id() + ", musical_style = '" + event.getMusical_style()
					+ "', minimum_age = " + event.getMinimum_age() + ", event_host_id = " + event.getEvent_host_id()
					+ ", event_status = '" + event.getEvent_status() + "', date_event = '" + event.getDate_event()
					+ "', date_event = '" + event.getDate_event() + "', event_capacity = '" + event.getEvent_capacity()
					+ "', event_formality = '" + event.getEvent_formality() + "', event_target = '"
					+ event.getEvent_target() + "', event_hour = '" + event.getEvent_hour() + "', event_price = '"
					+ event.getEvent_price() + "' WHERE event_id = " + event.getEvent_id();

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

	public static Events getEvent(int id) {
		Events event = null;
		try {
			connect();
			Statement st = connection.createStatement();
			String query = "SELECT * FROM events WHERE event_id = " + id;
			ResultSet rs = st.executeQuery(query);

			if (rs.next()) {
				event = new Events(rs.getInt("event_id"), rs.getString("event_name"), rs.getInt("event_musician_id"),
						rs.getString("musical_style"), rs.getInt("minimum_age"), rs.getInt("event_host_id"),
						rs.getString("event_status"), rs.getString("date_event"), rs.getInt("event_capacity"),
						rs.getInt("event_formality"), rs.getInt("event_target"), rs.getInt("event_hour"),
						rs.getInt("event_price"));
			}

			st.close();

		} catch (Exception e) {
			close();
			System.out.println(e.getMessage());
		}

		close();
		return event;
	}

	public static Events[] getEvents() {
		Events[] events = null;
		try {
			connect();
			Statement st = DAO.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM events");

			if (rs.next()) {
				rs.last();
				events = new Events[rs.getRow()];
				rs.beforeFirst();

				for (int i = 0; rs.next(); i++) {
					events[i] = new Events(rs.getInt("event_id"), rs.getString("event_name"),
							rs.getInt("event_musician_id"), rs.getString("musical_style"), rs.getInt("minimum_age"),
							rs.getInt("event_host_id"), rs.getString("event_status"), rs.getString("date_event"),
							rs.getInt("event_capacity"), rs.getInt("event_formality"), rs.getInt("event_target"),
							rs.getInt("event_hour"), rs.getInt("event_price"));
				}
			}

			st.close();
		} catch (Exception e) {
			close();
			System.err.println(e.getMessage());
		}

		close();
		return events;
	}

	public static Events[] getEventsbyMusicalStyle(String style) {
		Events[] events = null;
		try {
			connect();
			Statement st = DAO.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM events WHERE musical_style = " + style);

			if (rs.next()) {
				rs.last();
				events = new Events[rs.getRow()];
				rs.beforeFirst();

				for (int i = 0; rs.next(); i++) {
					events[i] = new Events(rs.getInt("event_id"), rs.getString("event_name"),
							rs.getInt("event_musician_id"), rs.getString("musical_style"), rs.getInt("minimum_age"),
							rs.getInt("event_host_id"), rs.getString("event_status"), rs.getString("date_event"),
							rs.getInt("event_capacity"), rs.getInt("event_formality"), rs.getInt("event_target"),
							rs.getInt("event_hour"), rs.getInt("event_price"));
				}
			}

			st.close();
		} catch (Exception e) {
			close();
			System.err.println(e.getMessage());
		}

		close();
		return events;
	}
}
