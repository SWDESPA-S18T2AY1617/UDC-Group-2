package model;

import model.storage.AppointmentCollection;

public abstract class AppointmentObserver {
	protected AppointmentCollection appointments;
	
	public void setAppointments (AppointmentCollection collections) {
		
		this.appointments = collections;
		if (collections !=  null) {
			collections.register(this);
		}
		
		update();
	}
	
	public abstract void update ();

}
