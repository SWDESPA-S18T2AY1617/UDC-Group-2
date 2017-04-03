package model;

public class Appointment extends Event{
	private Doctor doctor;
	private Client client;
	
	public Appointment (Doctor doctor, Client client, Details details, String title)
	{
		super.setDetails(details);
		super.setTitle(title);
		this.doctor = doctor;
		this.client = client;
	}
}
