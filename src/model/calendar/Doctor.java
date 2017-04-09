package model.calendar;

import java.util.Iterator;
import java.util.List;

import javafx.scene.paint.Color;

public class Doctor extends Person {
	private Color color;
	private String description;
	private List <Appointment> appointments;
	
	public static final String TABLE = "doctor";
	public static final String COL_ID = "doctor_id";
	public static final String COL_USERNAME = "username";
	public static final String COL_PASSWORD = "password";
	public static final String COL_FIRSTNAME = "first_name";
	public static final String COL_MIDDLENAME = "middle_name";
	public static final String COL_LASTNAME = "last_name";
	public static final String COL_COLOR = "color";
	public static final String COL_DESCRIPTION = "description";

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Iterator <Appointment> getAppointments() {
		return appointments.iterator();
	}

	public void setAppointments(List <Appointment> appointments) {
		this.appointments = appointments;
	}
	
	public String toString () {
		return "Dr. " + name.toString();
	}
	
	public boolean login (String username, String password) {
		if(this.getUsername().equals(username) && this.getPassword().equals(password))
			return true;
		else 
			return false;
	}
}
