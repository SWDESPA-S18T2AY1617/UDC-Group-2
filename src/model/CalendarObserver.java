package model;

public abstract class CalendarObserver {
	protected ModelGregorianCalendar mgc;
	
	public void setState (ModelGregorianCalendar mgc) {
		this.mgc = mgc;
		if(mgc != null)
			mgc.register(this);
		update();
	}
	
	public abstract void update ();
}
