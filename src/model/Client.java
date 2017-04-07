package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import model.storage.*;

public class Client {
	private ArrayList <model.calendar.Appointment> reservations;
	private String client_id;
	private String first_name;
	private String middle_name;
	private String last_name;
	private AppointmentCollection e;
	
	public Client(){
		client_id = null;
		first_name = null;
		middle_name= null;
		last_name = null;
		e = new AppointmentCollection();
	}
	
	public void setAppCollection(AppointmentCollection c){
		e = c;
	}
	
	public Client (ArrayList <model.calendar.Appointment> reservations)
	{
		this.reservations = reservations;
	}

	public String getClientID() {
		return client_id;
	}


	public void setClientID(String client_id) {
		this.client_id = client_id;
	}

	public String getFName() {
		return first_name;
	}


	public void setFName(String first_name) {
		this.first_name = first_name;
	}


	public String getMName() {
		return middle_name;
	}


	public void setMName(String middle_name) {
		this.middle_name = middle_name;
	}


	public String getLName() {
		return last_name;
	}


	public void setLName(String last_name) {
		this.last_name = last_name;
	}

	
	public void setReservations (Iterator<model.calendar.Appointment> res)
	{
		reservations.add((model.calendar.Appointment)res);
	}
	
	public ArrayList <model.calendar.Appointment> getReservations ()
	{
		e.getAppointmentsOfClient(this);
		return reservations;
	}
	
	public void cancelAppointment(model.calendar.Appointment ev){
		if (reservations.contains(ev))
			reservations.remove(ev);
		e.delete(ev);
	}
	
	public void addAppointment( model.calendar.Appointment appointment){
		e.add(appointment);
		reservations.add(appointment);
	}
	

}
