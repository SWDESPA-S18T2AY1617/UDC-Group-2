package model.calendar;

import java.util.ArrayList;

import model.storage.AppointmentCollection;

public class Client {
	private int id;
	private Name name;
	
	private String username;	//for log-in
	private String password;
	
	private AppointmentCollection appointmentCollection;
	private ArrayList<model.calendar.Appointment> appointments; // appointments set by client
	
	
	public static final String TABLE = "client";
	public static final String COL_ID = "client_id";
	public static final String COL_FIRSTNAME = "first_name";
	public static final String COL_MIDDLENAME = "middle_name";
	public static final String COL_LASTNAME = "last_name";
	
	public Client() {
		appointments = new ArrayList<model.calendar.Appointment>();
	}
	
	public boolean login(String username, String password) {
		if(this.getUsername().equals(username) && this.getPassword().equals(password))
			return true;
		else 
			return false;
	}
	
	public boolean login (int id){
		if(this.getId() == id)
			return true;
		else
			return false;
	}
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Name getName() {
		return name;
	}
	
	public void setName(Name name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setAppCollection(AppointmentCollection appointmentCollection) {
		this.appointmentCollection = appointmentCollection; 
	}
	
	public boolean setAppointment(model.calendar.Appointment app) {
		appointments.add(app);
		return appointmentCollection.update(app);
	}
	
	public boolean cancelAppointment(model.calendar.Appointment app) {
		if(appointments.contains(app)) {
			appointments.remove(app);
			return appointmentCollection.delete(app);
		}
		
		return false;	
	}
}
