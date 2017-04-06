package model.storage;

public abstract class SecretaryObserver {
	protected SecretaryCollection collections;
	
	public void setEvents (SecretaryCollection collections) {
		
		this.collections = collections;
		if (collections !=  null) {
			collections.register(this);
		}
		
		update();
	}
	
	public abstract void update();
}
