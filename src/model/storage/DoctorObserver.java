package model.storage;

public abstract class DoctorObserver {
	protected DoctorCollection collections;
	
	public void setEvents (DoctorCollection collections) {
		
		this.collections = collections;
		if (collections !=  null) {
			collections.register(this);
		}
		
		update();
	}
	
	public abstract void update();
}