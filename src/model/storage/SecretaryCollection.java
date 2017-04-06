package model.storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.user.Name;
import model.user.Secretary;

public class SecretaryCollection extends AccessObject <Secretary> {
	
	private List <SecretaryObserver> observers;
	private PreparedStatement statement;
	
	private void notifyAllObservers () {
		for (SecretaryObserver observer:observers) {
			observer.update();
		}
	}
	
	public void unregister (SecretaryObserver o) {
		this.observers.remove(o);
	}
	public void register (SecretaryObserver o) {
		this.observers.add(o);
	}
	
	@Override
	public Iterator<Secretary> getAll() {
		if(!ClinicDB.isOpen())
			return null;

		List <Secretary> secretaries = new ArrayList <Secretary>();
		
		try (Connection connect = ClinicDB.getActiveConnection()) {
			ResultSet rs;
			
			String query = 	"SELECT * " +
							" FROM " + Secretary.TABLE;
			
			statement = connect.prepareStatement(query);
			rs = statement.executeQuery();
			
			while(rs.next()) {
				secretaries.add(toSecretary(rs));
			}
			
			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
			e.printStackTrace();
			return null;
		}
		
		return secretaries.iterator();
	}
	
	public Secretary toSecretary(ResultSet rs) {
		Secretary sec = null;
		try {
			
			int id = rs.getInt(Secretary.COL_ID);
			String password = rs.getString(Secretary.COL_PASSWORD);
			String first = rs.getString(Secretary.COL_FIRSTNAME);
			String middle = rs.getString(Secretary.COL_MIDDLENAME);
			String last = rs.getString(Secretary.COL_LASTNAME);
			
			sec = new Secretary();
			sec.setId(id);
			sec.setPassword(password);
			sec.setName(new Name());
			sec.getName().setFirst(first);
			sec.getName().setMiddle(middle);
			sec.getName().setLast(last);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return sec;
	}
	
	@Override
	public boolean update(Secretary s) {
		if(!ClinicDB.isOpen())
			return false;

		try {
			Connection connect = ClinicDB.getActiveConnection();
			
			String query = 	"UPDATE " +  Secretary.TABLE +
							" SET " + Secretary.COL_PASSWORD + " = ?," +
							Secretary.COL_FIRSTNAME + " = ?," +
							Secretary.COL_MIDDLENAME + " = ?," +
							Secretary.COL_LASTNAME + " = ?," +
							" WHERE " + Secretary.COL_ID + " = ?";
			
			statement = connect.prepareStatement(query);

			statement.setInt(1, s.getId());
			statement.setString(2, s.getPassword());
			statement.setString(3, s.getName().getFirst());
			statement.setString(4, s.getName().getMiddle());
			statement.setString(5, s.getName().getLast());
			statement.setString(6, s.getUsername());
			
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
	public boolean add(Secretary s) {
		if(!ClinicDB.isOpen())
			return false;
		
		try {
			Connection connect = ClinicDB.getActiveConnection();
			
			String query = 	"INSERT INTO " + 
							Secretary.TABLE +
							" VALUES (?, ?, ?, ?, ?)";
			
			statement = connect.prepareStatement(query);
			
			statement.setInt(1, s.getId());
			statement.setString(2, s.getPassword());
			statement.setString(3, s.getName().getFirst());
			statement.setString(4, s.getName().getMiddle());
			statement.setString(5, s.getName().getLast());
			statement.setString(6, s.getUsername());
			
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
	public boolean delete(Secretary s) {
		if(!ClinicDB.isOpen())
			return false;
		
		try {
			Connection connect = ClinicDB.getActiveConnection();
			String query = 	"DELETE FROM " + 
							Secretary.TABLE +
							" WHERE " + Secretary.COL_ID + " = ?";
			
			statement = connect.prepareStatement(query);
			
			statement.setInt(1, s.getId());
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
	public Secretary get(int id) {
		if(!ClinicDB.isOpen())
			return null;

		Secretary sec = null;
		
		try (Connection connect = ClinicDB.getActiveConnection()) {
			ResultSet rs;
			
			String query = 	"SELECT * " +
							" FROM " + Secretary.TABLE + 
							" WHERE " + Secretary.COL_ID + " = ?";
			
			statement = connect.prepareStatement(query);
			
			statement.setInt(1, id);
			rs = statement.executeQuery();
			
			if(rs.next())
				sec = toSecretary(rs);
			
			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
			e.printStackTrace();
		}
		
		return sec;
	}
}