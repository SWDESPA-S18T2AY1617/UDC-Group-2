package model.calendar;

public class TaskDetails extends Details {
	private Status status;
	
	public TaskDetails() {
		super();
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Override 
	public String toString () {
		return super.toString() + " \nSTATUS: " + getStatus().toString();
	}
}
