package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class Client {
	private ArrayList <Event> reservations;
	private String client_id;
	private String password;
	private String first_name;
	private String middle_name;
	private String last_name;
	
	public Client(){
		client_id = null;
		password = null;
		first_name = null;
		middle_name= null;
		last_name = null;
	}
	
	
	public String getClientID() {
		return client_id;
	}


	public void setClientID(String client_id) {
		this.client_id = client_id;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getFName() {
		return first_name;
	}


	public void setFName(String first_name) {
		this.first_name = first_name;
	}


	public String getMName() {
		return middle_name;
	}


	public void setMName(String middle_name) {
		this.middle_name = middle_name;
	}


	public String getLName() {
		return last_name;
	}


	public void setLName(String last_name) {
		this.last_name = last_name;
	}


	public Client (ArrayList <Event> reservations)
	{
		this.reservations = reservations;
	}
	
	public void setReservations (Iterator<Event> res)
	{
		reservations.add((Event)res);
	}
	
	public ArrayList <Event> getReservations ()
	{
		return reservations;
	}
	
	public void cancelReservations(Event ev){
		if (reservations.contains(ev))
			reservations.remove(ev);
	}
	
	public Client toClient(ResultSet rs) throws SQLException{
		Client client;

		client = new Client();
		client.setFName(rs.getString("first_name"));
		client.setMName(rs.getString("middle_name"));
		client.setLName(rs.getString("last_name"));
		client.setPassword(rs.getString("password"));
		client.setClientID(rs.getString("client_id"));
		
		return client;
	}
}
