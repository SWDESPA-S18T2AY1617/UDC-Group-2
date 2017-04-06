package model.storage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.scene.paint.Color;
import model.calendar.Details;
import model.calendar.Event;
import model.calendar.EventDetails;
import model.calendar.Status;
import model.calendar.TaskDetails;

public class EventCollection extends AccessObject <Event> {
	private List <EventObserver> observers;
	private PreparedStatement statement;
	
	public EventCollection () {
		this.observers = new ArrayList <EventObserver> ();
	}
	
	public void register(EventObserver eventObserver) {
		this.observers.add(eventObserver);
	}
	
	public void unregister (EventObserver eventObserver) {
		this.observers.remove(eventObserver);
	}
	
	public boolean update (Event e) {
		if(!ClinicDB.isOpen())
			return false;

		try {
			Connection connect = ClinicDB.getActiveConnection();
			
			String query = 	"UPDATE " +  Event.TABLE +
							" SET " + Event.EVENT_TITLE + " = ?," +
							Event.EVENT_COLOR + " = ?," +
							Event.EVENT_DATE + " = ?," +
							Event.EVENT_TIME_START + " = ?," +
							Event.EVENT_TIME_END + " = ?," +
							Event.EVENT_STATUS + " = ? " +
							" WHERE " + Event.EVENT_ID + " = ?";
			
			statement = connect.prepareStatement(query);
			
			statement.setString(1, e.getTitle());
			statement.setString(2, e.getDetails().getColor().toString());
			statement.setDate(3, Date.valueOf(LocalDate.of(e.getDetails().getYear().getValue(), e.getDetails().getMonth(), e.getDetails().getDayOfMonth())));
			statement.setTime(4, Time.valueOf(e.getDetails().getTimeStart()));
			
			if (e.getDetails() instanceof EventDetails)
				statement.setTime(5, Time.valueOf(((EventDetails)e.getDetails()).getTimeEnd()));
			else 
				statement.setNull(5, Types.TIME);
			if (e.getDetails() instanceof TaskDetails)
				statement.setString(6, ((TaskDetails)e.getDetails()).getStatus().toString());
			else 
				statement.setNull(6, Types.VARCHAR);
			
			statement.setInt(7, e.getId());
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
	
	public boolean add (Event e) {
		if(!ClinicDB.isOpen())
			return false;
		
		try {
			Connection connect = ClinicDB.getActiveConnection();
			
			String query = 	"INSERT INTO " + 
							Event.TABLE +
							" VALUES (?, ?, ?, ?, ?, ?, ?)";
			
			statement = connect.prepareStatement(query);
			
			statement.setInt(1, e.getId());
			statement.setString(2, e.getTitle());
			statement.setString(3, e.getDetails().getColor().toString());
			statement.setDate(4, Date.valueOf(LocalDate.of(e.getDetails().getYear().getValue(), e.getDetails().getMonth(), e.getDetails().getDayOfMonth())));
			statement.setTime(5, Time.valueOf(e.getDetails().getTimeStart()));
			
			if (e.getDetails() instanceof EventDetails)
				statement.setTime(6, Time.valueOf(((EventDetails)e.getDetails()).getTimeEnd()));
			else 
				statement.setNull(6, Types.TIME);
			if (e.getDetails() instanceof TaskDetails)
				statement.setString(7, ((TaskDetails)e.getDetails()).getStatus().toString());
			else
				statement.setNull(7, Types.VARCHAR);
			
			statement.executeUpdate();
			notifyAllObservers();
			System.out.println("[" + getClass().getName() + "] INSERT SUCCESS!");
			return true;
		} catch (SQLException ev) {
			System.out.println("[" + getClass().getName() + "] INSERT FAILED!");
			return false;
		}	
	}
	
	public boolean delete (Event e) {
		if(!ClinicDB.isOpen())
			return false;
		
		try {
			Connection connect = ClinicDB.getActiveConnection();
			String query = 	"DELETE FROM " + 
							Event.TABLE +
							" WHERE " + Event.EVENT_ID + " = ?";
			
			statement = connect.prepareStatement(query);
			
			statement.setInt(1, e.getId());
			statement.executeUpdate();
			notifyAllObservers();
			System.out.println("[" + getClass().getName() + "] DELETE SUCCESS!");
			return true;
		} catch (SQLException ev) {
			System.out.println("[" + getClass().getName() + "] DELETE FAILED!");
			return false;
		}	
	}
	
	public Event get (int id) {
		if(!ClinicDB.isOpen())
			return null;
		
		Event event = null;
		
		try {
			Connection connect = ClinicDB.getActiveConnection();
			ResultSet rs;
			
			String query = 	"SELECT * " +
							" FROM " + Event.TABLE +
							" WHERE " + Event.EVENT_ID + " = " + id;
			
			statement = connect.prepareStatement(query);
			rs = statement.executeQuery();

			if(rs.next()) {
				event = toEvent(rs);
			}
			
			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
		}	
		
		return event;
	}
	
	public Iterator <Event> getAllByColor (Color c) {
		List <Event> events = new ArrayList <Event> ();
		
		if(!ClinicDB.isOpen())
			return events.iterator();
		
		try {
			Connection connect = ClinicDB.getActiveConnection();
			ResultSet rs;
			
			String query = 	"SELECT * " +
							" FROM " + Event.TABLE +
							" WHERE " + Event.EVENT_COLOR + " = ?";
			
			
			statement = connect.prepareStatement(query);
			statement.setString(1, c.toString());
			rs = statement.executeQuery();
			
			while(rs.next()) {
				events.add(toEvent(rs));
			}
			
			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
			return null;
		}	

		return events.iterator();
	}
	
	public Iterator <Event> getAllByDate (LocalDate date) {
		List <Event> events = new ArrayList <Event> ();
		
		if(!ClinicDB.isOpen())
			return events.iterator();
		
		try {
			Connection connect = ClinicDB.getActiveConnection();
			ResultSet rs;
			
			String query = 	"SELECT * " +
							" FROM " + Event.TABLE +
							" WHERE " + Event.EVENT_DATE + " = ?";
			
			
			statement = connect.prepareStatement(query);
			statement.setDate(1, Date.valueOf(date));
			rs = statement.executeQuery();
			
			while(rs.next()) {
				events.add(toEvent(rs));
			}
			
			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
			return null;
		}	
		
		return events.iterator();
	}
	
	public Iterator <Event> getTasksForThisDay (LocalDate date) {
		List <Event> events = new ArrayList <Event> ();
		
		if(!ClinicDB.isOpen())
			return events.iterator();
		
		try {
			Connection connect = ClinicDB.getActiveConnection();
			ResultSet rs;
			
			String query = 	"SELECT * " +
							" FROM " + Event.TABLE +
							" WHERE " + Event.EVENT_TIME_END + " IS NULL AND " +
							" " + Event.EVENT_DATE + " = ?";
			
			
			statement = connect.prepareStatement(query);
			statement.setDate(1, Date.valueOf(date));
			rs = statement.executeQuery();
			
			while(rs.next()) {
				events.add(toEvent(rs));
			}

			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
			return null;
		}	

		return events.iterator();
	}
	
	public Iterator <Event> getEventsForThisDay (LocalDate date) {	
		List <Event> events = new ArrayList <Event> ();
		
		if(!ClinicDB.isOpen())
			return events.iterator();
		
		try {
			Connection connect = ClinicDB.getActiveConnection();
			ResultSet rs;
			
			String query = 	"SELECT * " +
							" FROM " + Event.TABLE +
							" WHERE " + Event.EVENT_STATUS + " IS NULL AND " +
							" " + Event.EVENT_DATE + " = ?";
			
			
			statement = connect.prepareStatement(query);
			statement.setDate(1, Date.valueOf(date));
			rs = statement.executeQuery();
			
			while(rs.next()) {
				events.add(toEvent(rs));
			}

			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
			return null;
		}	

		return events.iterator();
	}
	
	public Iterator <Event> getEventsOnly () {
		List <Event> events = new ArrayList <Event> ();
		
		if(!ClinicDB.isOpen())
			return events.iterator();
		
		try {
			Connection connect = ClinicDB.getActiveConnection();
			ResultSet rs;
			
			String query = 	"SELECT * " +
							" FROM " + Event.TABLE +
							" WHERE " + Event.EVENT_STATUS + " IS NULL";
			
			statement = connect.prepareStatement(query);
			rs = statement.executeQuery();
			
			while(rs.next()) {
				events.add(toEvent(rs));
			}

			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
			return null;
		}	

		return events.iterator();
	}
	
	public Iterator <Event> getTasksOnly () {
		List <Event> events = new ArrayList <Event> ();
		
		if(!ClinicDB.isOpen())
			return events.iterator();
		
		try { 
			Connection connect = ClinicDB.getActiveConnection();
			ResultSet rs;
			
			String query = 	"SELECT * " +
							" FROM " + Event.TABLE +
							" WHERE " + Event.EVENT_TIME_END + " IS NULL";
			
			statement = connect.prepareStatement(query);
			rs = statement.executeQuery();
			
			while(rs.next()) {
				events.add(toEvent(rs));
			}
			
			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
			return null;
		}	

		return events.iterator();
	}
	
	public Iterator <Event> getAllByMonth (Month m) {
		List <Event> events = new ArrayList <Event> ();
		
		if(!ClinicDB.isOpen())
			return events.iterator();
		
		
		try {
			Connection connect = ClinicDB.getActiveConnection();
			ResultSet rs;
			
			String query = 	"SELECT * " +
							" FROM " + Event.TABLE +
							" WHERE MONTH(" + Event.EVENT_DATE + ") = ?";
			
			
			statement = connect.prepareStatement(query);
			statement.setInt(1, m.getValue());
			rs = statement.executeQuery();
			
			while(rs.next()) {
				events.add(toEvent(rs));
			}

			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
			return null;
		}	

		return events.iterator();
	}
	
	public Iterator <Event> getAll () {
		List <Event> events = new ArrayList <Event> ();
		
		try (Connection connect = ClinicDB.getActiveConnection()) {
			ResultSet rs;
			
			String query = 	"SELECT * " +
							" FROM " + Event.TABLE;
			
			statement = connect.prepareStatement(query);
			rs = statement.executeQuery();
			
			while(rs.next()) {
				events.add(toEvent(rs));
			}
			
			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
			e.printStackTrace();
			return null;
		}	

		return events.iterator();
	}
	
	public int lastUpdatedID() {
		if(!ClinicDB.isOpen())
			return -1;
		
		int i = 0;
		try (Connection connect = ClinicDB.getActiveConnection()) {
			ResultSet rs;
			
			String query = 	"SELECT MAX( " + Event.TABLE + "." + Event.EVENT_ID + ") FROM " + Event.TABLE;
			
			statement = connect.prepareStatement(query);
			rs = statement.executeQuery();
			
			if(rs.next())
				i = rs.getInt(1);

			System.out.println("[" + getClass().getName() + "] SELECT SUCCESS!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[" + getClass().getName() + "] SELECT FAILED!");
			return 0;
		}
		
		return i;
	}
	
	private Event toEvent (ResultSet rs) {
		Event event = null;
		Details details = null;
		try {
			int id = rs.getInt(Event.EVENT_ID);
			String title = rs.getString(Event.EVENT_TITLE);
			Color color = Color.valueOf(rs.getString(Event.EVENT_COLOR));
			LocalDate date = rs.getDate(Event.EVENT_DATE).toLocalDate();
			LocalTime timeStart = rs.getTime(Event.EVENT_TIME_START).toLocalTime();
			
			LocalTime timeEnd = null;
			if(rs.getTime(Event.EVENT_TIME_END) != null)
				timeEnd = rs.getTime(Event.EVENT_TIME_END).toLocalTime();
			Status status = Status.of(rs.getString(Event.EVENT_STATUS));
			
			if (status == null) {
				details = new EventDetails();
				((EventDetails)details).setTimeEnd(timeEnd);
			} else {
				details = new TaskDetails();
				((TaskDetails)details).setStatus(status);
			}
			
			details.setColor(color);
			details.setMonth(date.getMonth());
			details.setYear(Year.of(date.getYear()));
			details.setDayOfMonth(date.getDayOfMonth());
			details.setTimeStart(timeStart);
			
			event = new Event();
			event.setDetails(details);
			event.setId(id);
			event.setTitle(title);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return event;
	}
	
	private void notifyAllObservers () {
		for (EventObserver observer:observers) {
			observer.update();
		}
	}
}
