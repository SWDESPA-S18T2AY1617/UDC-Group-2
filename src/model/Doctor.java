package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Doctor {
	private ArrayList <Appointment> appointments;
	private ArrayList <Date> timeSlots = new ArrayList <Date>();
	private Color color;
	
	public Doctor (ArrayList <Appointment> appointments, Color color)
	{
		this.appointments = appointments;
		this.color = color;
	}
	
	public void setAppointments (ArrayList <Appointment> appointments)
	{
		this.appointments = appointments;
	}
	
	public void setAvailableTime (Date availTime)
	{
		timeSlots.add(availTime);
	}
	
	public void updateAppointment (Appointment app) 
	{
		for(int i = 0; i < appointments.size(); i++)
		{
			if(app.equals(appointments.get(i)))
				appointments.set(i, app);
		}
	}
	
	public void removeAppointment (Appointment app)
	{
		for(int i = 0; i < appointments.size(); i++)
		{
			if(app.equals(appointments.get(i)))
			{
				appointments.remove(i);
				System.out.println("Deleted appointment.");
			}
		}
	}
	
	public ArrayList <Appointment> getAllAppointments()
	{
		return appointments;
	}
}
