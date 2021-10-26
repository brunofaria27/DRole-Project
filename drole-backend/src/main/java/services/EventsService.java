package services;

import dao.EventsDAO;
import model.Events;
import spark.*;

public class EventsService extends EventsDAO {
	
	public Object add(Request request, Response response) {
		String event_name = request.queryParams("event_name");
		int event_musician_id = Integer.parseInt(request.queryParams("event_musician_id"));
		String musical_style = request.queryParams("musical_style");
		int minimum_age = Integer.parseInt(request.queryParams("minimum_age"));
		int event_host_id = Integer.parseInt(request.queryParams("event_host_id"));
		String event_status = request.queryParams("event_status");
		String date_event = request.queryParams("date_event");


		Events event = new Events(event_name, event_musician_id, musical_style, minimum_age, event_host_id, event_status, date_event);

		EventsDAO.createEvent(event);

		response.status(201);

		return "OK";
	}

	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));

		Events event = EventsDAO.getEvent(id);

		if (event != null) {
			response.header("Content-Type", "application/xml");
			response.header("Content-Encoding", "UTF-8");

			return "<events>\n" + "\t<id>" + event.getEvent_id() + "</id>\n" + "\t<name>" + event.getEvent_name()
					+ "</name>\n" + "\t<musicianId>" + event.getEvent_musician_id() + "</musicianId>\n" + "\t<musicalStyle>"
					+ event.getMusical_style() + "</musicalStyle>\n" + "\t<minimumAge>" + event.getMinimum_age() + "</minimumAge>\n"
					+ "\t<hostId>" + event.getEvent_host_id() + "</hostId>\n" + "\t<status>"
					+ event.getEvent_status() + "</status>\n" + "\t<date>"
					+ event.getDate_event() + "</date>\n" + "</events>\n";
		} else {
			response.status(404); // 404 Not found
			return "Event " + id + " n�o encontrado.";
		}

	}

	public Object update(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));

		Events event = EventsDAO.getEvent(id);

		if (event != null) {
			event.setEvent_name(request.queryParams("event_name"));
			event.setEvent_musician_id(Integer.parseInt(request.queryParams("event_musician_id")));
			event.setMusical_style(request.queryParams("musical_style"));
			event.setMinimum_age(Integer.parseInt(request.queryParams("minimum_age")));
			event.setEvent_host_id(Integer.parseInt(request.queryParams("event_host_id")));
			event.setEvent_status(request.queryParams("event_status"));
			event.setDate_event(request.queryParams("date_event"));

			EventsDAO.updateEvent(event);
			return id;
		} else {
			response.status(404);
			return "Event n�o encontrado";
		}
	}

	public Object remove(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));

		Events event = EventsDAO.getEvent(id);

		if (event != null) {

			EventsDAO.deleteEvent(id);
			response.status(200); // success
			return id;
		} else {
			response.status(404); // 404 Not found
			return "Event n�o encontrado.";
		}
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<events type=\"array\">");

		for (Events event : EventsDAO.getEvents()) {
			returnValue.append("<event>\n" + "\t<id>" + event.getEvent_id() + "</id>\n" + "\t<name>" + event.getEvent_name()
			+ "</name>\n" + "\t<musicianId>" + event.getEvent_musician_id() + "</musicianId>\n" + "\t<musicalStyle>"
			+ event.getMusical_style() + "</musicalStyle>\n" + "\t<minimumAge>" + event.getMinimum_age() + "</minimumAge>\n"
			+ "\t<hostId>" + event.getEvent_host_id() + "</hostId>\n" + "\t<status>"
			+ event.getEvent_status() + "</status>\n" + "\t<date>"
			+ event.getDate_event() + "</date>\n" + "</event>\n");
		}

		returnValue.append("</events>");
		response.header("Content-Type", "application/xml");
		response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}