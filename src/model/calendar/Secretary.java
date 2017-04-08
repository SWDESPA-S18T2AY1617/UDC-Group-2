package model.calendar;

import model.storage.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Secretary {
	private String username;
	private String password;
	private Name name;
	private int id;
	
	private ArrayList<Doctor> doctors;
	private AppointmentCollection appointmentCollection;
	private ArrayList<model.calendar.Appointment> appointments; // appointments set by the secretary
	
	
	public static final String TABLE = "secretary";
	public static final String COL_ID = "sec_id";
	public static final String COL_PASSWORD = "password";
	public static final String COL_FIRSTNAME = "first_name";
	public static final String COL_MIDDLENAME = "middle_name";
	public static final String COL_LASTNAME = "last_name";
	public static final String COL_USERNAME = "username";
	
	public Secretary() {
		doctors = new ArrayList<Doctor>();
		appointments = new ArrayList<model.calendar.Appointment>();
	}
	
	public boolean login(String username, String password) {
		if(this.getUsername().equals(username) && this.getPassword().equals(password))
			return true;
		else 
			return false;
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
	
	public void setAppCollection(AppointmentCollection appointmentCollection) {
		this.appointmentCollection = appointmentCollection; 
	}
	
	public void setDoctors(ArrayList<Doctor> doc) {
		Iterator<Doctor> d = doc.iterator();
		
		while(d.hasNext()) {
			Doctor doctor = d.next();
			doctors.add(doctor);
		}
	}
	
	public void addDoctor(Doctor d) {
		if(d != null)
			doctors.add(d);
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
