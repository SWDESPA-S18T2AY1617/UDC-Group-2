package model.storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.calendar.Client;
import model.calendar.Doctor;
import model.calendar.Name;

public class ClientCollection extends AccessObject <Client> {
	private List <ClientObserver> observers;
	private PreparedStatement statement;
	
	private void notifyAllObservers () {
		for (ClientObserver observer:observers) {
			observer.update();
		}
	}
	
	public void unregister (ClientObserver o) {
		this.observers.remove(o);
	}
	public void register (ClientObserver o) {
		this.observers.add(o);
	}

	
	@Override
	public Iterator<Client> getAll() {
		List <Client> clients = new ArrayList <Client>();
		
		try (Connection connect = ClinicDB.getActiveConnection()) {
			ResultSet rs;
			
			String query = 	"SELECT * " +
							" FROM " + Client.TABLE;
			
			statement = connect.prepareStatement(query);
			rs = statement.executeQuery();
			
			while(rs.next()) {
				clients.add(toClient(rs));
			}
			
			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
			e.printStackTrace();
			return null;
		}
		
		return clients.iterator();
	}

	private Client toClient(ResultSet rs) {
		Client client = null;
		try {
			int id = rs.getInt(Client.COL_ID);
			String first = rs.getString(Client.COL_FIRSTNAME);
			String middle = rs.getString(Client.COL_MIDDLENAME);
			String last = rs.getString(Client.COL_LASTNAME);
			
			client = new Client();
			client.setId(id);
			client.setName(new Name());
			client.getName().setFirst(first);
			client.getName().setMiddle(middle);
			client.getName().setLast(last);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return client;
	}

	@Override
	public boolean update(Client c) {
		if(!ClinicDB.isOpen())
			return false;

		try {
			Connection connect = ClinicDB.getActiveConnection();
			
			String query = 	"UPDATE " +  Client.TABLE +
							" SET " + Client.COL_FIRSTNAME + " = ?," +
							Client.COL_MIDDLENAME + " = ?," +
							Client.COL_LASTNAME + " = ?," +
							" WHERE " + Doctor.COL_ID + " = ?";
			
			statement = connect.prepareStatement(query);
			
			statement.setString(1, c.getName().getFirst());
			statement.setString(2, c.getName().getMiddle());
			statement.setString(3, c.getName().getLast());
			statement.setInt(4, c.getId());
			
			statement.executeUpdate();
			notifyAllObservers();
			System.out.println("[" + getClass().getName() + "] UPDATE SUCCESS!");
			return true;
		} catch (SQLException ev) {
			ev.printStackTrace();
			System.out.println("[" + getClass().getName() + "] UPDATE FAILED!");
			return false;
		}
	}

	@Override
	public boolean add(Client c) {
		if(!ClinicDB.isOpen())
			return false;
		
		try {
			Connection connect = ClinicDB.getActiveConnection();
			
			String query = 	"INSERT INTO " + 
							Client.TABLE +
							" VALUES (?, ?, ?, ?)";
			
			statement = connect.prepareStatement(query);
			
			statement.setInt(1, c.getId());
			statement.setString(3, c.getName().getFirst());
			statement.setString(4, c.getName().getMiddle());
			statement.setString(5, c.getName().getLast());
			
			statement.executeUpdate();
			notifyAllObservers();
			System.out.println("[" + getClass().getName() + "] INSERT SUCCESS!");
			return true;
		} catch (SQLException ev) {
			ev.printStackTrace();
			System.out.println("[" + getClass().getName() + "] INSERT FAILED!");
			return false;
		}
	}

	@Override
	public boolean delete(Client c) {
		if(!ClinicDB.isOpen())
			return false;
		
		try {
			Connection connect = ClinicDB.getActiveConnection();
			String query = 	"DELETE FROM " + 
							Client.TABLE +
							" WHERE " + Client.COL_ID + " = ?";
			
			statement = connect.prepareStatement(query);
			
			statement.setInt(1, c.getId());
			statement.executeUpdate();
			notifyAllObservers();
			System.out.println("[" + getClass().getName() + "] DELETE SUCCESS!");
			return true;
		} catch (SQLException ev) {
			System.out.println("[" + getClass().getName() + "] DELETE FAILED!");
			return false;
		}
	}

	@Override
	public Client get(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}