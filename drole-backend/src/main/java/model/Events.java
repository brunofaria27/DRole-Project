package model;

import java.util.Objects;

public class Events {
	private int event_id;
	private String event_name;
	private int event_musician_id;
	private String musical_style;
	private int minimum_age;
	private int event_host_id;
	private String event_status;
	private String date_event;
	private int event_capacity;
	private int event_formality;
	private int event_target;
	private int event_hour;
	private int event_price;
	
	public Events(int event_id, String event_name, int event_musician_id, String musical_style, int minimum_age,
			int event_host_id, String event_status, String date_event, int event_capacity, int event_formality,
			int event_target, int event_hour, int event_price) {
		this.event_id = event_id;
		this.event_name = event_name;
		this.event_musician_id = event_musician_id;
		this.musical_style = musical_style;
		this.minimum_age = minimum_age;
		this.event_host_id = event_host_id;
		this.event_status = event_status;
		this.date_event = date_event;
		this.event_capacity = event_capacity;
		this.event_formality = event_formality;
		this.event_target = event_target;
		this.event_hour = event_hour;
		this.event_price = event_price;
	}
	
	public Events(String event_name, int event_musician_id, String musical_style, int minimum_age,
			int event_host_id, String event_status, String date_event, int event_capacity, int event_formality,
			int event_target, int event_hour, int event_price) {
		this.event_name = event_name;
		this.event_musician_id = event_musician_id;
		this.musical_style = musical_style;
		this.minimum_age = minimum_age;
		this.event_host_id = event_host_id;
		this.event_status = event_status;
		this.date_event = date_event;
		this.event_capacity = event_capacity;
		this.event_formality = event_formality;
		this.event_target = event_target;
		this.event_hour = event_hour;
		this.event_price = event_price;
	}
	
	public Events() {
		this("", 0, "", 0, 0, "", "", 0, 0, 0, 0, 0);
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

	public int getEvent_capacity() {
		return event_capacity;
	}

	public void setEvent_capacity(int event_capacity) {
		this.event_capacity = event_capacity;
	}

	public int getEvent_formality() {
		return event_formality;
	}

	public void setEvent_formality(int event_formality) {
		this.event_formality = event_formality;
	}

	public int getEvent_target() {
		return event_target;
	}

	public void setEvent_target(int event_target) {
		this.event_target = event_target;
	}

	public int getEvent_hour() {
		return event_hour;
	}

	public void setEvent_hour(int event_hour) {
		this.event_hour = event_hour;
	}

	public int getEvent_price() {
		return event_price;
	}

	public void setEvent_price(int event_price) {
		this.event_price = event_price;
	}

	@Override
	public String toString() {
		return "Events [event_id=" + event_id + ", event_name=" + event_name + ", event_musician_id="
				+ event_musician_id + ", musical_style=" + musical_style + ", minimum_age=" + minimum_age
				+ ", event_host_id=" + event_host_id + ", event_status=" + event_status + ", date_event=" + date_event
				+ ", event_capacity=" + event_capacity + ", event_formality=" + event_formality + ", event_target="
				+ event_target + ", event_hour=" + event_hour + ", event_price=" + event_price + "]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(date_event, event_capacity, event_formality, event_host_id, event_hour, event_id,
				event_musician_id, event_name, event_price, event_status, event_target, minimum_age, musical_style);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Events other = (Events) obj;
		return Objects.equals(date_event, other.date_event) && event_capacity == other.event_capacity
				&& event_formality == other.event_formality && event_host_id == other.event_host_id
				&& event_hour == other.event_hour && event_id == other.event_id
				&& event_musician_id == other.event_musician_id && Objects.equals(event_name, other.event_name)
				&& event_price == other.event_price && Objects.equals(event_status, other.event_status)
				&& event_target == other.event_target && minimum_age == other.minimum_age
				&& Objects.equals(musical_style, other.musical_style);
	}
}