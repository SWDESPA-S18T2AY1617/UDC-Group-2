package model.calendar;

public class Client extends Person {
	public static final String TABLE = "client";
	public static final String COL_ID = "client_id";
	public static final String COL_FIRSTNAME = "first_name";
	public static final String COL_MIDDLENAME = "middle_name";
	public static final String COL_LASTNAME = "last_name";
	public static final String COL_PASSWORD = "password";
	public static final String COL_USERNAME = "user_name";
	
	public String toString () {
		return getName().toString();
	}
}
