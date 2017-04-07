package model.calendar;

public class Secretary {
	private String username;
	private String password;
	private Name name;
	private int id;
	
	public static final String TABLE = "secretary";
	public static final String COL_ID = "sec_id";
	public static final String COL_PASSWORD = "password";
	public static final String COL_FIRSTNAME = "first_name";
	public static final String COL_MIDDLENAME = "middle_name";
	public static final String COL_LASTNAME = "last_name";
	public static final String COL_USERNAME = "username";
	
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
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
