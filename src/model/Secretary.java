import java.util.*; 
import java.sql.*;

public class Secretary {
	private int sec_id;
	private String password;
	private String first_name;
	private String middle_name;
	private String last_name;
	
	private ArrayList<Doctor> doctors; 
	
	public Secretary() {
		doctors = new ArrayList<Doctor>();
	}
	
	public setID(int sec_id) {
		this.sec_id = sec_id;
	}
	
	public setPassword(String password) {
		this.password = password;
	}
	
	public setFName(String first_name) {
		this.first_name = first_name;
	}
	
	public setMName(String middle_name) {
		this.middle_name = middle_name;
	}
	
	public setLName(String last_name) {
		this.last_name = last_name;
	}
	
	public int getID() {
		return sec_id;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getFName() {
		return first_name;
	}
	
	public String getMName() {
		return middle_name;
	}
	
	public String getLName() {
		return last_name;
	}
	
	public void setDoctors(ArrayList<Doctor> doc) {
		Iterator<Doctor> d = doctors.iterator();
		
		while(d.hasNext()) {
			Doctor doc = d.next();
			doctors.add(doc);
		}
	} 
	
	public void addDoctor(Doctor d) {
		if(d != null)
			doctos.add(d);
	}
	
	public boolean setsAppointment(Client client, Doctor doctor, Date timeSlot) {
		Details details = new Details(timeSlot.getMonth(), timeSlot.getDay(), timeSlot.getMonth()); // to be edited
		Appointment appointment = new Appointment(doctor, client, details, client.getLName() + " " + client.getFName()); // title? 
		
		for(int i = 0; i < doctors.size(); i++) { 
			if(doctors.get(i).getID() == d.getID()) { // d.equals(doctors.get(i)) 
				doctors.get(i).updateAppointment(appointment);
				return true;
			}
		}
		
		return false; 
	}
	
	// can delete appointmet set by secretary only 
	public void deleteAppointment() { // 
		// db variable ---> setter (c or s)
	}
	
	public Secretary toSecretary(ResultSet rs) throws SQLException{
		Secretary sec;

		sec = new Secretary(); 
		sec.setID(rs.getString("sec_id"));
		sec.setPassword(rs.getString("password"));
		sec.setFName(rs.getString("first_name"));
		sec.setMName(rs.getString("middle_name"));
		sec.setLName(rs.getString("last_name"));

		return sec;
	}
}