package model.storage;

public abstract class EventObserver {
	protected EventCollection collections;
	
	public void setEvents (EventCollection collections) {
		
		this.collections = collections;
		if (collections !=  null) {
			collections.register(this);
		}
		
		update();
	}
	
	public abstract void update();

}
