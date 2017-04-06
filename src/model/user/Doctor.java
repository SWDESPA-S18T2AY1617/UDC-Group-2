package model.user;

import java.util.Iterator;
import java.util.List;

import javafx.scene.paint.Color;

public class Doctor {
	private int id;
	private Name name;
	private String username;
	private String password;
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
	
	public boolean login (String username, String password) {
		if(this.getUsername() == username && this.getPassword() == password)
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

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
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
	
	
}
