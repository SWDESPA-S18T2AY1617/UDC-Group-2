package model;

import java.time.LocalTime;

public class EventDetails extends Details{
	private LocalTime timeEnd;
	
	public LocalTime getTimeEnd()
	{
		return timeEnd;
	}
	
	public void setTimeEnd (LocalTime end) {
		this.timeEnd = end;
	}
	
	public boolean compareEndTime(EventDetails o)
	{
		boolean equalTimeEnd;
		equalTimeEnd = (o.getTimeEnd() == null);
		if(!equalTimeEnd)
			equalTimeEnd = (this.timeEnd.equals(o.getTimeEnd()));
		
		return equalTimeEnd;
	}
	
	@Override
	public boolean compares (Details other) {
		boolean condition = super.compares(other);
		boolean equalTimeEnd = (((EventDetails)other) == null);
		
		if (other instanceof EventDetails && !equalTimeEnd){
			equalTimeEnd = ((EventDetails)other).getTimeEnd().equals(this.timeEnd);
		}
		
		return condition && equalTimeEnd;
	}
	
	@Override
	public String toString () {
		return super.toString() + "\nTIME END: " + getTimeEnd().toString();
	}
}
