package control.doctor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import control.AppointmentControl;
import control.DayControl;
import control.ToolbarControl;
import control.WeekControl;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import model.Appointment;
import model.AppointmentObserver;
import model.Doctor;
import model.ModelGregorianCalendar;
import model.storage.AppointmentCollection;
import model.storage.ClinicDB;
import model.storage.DoctorCollection;
import model.storage.EventCollection;

public class DoctorMainControl extends AppointmentObserver {

	@FXML private AnchorPane agendaView;
    @FXML private AnchorPane dayView;
    @FXML private AnchorPane weekView;
    @FXML private AnchorPane toolbar;
    @FXML private AnchorPane loginView;
    @FXML private AnchorPane registerView;
    @FXML private AnchorPane startupView;
    @FXML private AnchorPane addView;
    
    @FXML private AppointmentControl agendaViewController;
    @FXML private DayControl dayViewController;
    @FXML private WeekControl weekViewController;
    @FXML private DoctorLoginControl loginViewController;
    @FXML private DoctorRegisterControl registerViewController;
    @FXML private DoctorStartupControl startupViewController;
    @FXML private ToolbarControl toolbarController;
    @FXML private DoctorAddControl addViewController;
    
	private AppointmentCollection appointments;
	private EventCollection events;
	private DoctorCollection doctors;
	private Doctor mainDoctor;
	
	private ModelGregorianCalendar mgc;
	
	@FXML 
	public void initialize () {
		setToolbarPane();
		setStartupVisible();
		setAppointmentsPane();
	}
	
	public void setAppointments (AppointmentCollection appointments) {
		this.appointments = appointments;
	}
	
	private void setAppointmentsPane() {
		agendaViewController.setPlaceholder("No appointments!");
		agendaViewController.cancelButtonVisibility(false);
		agendaViewController.removeButtonVisibility(false);
	}

	public void setEvents (EventCollection events) {
		this.events = events;
	}
	
	public void setToolbarPane () {
		toolbarController.setDoctorsView();
		toolbarController.setParent(this);
		toolbarController.setLogoutAction(logout -> {
			Alert alert = new Alert (AlertType.CONFIRMATION);
			alert.setTitle("Logout Confirmation!");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to logout?");
    		alert.showAndWait();
    		
    		if(alert.getResult() == ButtonType.OK) {
    			setStartupVisible();
    			mainDoctor = null;
    			toolbar.setDisable(true);
    		} 
		});
		
		toolbarController.setWeekOrDay(dayView, weekView, agendaView);
		toolbarController.setAgendaOrCalendar(dayView, weekView, agendaView);
		toolbarController.setDoctors(null);
		toolbar.setDisable(true);
		
		toolbarController.setAddSlots(event -> {
			setAddSlotsVisible();
		});
	}
	
	public void setCalendar(ModelGregorianCalendar modelGregorianCalendar) {
		toolbarController.setCalendar(modelGregorianCalendar);
		this.mgc = modelGregorianCalendar;
	}
	
    public void setStartupVisible () {
    	startupView.setVisible(true);
    	agendaView.setVisible(false);
    	weekView.setVisible(false);
    	dayView.setVisible(false);
    	loginView.setVisible(false);
    	registerView.setVisible(false);
    	addView.setVisible(false);
    	
    	update();
    }
    
    public void setLoginVisible () {
    	startupView.setVisible(false);
    	agendaView.setVisible(false);
    	weekView.setVisible(false);
    	dayView.setVisible(false);
    	loginView.setVisible(true);
    	registerView.setVisible(false);
    	addView.setVisible(false);
    	
    	update();
    }
    
    public void setRegisterVisible () {
    	startupView.setVisible(false);
    	agendaView.setVisible(false);
    	weekView.setVisible(false);
    	dayView.setVisible(false);
    	loginView.setVisible(false);
    	registerView.setVisible(true);
    	addView.setVisible(false);
    	
    	update();
    }
    
    public void setAgendaVisible () {
    	startupView.setVisible(false);
    	agendaView.setVisible(true);
    	weekView.setVisible(false);
    	dayView.setVisible(false);
    	loginView.setVisible(false);
    	registerView.setVisible(false);
    	addView.setVisible(false);
    	
    	update();
    }
    
    public void setDayVisible () {
    	startupView.setVisible(false);
    	agendaView.setVisible(false);
    	weekView.setVisible(false);
    	dayView.setVisible(true);
    	loginView.setVisible(false);
    	registerView.setVisible(false);
    	addView.setVisible(false);
    }
    
    public void setWeekVisible () {
    	startupView.setVisible(false);
    	agendaView.setVisible(false);
    	weekView.setVisible(true);
    	dayView.setVisible(false);
    	loginView.setVisible(false);
    	registerView.setVisible(false);
    	addView.setVisible(false);
    	
    	update();
    }
    
    public void setAddSlotsVisible() {
    	startupView.setVisible(false);
    	agendaView.setVisible(false);
    	weekView.setVisible(false);
    	dayView.setVisible(false);
    	loginView.setVisible(false);
    	registerView.setVisible(false);
    	addView.setVisible(true);
    	
    	update();
	}

	public void setDoctors(DoctorCollection doctors) {
		this.doctors = doctors;
		
		startupViewController.initializeButtons(this);
		loginViewController.initializeButtons(this, doctors);
		registerViewController.initializeButtons(this, doctors);
	}
	
	public void setMainDoctor(Doctor d) {
		mainDoctor = d;
		toolbar.setDisable(false);
		
		if(d == null) {
			toolbar.setDisable(true);
			setStartupVisible();
		}
	}
	
	@Override
	public void update() {
		if(mainDoctor != null) {
			ClinicDB.openConnection();
			List <Appointment> dayEvents = new ArrayList <Appointment> ();
			List <Appointment> weekEvents = new ArrayList <Appointment> ();
			
			if(toolbarController.isFree()) {
				Iterator <Appointment> events = appointments.getFreeAppointmentsOfDoctorThisDay(mainDoctor, mgc.selectedDate());
				
				
				while (events.hasNext()) {
					dayEvents.add(events.next());
				}
				
				for (int j = 0; j < mgc.getWeek().size(); j++) {
					Iterator <Appointment> tempEvents = appointments.getFreeAppointmentsOfDoctorThisDay(mainDoctor, mgc.getWeek().get(j));
					while(tempEvents.hasNext()) {
						weekEvents.add(tempEvents.next());
					}
				}
			}
			
			if(toolbarController.isOccupied()) {
				Iterator <Appointment> events = appointments.getOccupiedAppointmentsOfDoctorThisDay(mainDoctor, mgc.selectedDate());
				
				while (events.hasNext()) {
					dayEvents.add(events.next());
				}
				
				for (int j = 0; j < mgc.getWeek().size(); j++) {
					Iterator <Appointment> tempEvents = appointments.getOccupiedAppointmentsOfDoctorThisDay(mainDoctor, mgc.getWeek().get(j));
					while(tempEvents.hasNext()) {
						weekEvents.add(tempEvents.next());
					}
				}
			}		
			
			if(toolbarController.isDay())
				agendaViewController.setEvents(dayEvents.iterator());
			else
				agendaViewController.setEvents(weekEvents.iterator());
			
			weekViewController.setEvents(weekEvents.iterator());
			dayViewController.setEvents(dayEvents.iterator());
			
			dayViewController.initializeButtons(appointments, events, null, null);
			weekViewController.initializeButtons(appointments, events, null, null);
			weekViewController.setDateLabel(mgc.getWeek());
			ClinicDB.closeConnection();
		}
	}
}
