package model;

public class Event {
	private Details details;
	private String title;
	private int id;
	
	public static final String TABLE = "events";
	public static final String EVENT_ID = "id";
	public static final String EVENT_COLOR = "color";
	public static final String EVENT_DATE = "date";
	public static final String EVENT_STATUS = "status";
	public static final String EVENT_TIME_START = "time_start";
	public static final String EVENT_TIME_END = "time_end";
	public static final String EVENT_TITLE = "title";
	
	public int getId () {
		return id;
	}
	
	public String getTitle () {
		return title;
	}
	
	public Details getDetails () {
		return details;
	}
	
	public void setId (int id) {
		this.id = id;
	}
	
	public void setTitle (String title) {
		this.title = title;
	}
	
	public void setDetails (Details details) {
		this.details = details;
	}
	
	public String toString () {
		return "Event ID # " + id + "\nTITLE: " + title + "\n" + details.toString() + "\n";
	}
	public String stringMe () { 
		if(this.getDetails() instanceof EventDetails )
			return this.details.getTimeStart() +"  -  " +((EventDetails)this.details).getTimeEnd() +"  " +title; 
	    if(this.getDetails() instanceof TaskDetails)
	    	return this.details.getTimeStart() +"	         " +title +"  [" +((TaskDetails)this.details).getStatus() +"]  ";
		return title; 	
	}
}
