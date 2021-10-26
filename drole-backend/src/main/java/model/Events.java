package model;


public class Events {
	private int event_id;
	private String event_name;
	private int event_musician_id;
	private String musical_style;
	private int minimum_age;
	private int event_host_id;
	private String event_status;
	private String date_event;
	
	public Events(int event_id, String event_name, int event_musician_id, String musical_style, int minimum_age,
			int event_host_id, String event_status, String date_event) {
		this.event_id = event_id;
		this.event_name = event_name;
		this.event_musician_id = event_musician_id;
		this.musical_style = musical_style;
		this.minimum_age = minimum_age;
		this.event_host_id = event_host_id;
		this.event_status = event_status;
		this.date_event = date_event;
	}
	
	public Events(String event_name, int event_musician_id, String musical_style, int minimum_age,
			int event_host_id, String event_status, String date_event) {
		this.event_name = event_name;
		this.event_musician_id = event_musician_id;
		this.musical_style = musical_style;
		this.minimum_age = minimum_age;
		this.event_host_id = event_host_id;
		this.event_status = event_status;
		this.date_event = date_event;
	}

	public Events() {
		this.event_id = 0;
		this.event_name = "";
		this.event_musician_id = 0;
		this.musical_style = "";
		this.minimum_age = 0;
		this.event_host_id = 0;
		this.event_status = "";
		this.date_event = "";
	}

	public int getEvent_id() {
		return event_id;
	}

	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}

	public String getEvent_name() {
		return event_name;
	}

	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}

	public int getEvent_musician_id() {
		return event_musician_id;
	}

	public void setEvent_musician_id(int event_musician_id) {
		this.event_musician_id = event_musician_id;
	}

	public String getMusical_style() {
		return musical_style;
	}

	public void setMusical_style(String musical_style) {
		this.musical_style = musical_style;
	}

	public int getMinimum_age() {
		return minimum_age;
	}

	public void setMinimum_age(int minimum_age) {
		this.minimum_age = minimum_age;
	}

	public int getEvent_host_id() {
		return event_host_id;
	}

	public void setEvent_host_id(int event_host_id) {
		this.event_host_id = event_host_id;
	}

	public String getEvent_status() {
		return event_status;
	}

	public void setEvent_status(String event_status) {
		this.event_status = event_status;
	}

	public String getDate_event() {
		return date_event;
	}

	public void setDate_event(String date_event) {
		this.date_event = date_event;
	}
	
	@Override
	public String toString() {
		return "Evento {\n" + "- Event ID: " + event_id + "\n- Event name: " + event_name + "\n- Event Musician ID: " + event_musician_id + "\n- Musical Style: " + musical_style +
				"\n- Minimum age: " + minimum_age + "\n- Event Host ID: " + event_host_id + "\n- Event Status: " + event_status + "- Date Event: " + date_event + "\n}";
	}
	
}