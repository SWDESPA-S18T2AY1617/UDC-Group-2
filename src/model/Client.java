package model;

import java.util.ArrayList;

public class Client {
	private ArrayList <Event> reservations;
	
	public Client (ArrayList <Event> reservations)
	{
		this.reservations = reservations;
	}
	
	public void setReservations (ArrayList <Event> reservations)
	{
		this.reservations = reservations;
	}
	
	public ArrayList <Event> getReservations ()
	{
		return reservations;
	}
}
