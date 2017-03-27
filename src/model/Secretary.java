package model;

import java.util.ArrayList;
import java.util.Date;

public class Secretary {
	private ArrayList <Doctor> doctors;
	
	public Secretary (ArrayList <Doctor> doctors)
	{
		this.doctors = doctors;
	}
	
	public void setDoctors (ArrayList <Doctor> doctors)
	{
		this.doctors = doctors;
	}
	
	public boolean addAppointment (Client client, Doctor doctor, Date timeSlot)
	{
		return true;
	}
}
