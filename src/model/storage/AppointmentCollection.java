package model.storage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.scene.paint.Color;
import model.Appointment;
import model.AppointmentObserver;
import model.Client;
import model.Doctor;
import model.Event;
import model.EventDetails;
import model.Name;

public class AppointmentCollection extends AccessObject <Appointment> {
	private List <AppointmentObserver> observers;
	
	public AppointmentCollection () {
		observers = new ArrayList <AppointmentObserver> ();
	}
	public void notifyAllObservers () {
		for (AppointmentObserver observer:observers) {
			observer.update();
		}
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
			
			String query = "SELECT * " +
					" FROM " + 
					Appointment.TABLE + " a, " +
					Client.TABLE + " c," +
					Doctor.TABLE + " d, " +
					Event.TABLE + " e " +
					"WHERE " + 	
					"c." + Client.COL_ID + " = " + " a." + Appointment.COL_CLIENTID + " AND " +
					"d." + Doctor.COL_ID + " = " + " a." + Appointment.COL_DOCID + " AND " +
					"e." + Event.EVENT_ID + " = " + " a." + Appointment.COL_ID;
			
			
			PreparedStatement statement = connect.prepareStatement(query);
			
			rs = statement.executeQuery();
			
			while(rs.next()) {
				appointments.add(toAppointment(rs));
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
			return null;
		}	
	
		return appointments.iterator();
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
			
			PreparedStatement statement = connect.prepareStatement(query);
			
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
	
			PreparedStatement statement = connect.prepareStatement(query);
			
			statement.setInt(1, e.getDoctor().getId());
			statement.setInt(2, e.getClient().getId());
			statement.setInt(3, e.getId());
			
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
	public boolean delete (Appointment e) {
		if(!ClinicDB.isOpen())
			return false;
		
		try {
			Connection connect = ClinicDB.getActiveConnection();
			String query = 	"DELETE FROM " + Appointment.TABLE + " WHERE " + Appointment.COL_ID + " = ?";
	
			PreparedStatement statement = connect.prepareStatement(query);
		
			statement.setInt(1, e.getId());
			
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
	public Appointment get(int id) {
		Appointment app = null;
		if(!ClinicDB.isOpen())
			return null;
		
		try {
			ResultSet rs;
			Connection connect = ClinicDB.getActiveConnection();
			String query = 	"SELECT * " +
					" FROM " + 
					Appointment.TABLE + " a, " +
					Client.TABLE + " c," +
					Doctor.TABLE + " d, " +
					Event.TABLE + " e " +
					"WHERE " + 	
					"c." + Client.COL_ID + " = " + " a." + Appointment.COL_CLIENTID + " AND " +
					"d." + Doctor.COL_ID + " = " + " a." + Appointment.COL_DOCID + " AND " +
					"e." + Event.EVENT_ID + " = " + " a." + Appointment.COL_ID + " AND " +
					" a." + Appointment.COL_ID + " = ?";
	
			PreparedStatement statement = connect.prepareStatement(query);
		
			statement.setInt(1, id);
		
			rs = statement.executeQuery();
			
			if(rs.next()) 
				app = toAppointment(rs);
			
			notifyAllObservers();
			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException ev) {
			ev.printStackTrace();
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
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
					" FROM " + 
					Appointment.TABLE + " a, " +
					Client.TABLE + " c," +
					Doctor.TABLE + " d, " +
					Event.TABLE + " e " +
					"WHERE " + 	
					"c." + Client.COL_ID + " = " + " a." + Appointment.COL_CLIENTID + " AND " +
					"d." + Doctor.COL_ID + " = " + " a." + Appointment.COL_DOCID + " AND " +
					"e." + Event.EVENT_ID + " = " + " a." + Appointment.COL_ID + " AND " +
					" a." + Appointment.COL_DOCID + " = ? AND " +
					" a." + Appointment.COL_CLIENTID + " IS NULL";
			
			
			PreparedStatement statement = connect.prepareStatement(query);
			
			statement.setInt(1, doctor.getId());
			
			rs = statement.executeQuery();
			
			while(rs.next()) {
				appointments.add(toAppointment(rs));
			}
			
			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
			return null;
		}	
		
		return appointments.iterator();
	}
	
	public Iterator <Appointment> getFreeAppointmentsOfDoctorThisDay (Doctor doctor) {
		List <Appointment> appointments = new ArrayList <Appointment> ();
		
		if(!ClinicDB.isOpen())
			return appointments.iterator();
	
		try {
			Connection connect = ClinicDB.getActiveConnection();
			ResultSet rs;
			
			String query = 	"SELECT * " +
					" FROM " + 
					Appointment.TABLE + " a, " +
					Client.TABLE + " c," +
					Doctor.TABLE + " d, " +
					Event.TABLE + " e " +
					"WHERE " + 	
					"c." + Client.COL_ID + " = " + " a." + Appointment.COL_CLIENTID + " AND " +
					"d." + Doctor.COL_ID + " = " + " a." + Appointment.COL_DOCID + " AND " +
					"e." + Event.EVENT_ID + " = " + " a." + Appointment.COL_ID + " AND " +
					" a." + Appointment.COL_DOCID + " = ? AND " +
					" a." + Appointment.COL_CLIENTID + " IS NULL";
			
			
			PreparedStatement statement = connect.prepareStatement(query);
			
			statement.setInt(1, doctor.getId());
			
			rs = statement.executeQuery();
			
			while(rs.next()) {
				appointments.add(toAppointment(rs));
			}
			
			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			e.printStackTrace();
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
							" FROM " + 
							Appointment.TABLE + " a, " +
							Client.TABLE + " c," +
							Doctor.TABLE + " d, " +
							Event.TABLE + " e " +
							"WHERE " + 	
							"c." + Client.COL_ID + " = " + " a." + Appointment.COL_CLIENTID + " AND " +
							"d." + Doctor.COL_ID + " = " + " a." + Appointment.COL_DOCID + " AND " +
							"e." + Event.EVENT_ID + " = " + " a." + Appointment.COL_ID + " AND " +
							" a." +Appointment.COL_CLIENTID + " = ?";
			
			PreparedStatement statement = connect.prepareStatement(query);
			statement.setInt(1, client.getId());
			
			rs = statement.executeQuery();
			
			while(rs.next()) {
				appointments.add(toAppointment(rs));
			}
			
			
			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
			return null;
		}	
		
		return appointments.iterator();
	}
	
	public Iterator <Appointment> getAppointmentsOfClientThisDay (Client client, LocalDate date) {
		List <Appointment> appointments = new ArrayList <Appointment> ();
		
		if(!ClinicDB.isOpen())
			return appointments.iterator();
	
		try {
			Connection connect = ClinicDB.getActiveConnection();
			ResultSet rs;
			
			String query = 	"SELECT * " +
							" FROM " + 
							Appointment.TABLE + " a, " +
							Client.TABLE + " c," +
							Doctor.TABLE + " d, " +
							Event.TABLE + " e " +
							"WHERE " + 	
							"c." + Client.COL_ID + " = " + " a." + Appointment.COL_CLIENTID + " AND " +
							"d." + Doctor.COL_ID + " = " + " a." + Appointment.COL_DOCID + " AND " +
							"e." + Event.EVENT_ID + " = " + " a." + Appointment.COL_ID + " AND " +
							" a." +Appointment.COL_CLIENTID + " = ? AND " + 
							" e." +Event.EVENT_DATE + " = ?";
			
			PreparedStatement statement = connect.prepareStatement(query);
			statement.setInt(1, client.getId());
			statement.setDate(2, Date.valueOf(date));
			rs = statement.executeQuery();
			
			while(rs.next()) {
				appointments.add(toAppointment(rs));
			}
			
			
			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
			return null;
		}	
		
		return appointments.iterator();
	}
	
	public Iterator <Appointment> getOccupiedAppointmentsOfDoctor (Doctor doctor) {
		List <Appointment> appointments = new ArrayList <Appointment> ();
		
		if(!ClinicDB.isOpen())
			return appointments.iterator();
	
		try {
			Connection connect = ClinicDB.getActiveConnection();
			ResultSet rs;
			
			String query = 	"SELECT * " +
					" FROM " + 
					Appointment.TABLE + " a, " +
					Client.TABLE + " c," +
					Doctor.TABLE + " d, " +
					Event.TABLE + " e " +
					"WHERE " + 	
					"c." + Client.COL_ID + " = " + " a." + Appointment.COL_CLIENTID + " AND " +
					"d." + Doctor.COL_ID + " = " + " a." + Appointment.COL_DOCID + " AND " +
					"e." + Event.EVENT_ID + " = " + " a." + Appointment.COL_ID + " AND " +
							" a." + Appointment.COL_DOCID + " = ? AND " +
							" a." + Appointment.COL_CLIENTID + " IS NOT NULL";
			
			
			PreparedStatement statement = connect.prepareStatement(query);
			
			statement.setInt(1, doctor.getId());
			
			rs = statement.executeQuery();
			
			while(rs.next()) {
				appointments.add(toAppointment(rs));
			}
			
			
			
			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
			e.printStackTrace();
			return null;
		}	
		
		return appointments.iterator();
	}
	
	public Iterator <Appointment> getOccupiedAppointmentsOfDoctorThisDay (Doctor doctor, LocalDate date) {
		List <Appointment> appointments = new ArrayList <Appointment> ();
		
		if(!ClinicDB.isOpen())
			return appointments.iterator();
	
		try {
			Connection connect = ClinicDB.getActiveConnection();
			ResultSet rs;
			
			String query = 	"SELECT * " +
					" FROM " + 
					Appointment.TABLE + " a, " +
					Client.TABLE + " c," +
					Doctor.TABLE + " d, " +
					Event.TABLE + " e " +
					"WHERE " + 	
					"c." + Client.COL_ID + " = " + " a." + Appointment.COL_CLIENTID + " AND " +
					"d." + Doctor.COL_ID + " = " + " a." + Appointment.COL_DOCID + " AND " +
					"e." + Event.EVENT_ID + " = " + " a." + Appointment.COL_ID + " AND " +
							" a." + Appointment.COL_DOCID + " = ? AND " +
							" a." + Appointment.COL_CLIENTID + " IS NOT NULL AND " +
							" e." + Event.EVENT_DATE + " = ?";
			
			
			PreparedStatement statement = connect.prepareStatement(query);
			
			statement.setInt(1, doctor.getId());
			statement.setDate(2, Date.valueOf(date));
			
			rs = statement.executeQuery();
			
			while(rs.next()) {
				appointments.add(toAppointment(rs));
			}
			
			
			
			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
			e.printStackTrace();
			return null;
		}	
		
		return appointments.iterator();
	}
	
	public Iterator <Appointment> getAllOccupiedAppointments () {
		List <Appointment> appointments = new ArrayList <Appointment> ();
		
		if(!ClinicDB.isOpen())
			return appointments.iterator();
	
		try {
			Connection connect = ClinicDB.getActiveConnection();
			ResultSet rs;
			
			String query = 	"SELECT * " +
					" FROM " + 
					Appointment.TABLE + " a, " +
					Client.TABLE + " c," +
					Doctor.TABLE + " d, " +
					Event.TABLE + " e " +
					"WHERE " + 	
					"c." + Client.COL_ID + " = " + " a." + Appointment.COL_CLIENTID + " AND " +
					"d." + Doctor.COL_ID + " = " + " a." + Appointment.COL_DOCID + " AND " +
					"e." + Event.EVENT_ID + " = " + " a." + Appointment.COL_ID + " AND " +
							" a." + Appointment.COL_CLIENTID + " IS NOT NULL";
			
			PreparedStatement statement = connect.prepareStatement(query);
			
			rs = statement.executeQuery();
			
			while(rs.next()) {
				appointments.add(toAppointment(rs));
			}
			
			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			e.printStackTrace();
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
		
		String query = "SELECT * " +
				" FROM " + 
				Appointment.TABLE + " a, " +
				Client.TABLE + " c," +
				Doctor.TABLE + " d, " +
				Event.TABLE + " e " +
				"WHERE " + 	
				"c." + Client.COL_ID + " = " + " a." + Appointment.COL_CLIENTID + " AND " +
				"d." + Doctor.COL_ID + " = " + " a." + Appointment.COL_DOCID + " AND " +
				"e." + Event.EVENT_ID + " = " + " a." + Appointment.COL_ID + " AND " +
				" a." + Appointment.COL_DOCID + " = ?";
		
		PreparedStatement statement = connect.prepareStatement(query);
		statement.setInt(1, doctor.getId());
		
		rs = statement.executeQuery();
		
		while(rs.next()) {
			appointments.add(toAppointment(rs));
		}
		
		System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
	} catch (SQLException e) {
		e.printStackTrace();
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
				" FROM " + 
				Appointment.TABLE + " a, " +
				Doctor.TABLE + " d, " +
				Event.TABLE + " e " +
				"WHERE " + 	
				"d." + Doctor.COL_ID + " = " + " a." + Appointment.COL_DOCID + " AND " +
				"e." + Event.EVENT_ID + " = " + " a." + Appointment.COL_ID + " AND " +
				" a." + Appointment.COL_CLIENTID + " IS NULL";
		
		
		PreparedStatement statement = connect.prepareStatement(query);
		
		rs = statement.executeQuery();
		
		while(rs.next()) {
			appointments.add(toFreeAppointment(rs));
		}
		
		System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
	} catch (SQLException e) {
		e.printStackTrace();
		System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
		return null;
	}	
		
		return appointments.iterator();
	}
	

	private Appointment toAppointment (ResultSet rs) throws SQLException {
		Appointment appointment = new Appointment();
		Client client = new Client();
		
		client.setId(rs.getInt(4));
		client.setName(new Name());
		client.getName().setFirst(rs.getString(5));
		client.getName().setMiddle(rs.getString(6));
		client.getName().setLast(rs.getString(7));
		client.setUsername(rs.getString(8));
		client.setPassword(rs.getString(9));
		
		Doctor doctor = new Doctor();
		doctor.setId(rs.getInt(10));
		doctor.setName(new Name());
		doctor.setPassword(rs.getString(11));
		doctor.getName().setFirst(rs.getString(12));
		doctor.getName().setMiddle(rs.getString(13));
		doctor.getName().setLast(rs.getString(14));
		doctor.setColor(Color.valueOf(rs.getString(15)));
		doctor.setDescription(rs.getString(16));
		doctor.setUsername(rs.getString(17));
		
		appointment.setId(rs.getInt(18));
		appointment.setTitle(rs.getString(19));
		
		EventDetails details = new EventDetails ();
		details.setColor(Color.valueOf(rs.getString(20)));
		LocalDate date = rs.getDate(21).toLocalDate();
		details.setDayOfMonth(date.getDayOfMonth());
		details.setYear(Year.of(date.getYear()));
		details.setMonth(date.getMonth());
		details.setTimeStart(rs.getTime(22).toLocalTime());
		details.setTimeEnd(rs.getTime(23).toLocalTime());
		
		appointment.setDetails(details);
		appointment.setClient(client);
		appointment.setDoctor(doctor);
		
		return appointment;
	}
	
	public Appointment toFreeAppointment (ResultSet rs) throws SQLException {
		Appointment appointment = new Appointment();
		Client client = null;
		
		Doctor doctor = new Doctor();
		doctor.setId(rs.getInt(4));
		doctor.setName(new Name());
		doctor.setPassword(rs.getString(5));
		doctor.getName().setFirst(rs.getString(6));
		doctor.getName().setMiddle(rs.getString(7));
		doctor.getName().setLast(rs.getString(8));
		doctor.setColor(Color.valueOf(rs.getString(9)));
		doctor.setDescription(rs.getString(10));
		doctor.setUsername(rs.getString(11));
		
		appointment.setId(rs.getInt(12));
		appointment.setTitle(rs.getString(13));
		
		EventDetails details = new EventDetails ();
		details.setColor(Color.valueOf(rs.getString(14)));
		LocalDate date = rs.getDate(15).toLocalDate();
		details.setDayOfMonth(date.getDayOfMonth());
		details.setYear(Year.of(date.getYear()));
		details.setMonth(date.getMonth());
		details.setTimeStart(rs.getTime(16).toLocalTime());
		details.setTimeEnd(rs.getTime(17).toLocalTime());
		
		appointment.setDetails(details);
		appointment.setClient(client);
		appointment.setDoctor(doctor);
		
		return appointment;
	}
	public Iterator<Appointment> getFreeAppointmentsThisDay(LocalDate selectedDate) {
		List <Appointment> appointments = new ArrayList <Appointment> ();
		
		if(!ClinicDB.isOpen())
			return appointments.iterator();
	
		try {
			Connection connect = ClinicDB.getActiveConnection();
			ResultSet rs;
			
			String query = 	"SELECT * " +
					" FROM " + 
					Appointment.TABLE + " a, " +
					Doctor.TABLE + " d, " +
					Event.TABLE + " e " +
					"WHERE " + 	
					"d." + Doctor.COL_ID + " = " + " a." + Appointment.COL_DOCID + " AND " +
					"e." + Event.EVENT_ID + " = " + " a." + Appointment.COL_ID + " AND " +
					" a." + Appointment.COL_CLIENTID + " IS NULL AND " +
					" e." + Event.EVENT_DATE + " = ?";
			
			PreparedStatement statement = connect.prepareStatement(query);
			statement.setDate(1, Date.valueOf(selectedDate));
			rs = statement.executeQuery();
			
			while(rs.next()) {
				appointments.add(toFreeAppointment(rs));
			}
			
			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
			return null;
		}	
		
		return appointments.iterator();
	}
}