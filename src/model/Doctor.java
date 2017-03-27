package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;

public class Doctor {
	private ArrayList <Event> appointments;
	private Color color;
	
	public Doctor (ArrayList <Event> appointments, Color color)
	{
		this.appointments = appointments;
		this.color = color;
	}
	
	public void setAppointments (ArrayList <Event> appointments)
	{
		this.appointments = appointments;
	}
	
	public void setAvailableTime (Date availTime)
	{
		//insert shit here
	}
	
	public void updateAppointment (Event app) 
	{
		//insert shit here
	}
	
	public void removeAppointment (Event app)
	{
		//insert shit here
	}
	
	public ArrayList <Event> getAllAppointments()
	{
		return appointments;
	}
}
