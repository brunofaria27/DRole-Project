package model;

public class UserType {
	private int type_id;
	private String type_name;
	private boolean create_event;
	private boolean search_events;

	public UserType() {
		this.create_event = false;
		this.search_events = false;
		this.type_name = "";
		this.type_id = 0;
	}

	public UserType(int type_id, String type_name, boolean create_event, boolean search_events) {
		this.create_event = create_event;
		this.search_events = search_events;
		this.type_name = type_name;
		this.type_id = type_id;
	}

	public int getType_id() {
		return type_id;
	}

	public void setType_id(int type_id) {
		this.type_id = type_id;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public boolean isCreate_event() {
		return create_event;
	}

	public void setCreate_event(boolean create_event) {
		this.create_event = create_event;
	}

	public boolean isSearch_events() {
		return search_events;
	}

	public void setSearch_events(boolean search_events) {
		this.search_events = search_events;
	}
	
	@Override
	public String toString() {
		return "Score [type_id=" + type_id +", type_name="+type_name+ ", search_events=" + search_events + ", create_event=" + create_event+"]";
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getType_id() == ((UserType) obj).getType_id());
	}

}
