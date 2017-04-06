package model.storage;

public abstract class ClientObserver {
	protected ClientCollection collections;
	
	public void setEvents (ClientCollection collections) {
		
		this.collections = collections;
		if (collections !=  null) {
			collections.register(this);
		}
		
		update();
	}
	
	public abstract void update();

}
