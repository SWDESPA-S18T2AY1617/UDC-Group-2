package model.storage;

public abstract class AppointmentObserver {
	protected AppointmentCollection collections;
	
	public void setEvents (AppointmentCollection collections) {
		
		this.collections = collections;
		if (collections !=  null) {
			collections.register(this);
		}
		
		update();
	}
	
	public abstract void update ();

}
