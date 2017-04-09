package control;

import model.Appointment;
import model.Client;
import model.storage.AppointmentCollection;
import model.storage.EventCollection;

public abstract class PopupControl {
	protected Appointment event;
	
	public abstract void setUserData (Appointment event);
	public abstract void initializeButtons (AppointmentCollection appointments,
											EventCollection events,
											Client client);	
}
