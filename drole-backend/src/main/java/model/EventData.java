package model;

public class EventData {
	private int event_id;
	private String event_name;
	private String event_musician_name;
	private String musical_style;
	private int minimum_age;
	private String event_host_name;
	private String event_status;
	private String date_event;
	private int event_musician_id;
	private int event_host_id;
	
	public EventData(int event_id, String event_name, String event_musician_name, String musical_style, int minimum_age,
			String event_host_name, String event_status, String date_event, int event_musician_id, int event_host_id) {
		this.event_id = event_id;
		this.event_name = event_name;
		this.event_musician_name = event_musician_name;
		this.musical_style = musical_style;
		this.minimum_age = minimum_age;
		this.event_host_name = event_host_name;
		this.event_status = event_status;
		this.date_event = date_event;
		this.event_musician_id = event_musician_id;
		this.event_host_id = event_host_id;
	}
	
	public EventData(String event_name, String event_musician_name, String musical_style, int minimum_age,
			String event_host_name, String event_status, String date_event, int event_musician_id, int event_host_id) {
		this.event_name = event_name;
		this.event_musician_name = event_musician_name;
		this.musical_style = musical_style;
		this.minimum_age = minimum_age;
		this.event_host_name = event_host_name;
		this.event_status = event_status;
		this.date_event = date_event;
		this.event_musician_id = event_musician_id;
		this.event_host_id = event_host_id;
	}
	
	public EventData() {
		this("", "", "", 0, "", "", "", 0, 0);
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
	public String getEvent_musician_name() {
		return event_musician_name;
	}
	public void setEvent_musician_name(String event_musician_name) {
		this.event_musician_name = event_musician_name;
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
	public String getEvent_host_name() {
		return event_host_name;
	}
	public void setEvent_host_name(String event_host_name) {
		this.event_host_name = event_host_name;
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

	public int getEvent_musician_id() {
		return event_musician_id;
	}

	public void setEvent_musician_id(int event_musician_id) {
		this.event_musician_id = event_musician_id;
	}

	public int getEvent_host_id() {
		return event_host_id;
	}

	public void setEvent_host_id(int event_host_id) {
		this.event_host_id = event_host_id;
	}

	@Override
	public String toString() {
		return "EventData [event_id=" + event_id + ", event_name=" + event_name + ", event_musician_name="
				+ event_musician_name + ", musical_style=" + musical_style + ", minimum_age=" + minimum_age
				+ ", event_host_name=" + event_host_name + ", event_status=" + event_status + ", date_event="
				+ date_event + "]";
	}
}
