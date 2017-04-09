package model.calendar;

public class Appointment extends Event {
	public static final String TABLE = "appointment";
	public static final String COL_DOCID = "doctor_id";
	public static final String COL_CLIENTID = "client_id";
	public static final String COL_ID = "event_id";
	
	private Doctor doctor;
	private Client client;

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	public boolean isFree () {
		return client == null;
	}
	
	@Override
	public String toString () {
		return super.toString() + 
				"\nDOCTOR: " + getDoctor().toString() +
				"\nPATIENT: " + getClient().toString();
	}
	
	public Event getEvent() {
		Event event = new Event();
		event.setId(getId());
		event.setDetails(getDetails());
		event.setTitle(getTitle());
		return event;
	}
}
