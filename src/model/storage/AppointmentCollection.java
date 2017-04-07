package model.storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.calendar.Appointment;
import model.user.Client;
import model.user.Doctor;

public class AppointmentCollection extends AccessObject <Appointment> {
	private List <AppointmentObserver> observers;
	private PreparedStatement statement;

	private DoctorCollection doctorList;
	private ClientCollection clientList;
	private EventCollection eventList;
	
	public void notifyAllObservers () {
		for (AppointmentObserver observer:observers) {
			observer.update();
		}
	}
	
	public AppointmentCollection(){
		doctorList = null;
		clientList = null;
		eventList = null;
	}
	
	
	public AppointmentCollection (DoctorCollection doctorList, ClientCollection clientList, EventCollection eventList) {
		this.doctorList = doctorList;
		this.clientList = clientList;
		this.eventList = eventList;
	}
	
	public void unregister (AppointmentObserver o) {
		this.observers.remove(o);
	}
	public void register (AppointmentObserver o) {
		this.observers.add(o);
	}

	
	@Override
	public Iterator <Appointment> getAll() {
		List <Appointment> appointments = new ArrayList <Appointment> ();
		
		if(!ClinicDB.isOpen())
			return appointments.iterator();
		
		try {
			Connection connect = ClinicDB.getActiveConnection();
			ResultSet rs;
			
			String query = 	"SELECT * " +
							" FROM " + Appointment.TABLE;
			
			
			statement = connect.prepareStatement(query);
			
			rs = statement.executeQuery();
			
			while(rs.next()) {
				appointments.add(toAppointment(rs));
			}
			
			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
			return null;
		}	
	
		return appointments.iterator();
	}

	private Appointment toAppointment (ResultSet rs) throws SQLException {
		Appointment appointment = ((Appointment)eventList.get(rs.getInt(Appointment.COL_ID)));
		appointment.setClient(clientList.get(rs.getInt(Appointment.COL_CLIENTID)));
		appointment.setDoctor(doctorList.get(rs.getInt(Appointment.COL_DOCID)));
		
		return appointment;
	}

