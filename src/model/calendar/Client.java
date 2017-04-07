package model.calendar;

public class Client {
	private int id;
	private Name name;
	
	public static final String TABLE = "client";
	public static final String COL_ID = "client_id";
	public static final String COL_FIRSTNAME = "first_name";
	public static final String COL_MIDDLENAME = "middle_name";
	public static final String COL_LASTNAME = "last_name";
	
	public Name getName() {
		return name;
	}
	
	public void setName(Name name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}
