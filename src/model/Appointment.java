package model;

public class Appointment extends Event{
	private Doctor doctor;
	private Client client;
	
	public Appointment (Doctor doctor, Client client)
	{
		this.doctor = doctor;
		this.client = client;
	}
}