	@Override
	public boolean update(Appointment e) {
		if(!ClinicDB.isOpen())
			return false;

		try {
			Connection connect = ClinicDB.getActiveConnection();
			
			String query = 	"UPDATE " +  Appointment.TABLE +
							" SET " + Appointment.COL_CLIENTID + " = ?," +
							Appointment.COL_DOCID + " = ?" +
							" WHERE " + Appointment.COL_ID + " = ?";
			
			statement = connect.prepareStatement(query);
			
			statement.setInt(1, e.getClient().getId());
			statement.setInt(2, e.getDoctor().getId());
			statement.setInt(3, e.getId());
			
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
	public boolean add (Appointment e) {
		if(!ClinicDB.isOpen())
			return false;
		
		try {
			Connection connect = ClinicDB.getActiveConnection();
			String query = 	"INSERT INTO VALUES (?, ?, ?)";
	
			statement = connect.prepareStatement(query);
			
			statement.setInt(1, e.getDoctor().getId());
			statement.setInt(2, e.getClient().getId());
			statement.setInt(3, e.getId());
			
			statement.executeUpdate();
			notifyAllObservers();
			System.out.println("[" + getClass().getName() + "] INSERT SUCCESS!");
			return true;
		} catch (SQLException ev) {
			System.out.println("[" + getClass().getName() + "] INSERT FAILED!");
			return false;
		}	
	}

	@Override
	public boolean delete (Appointment e) {
		if(!ClinicDB.isOpen())
			return false;
		
		try {
			Connection connect = ClinicDB.getActiveConnection();
			String query = 	"DELETE FROM " + Appointment.TABLE + " WHERE " + Appointment.COL_ID + " = ?";
	
			statement = connect.prepareStatement(query);
		
			statement.setInt(1, e.getId());
			
			statement.executeUpdate();
			notifyAllObservers();
			System.out.println("[" + getClass().getName() + "] INSERT SUCCESS!");
			return true;
		} catch (SQLException ev) {
			System.out.println("[" + getClass().getName() + "] INSERT FAILED!");
			return false;
		}	
	}

	@Override
	public Appointment get(int id) {
		Appointment app = null;
		if(!ClinicDB.isOpen())
			return null;
		
		try {
			ResultSet rs;
			Connection connect = ClinicDB.getActiveConnection();
			String query = 	"DELETE FROM " + Appointment.TABLE + " WHERE " + Appointment.COL_ID + " = ?";
	
			statement = connect.prepareStatement(query);
		
			statement.setInt(1, id);
		
			rs = statement.executeQuery();
			
			if(rs.next()) 
				app = toAppointment(rs);
			
			notifyAllObservers();
			System.out.println("[" + getClass().getName() + "] INSERT SUCCESS!");
		} catch (SQLException ev) {
			System.out.println("[" + getClass().getName() + "] INSERT FAILED!");
		}	
		
		return app;
	}
	
	public Iterator <Appointment> getFreeAppointmentsOfDoctor (Doctor doctor) {
		List <Appointment> appointments = new ArrayList <Appointment> ();
		
		if(!ClinicDB.isOpen())
			return appointments.iterator();
	
		try {
			Connection connect = ClinicDB.getActiveConnection();
			ResultSet rs;
			
			String query = 	"SELECT * " +
							" FROM " + Appointment.TABLE + " WHERE " + 
							Appointment.COL_DOCID + " = ? AND " +
							Appointment.COL_CLIENTID + " IS NULL";
			
			
			statement = connect.prepareStatement(query);
			
			statement.setInt(1, doctor.getId());
			
			rs = statement.executeQuery();
			
			while(rs.next()) {
				appointments.add(toAppointment(rs));
			}
			
			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
			return null;
		}	
		
		return appointments.iterator();
	}
	
	public Iterator <Appointment> getAppointmentsOfClient (Client client) {
		List <Appointment> appointments = new ArrayList <Appointment> ();
		
		if(!ClinicDB.isOpen())
			return appointments.iterator();
	
		try {
			Connection connect = ClinicDB.getActiveConnection();
			ResultSet rs;
			
			String query = 	"SELECT * " +
							" FROM " + Appointment.TABLE + " WHERE " + 							
							Appointment.COL_CLIENTID + " = ?";
			
			
			statement = connect.prepareStatement(query);
			statement.setInt(1, client.getId());
			
			rs = statement.executeQuery();
			
			while(rs.next()) {
				appointments.add(toAppointment(rs));
			}
			
			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
			return null;
		}	
		
		return appointments.iterator();
	}
	
	public Iterator <Appointment> getOccupiedAppointmentsOfDoctors (Doctor doctor) {
		List <Appointment> appointments = new ArrayList <Appointment> ();
		
		if(!ClinicDB.isOpen())
			return appointments.iterator();
	
		try {
			Connection connect = ClinicDB.getActiveConnection();
			ResultSet rs;
			
			String query = 	"SELECT * " +
							" FROM " + Appointment.TABLE + " WHERE " + 
							Appointment.COL_DOCID + " = ? AND " +
							Appointment.COL_CLIENTID + " IS NOT NULL";
			
			
			statement = connect.prepareStatement(query);
			
			statement.setInt(1, doctor.getId());
			
			rs = statement.executeQuery();
			
			while(rs.next()) {
				appointments.add(toAppointment(rs));
			}
			
			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
			return null;
		}	
		
		return appointments.iterator();
	}
	
	public Iterator <Appointment> getAllOccupiedAppointments (Doctor doctor) {
		List <Appointment> appointments = new ArrayList <Appointment> ();
		
		if(!ClinicDB.isOpen())
			return appointments.iterator();
	
		try {
			Connection connect = ClinicDB.getActiveConnection();
			ResultSet rs;
			
			String query = 	"SELECT * " +
							" FROM " + Appointment.TABLE + " WHERE " +
							Appointment.COL_CLIENTID + " IS NOT NULL";
			
			
			statement = connect.prepareStatement(query);
			
			statement.setInt(1, doctor.getId());
			
			rs = statement.executeQuery();
			
			while(rs.next()) {
				appointments.add(toAppointment(rs));
			}
			
			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
			return null;
		}	
		
		return appointments.iterator();
	}
	
	public Iterator <Appointment> getAppointmentsOfDoctor (Doctor doctor) {
		List <Appointment> appointments = new ArrayList <Appointment> ();
		
		if(!ClinicDB.isOpen())
			return appointments.iterator();
	
	try {
		Connection connect = ClinicDB.getActiveConnection();
		ResultSet rs;
		
		String query = 	"SELECT * " +
						" FROM " + Appointment.TABLE + " WHERE " + Appointment.COL_DOCID + " = ?";
		
		
		statement = connect.prepareStatement(query);
		statement.setInt(1, doctor.getId());
		
		rs = statement.executeQuery();
		
		while(rs.next()) {
			appointments.add(toAppointment(rs));
		}
		
		System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
	} catch (SQLException e) {
		System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
		return null;
	}	
		
		return appointments.iterator();
	}
	
	public Iterator <Appointment> getFreeAppointments () {
		List <Appointment> appointments = new ArrayList <Appointment> ();
		
		if(!ClinicDB.isOpen())
			return appointments.iterator();
	
	try {
		Connection connect = ClinicDB.getActiveConnection();
		ResultSet rs;
		
		String query = 	"SELECT * " +
						" FROM " + Appointment.TABLE + " WHERE " + Appointment.COL_CLIENTID + " = NULL";
		
		
		statement = connect.prepareStatement(query);
		
		rs = statement.executeQuery();
		
		while(rs.next()) {
			appointments.add(toAppointment(rs));
		}
		
		System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
	} catch (SQLException e) {
		System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
		return null;
	}	
		
		return appointments.iterator();
	}
}