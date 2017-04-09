package model.storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.Client;
import model.Doctor;
import model.Name;

public class ClientCollection extends AccessObject <Client> {
	@Override
	public Iterator<Client> getAll() {
		List <Client> clients = new ArrayList <Client>();
		
		try (Connection connect = ClinicDB.getActiveConnection()) {
			ResultSet rs;
			
			String query = 	"SELECT * " +
							" FROM " + Client.TABLE;
			PreparedStatement statement;
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
							Client.COL_USERNAME + " = ?," +
							Client.COL_PASSWORD + " = ?" +
							" WHERE " + Doctor.COL_ID + " = ?";
			
			PreparedStatement statement;
			statement = connect.prepareStatement(query);
			
			statement.setString(1, c.getName().getFirst());
			statement.setString(2, c.getName().getMiddle());
			statement.setString(3, c.getName().getLast());
			statement.setString(4, c.getUsername());
			statement.setString(5, c.getPassword());
			statement.setInt(6, c.getId());
			
			statement.executeUpdate();
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
							" VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement statement;
			statement = connect.prepareStatement(query);
			
			statement.setInt(1, c.getId());
			statement.setString(2, c.getName().getFirst());
			statement.setString(3, c.getName().getMiddle());
			statement.setString(4, c.getName().getLast());
			statement.setString(5, c.getUsername());
			statement.setString(6, c.getPassword());
			
			statement.executeUpdate();
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
			PreparedStatement statement;
			statement = connect.prepareStatement(query);
			
			statement.setInt(1, c.getId());
			statement.executeUpdate();
			System.out.println("[" + getClass().getName() + "] DELETE SUCCESS!");
			return true;
		} catch (SQLException ev) {
			System.out.println("[" + getClass().getName() + "] DELETE FAILED!");
			return false;
		}
	}

	@Override
	public Client get(int id) {
		Client client = null;
		
		try (Connection connect = ClinicDB.getActiveConnection()) {
			ResultSet rs;
			
			String query = 	"SELECT * " +
							" FROM " + Client.TABLE + 
							" WHERE " + Client.COL_ID + " = ?";
			PreparedStatement statement;
			statement = connect.prepareStatement(query);
			statement.setInt(1, id);
			rs = statement.executeQuery();
			
			if (rs.next()) {
				client = toClient(rs);
			}
			
			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
			e.printStackTrace();
		}
		
		return client;
	}

	public Client get(String username, String password) {
		Client client = null;
		
		try (Connection connect = ClinicDB.getActiveConnection()) {
			ResultSet rs;
			
			String query = 	"SELECT * " +
							" FROM " + Client.TABLE + 
							" WHERE " + Client.COL_PASSWORD + " = ?" + 
							" AND " + Client.COL_USERNAME + " = ?";
			
			PreparedStatement statement;
			statement = connect.prepareStatement(query);
			statement.setString(1, password);
			statement.setString(2, username);
			
			rs = statement.executeQuery();
			
			if (rs.next()) {
				client = toClient(rs);
			}
			
			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
			e.printStackTrace();
		}
		
		return client;
	}
	
	public int lastUpdatedID () {
		int i = 0;
		
		try (Connection connect = ClinicDB.getActiveConnection()) {
			ResultSet rs;
			
			String query = 	"SELECT MAX(" + Client.COL_ID + ") FROM " + Client.TABLE;
			PreparedStatement statement;
			statement = connect.prepareStatement(query);
			rs = statement.executeQuery();
			
			if (rs.next()) {
				i = rs.getInt(1);
			}
			
			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
			e.printStackTrace();
		}
		
		return i;
	}
}