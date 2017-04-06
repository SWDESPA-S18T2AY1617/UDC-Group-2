package model.calendar;

import java.time.LocalTime;

public class EventDetails extends Details {
	protected LocalTime timeEnd;
	
	public LocalTime getTimeEnd()
	{
		return timeEnd;
	}
	
	public void setTimeEnd (LocalTime end) {
		this.timeEnd = end;
	}
	
	@Override
	public String toString () {
		return super.toString() + "\nTIME END: " + getTimeEnd().toString();
	}
}
