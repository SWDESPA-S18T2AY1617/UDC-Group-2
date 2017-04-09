package model.storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.scene.paint.Color;
import model.Doctor;
import model.Name;

public class DoctorCollection extends AccessObject <Doctor> {
	@Override
	public Iterator<Doctor> getAll() {
		List <Doctor> doctors = new ArrayList <Doctor>();
		
		if(!ClinicDB.isOpen())
			return doctors.iterator();
		
		try {
			Connection connect = ClinicDB.getActiveConnection();
			ResultSet rs;
			
			String query = 	"SELECT * " +
							" FROM " + Doctor.TABLE;
			PreparedStatement statement;
			statement = connect.prepareStatement(query);
			rs = statement.executeQuery();
			
			while(rs.next()) {
				doctors.add(toDoctor(rs));
			}
			
			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
			e.printStackTrace();
			return null;
		}
		
		return doctors.iterator();
	}
	
	public Doctor toDoctor(ResultSet rs) {
		Doctor doctor = null;
		try {
			int id = rs.getInt(Doctor.COL_ID);
			String password = rs.getString(Doctor.COL_PASSWORD);
			String first = rs.getString(Doctor.COL_FIRSTNAME);
			String middle = rs.getString(Doctor.COL_MIDDLENAME);
			String last = rs.getString(Doctor.COL_LASTNAME);
			Color color = Color.valueOf(rs.getString(Doctor.COL_COLOR));
			String username = rs.getString(Doctor.COL_USERNAME);
			
			doctor = new Doctor();
			
			doctor.setId(id);
			doctor.setPassword(password);
			doctor.setName(new Name());
			doctor.getName().setFirst(first);
			doctor.getName().setLast(last);
			doctor.getName().setMiddle(middle);
			doctor.setColor(color);
			doctor.setUsername(username);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return doctor;
	}

	@Override
	public boolean update(Doctor d) {
		if(!ClinicDB.isOpen())
			return false;

		try {
			Connection connect = ClinicDB.getActiveConnection();
			
			String query = 	"UPDATE " +  Doctor.TABLE +
							" SET " + Doctor.COL_PASSWORD + " = ?," +
							Doctor.COL_FIRSTNAME + " = ?," +
							Doctor.COL_MIDDLENAME + " = ?," +
							Doctor.COL_LASTNAME + " = ?," +
							Doctor.COL_COLOR + " = ?," +
							Doctor.COL_DESCRIPTION + " = ?," +
							Doctor.COL_USERNAME + " = ?" +
							" WHERE " + Doctor.COL_ID + " = ?";
			
			PreparedStatement statement;
			statement = connect.prepareStatement(query);
			
			statement.setInt(9, d.getId());
			statement.setString(1, d.getPassword());
			statement.setString(2, d.getName().getFirst());
			statement.setString(3, d.getName().getMiddle());
			statement.setString(4, d.getName().getLast());
			statement.setString(5, d.getColor().toString());
			statement.setString(7, d.getDescription());
			statement.setString(8, d.getUsername());
			
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
	public boolean add(Doctor d) {
		if(!ClinicDB.isOpen())
			return false;
		
		try {
			Connection connect = ClinicDB.getActiveConnection();
			
			String query = 	"INSERT INTO " + 
							Doctor.TABLE +
							" VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement statement;
			statement = connect.prepareStatement(query);
			
			statement.setInt(1, d.getId());
			statement.setString(2, d.getPassword());
			statement.setString(3, d.getName().getFirst());
			statement.setString(4, d.getName().getMiddle());
			statement.setString(5, d.getName().getLast());
			statement.setString(6, d.getColor().toString());
			statement.setString(7, d.getDescription());
			statement.setString(8,  d.getUsername());
			
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
	public boolean delete(Doctor d) {
		if(!ClinicDB.isOpen())
			return false;
		
		try {
			Connection connect = ClinicDB.getActiveConnection();
			String query = 	"DELETE FROM " + 
							Doctor.TABLE +
							" WHERE " + Doctor.COL_ID + " = ?";
			
			PreparedStatement statement;
			statement = connect.prepareStatement(query);
			
			statement.setInt(1, d.getId());
			statement.executeUpdate();
			
			System.out.println("[" + getClass().getName() + "] DELETE SUCCESS!");
			return true;
		} catch (SQLException ev) {
			System.out.println("[" + getClass().getName() + "] DELETE FAILED!");
			return false;
		}
	}

	@Override
	public Doctor get(int id) {
		if(!ClinicDB.isOpen())
			return null;
		
		ResultSet rs;
		Doctor doctor = null;
		
		try {
			Connection connect = ClinicDB.getActiveConnection();
			String query = 	"SELECT * FROM " + 
							Doctor.TABLE +
							" WHERE " + Doctor.COL_ID + " = ?";
			
			PreparedStatement statement;

			statement = connect.prepareStatement(query);
			rs = statement.executeQuery();
			
			while(rs.next()) {
				doctor = toDoctor(rs);
			}
			
			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
			return doctor;
		} catch (SQLException ev) {
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
			return null;
		}
	}
}
