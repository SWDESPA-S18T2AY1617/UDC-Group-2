package model;

public class TaskDetails extends Details{
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
	public boolean compares (Details other) {
		boolean condition = super.compares(other);
		boolean isEqual = (((EventDetails)other) == null);
		if (other instanceof TaskDetails && !isEqual){
			isEqual = ((TaskDetails) other).getStatus().equals(status);
		}
		
		return condition && isEqual;
	}
	
	@Override 
	public String toString () {
		return super.toString() + " \nSTATUS: " + getStatus().toString();
	}
}
