package model.user;

import model.calendar.*;
import java.awt.Color; 

public class Appointment extends Event {
	private Details details; 
	private int id; 
	private Doctor doctor;
	private Client client;
	private Color color; 
	
	public static final String TABLE = "appointment";
	public static final String COL_DOCID = "doc_id";
	public static final String COL_CLIENTID = "client_id";
	public static final String COL_ID = "event_id";
	
	public Appointment(Details details, Doctor doctor, Client client, Color color) {
		this.details = details;
		this.doctor = doctor;
		this. client = client;
		this.color = color; 
	}
	
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
	
	public int getID() {
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public void setDetails(Details details) {
		this.details = details;
	}
	
	public Details getDetails() {
		return details;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color; 
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
}
