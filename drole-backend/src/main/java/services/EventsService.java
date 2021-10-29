package services;


import dao.EventsDAO;
import model.EventData;
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
		int event_capacity = Integer.parseInt(request.queryParams("event_capacity"));
		int event_formality = Integer.parseInt(request.queryParams("event_formality"));
		int event_target = Integer.parseInt(request.queryParams("event_target"));
		int event_hour = Integer.parseInt(request.queryParams("event_hour"));
		int event_price = Integer.parseInt(request.queryParams("event_price"));

		Events event = new Events(event_name, event_musician_id, musical_style, minimum_age, event_host_id,
				event_status, date_event, event_capacity, event_formality, event_target, event_hour, event_price);
		
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
					+ "</name>\n" + "\t<musicianId>" + event.getEvent_musician_id() + "</musicianId>\n"
					+ "\t<musicalStyle>" + event.getMusical_style() + "</musicalStyle>\n" + "\t<minimumAge>"
					+ event.getMinimum_age() + "</minimumAge>\n" + "\t<hostId>" + event.getEvent_host_id()
					+ "</hostId>\n" + "\t<status>" + event.getEvent_status() + "</status>\n" + "\t<date>"
					+ event.getDate_event() + "</date>\n" + "\t<capacity>" + event.getEvent_capacity() + "</capacity>\n"
					+ "\t<formality>" + event.getEvent_formality() + "</formality>\n" + "\t<target>"
					+ event.getEvent_target() + "</target>\n" + "\t<hour>" + event.getEvent_hour() + "</hour>\n"
					+ "\t<price>" + event.getEvent_price() + "</price>\n" + "</events>\n";
		} else {
			response.status(404); // 404 Not found
			return "Event " + id + " não encontrado.";
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
			return "Event não encontrado";
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
			return "Event não encontrado.";
		}
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<events type=\"array\">");

		for (Events event : EventsDAO.getEvents()) {
			returnValue.append("<event>\n" + "\t<id>" + event.getEvent_id() + "</id>\n" + "\t<name>"
					+ event.getEvent_name() + "</name>\n" + "\t<musicianId>" + event.getEvent_musician_id()
					+ "</musicianId>\n" + "\t<musicalStyle>" + event.getMusical_style() + "</musicalStyle>\n"
					+ "\t<minimumAge>" + event.getMinimum_age() + "</minimumAge>\n" + "\t<hostId>"
					+ event.getEvent_host_id() + "</hostId>\n" + "\t<status>" + event.getEvent_status() + "</status>\n"
					+ "\t<date>" + event.getDate_event() + "</date>\n" + "\t<capacity>" + event.getEvent_capacity()
					+ "</capacity>\n" + "\t<formality>" + event.getEvent_formality() + "</formality>\n" + "\t<target>"
					+ event.getEvent_target() + "</target>\n" + "\t<hour>" + event.getEvent_hour() + "</hour>\n"
					+ "\t<price>" + event.getEvent_price() + "</price>\n" + "</event>\n");
		}

		returnValue.append("</events>");
		response.header("Content-Type", "application/xml");
		response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
	
	public Object getAllData(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<events type=\"array\">");

		for (EventData event : EventsDAO.getEventsData()) {
			returnValue.append("<event>\n" + "\t<id>" + event.getEvent_id() + "</id>\n" + "\t<name>"
					+ event.getEvent_name() + "</name>\n" + "\t<musicianName>" + event.getEvent_musician_name()
					+ "</musicianName>\n" + "\t<musicalStyle>" + event.getMusical_style() + "</musicalStyle>\n"
					+ "\t<minimumAge>" + event.getMinimum_age() + "</minimumAge>\n" + "\t<status>" + event.getEvent_status() + "</status>\n"
					+ "\t<date>" + event.getDate_event() + "</date>\n" + "\t<hostName>" + event.getEvent_host_name()
					+ "</hostName>\n" + "\t<musicianId>" + event.getEvent_musician_id()
					+ "</musicianId>\n" + "\t<hostId>" + event.getEvent_host_id()
					+ "</hostId>\n" + "</event>\n");
		}

		returnValue.append("</events>");
		response.header("Content-Type", "application/xml");
		response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}
