package control.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import control.AppointmentControl;
import control.DayControl;
import control.ToolbarControl;
import control.WeekControl;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import model.Appointment;
import model.AppointmentObserver;
import model.Client;
import model.Doctor;
import model.Event;
import model.ModelGregorianCalendar;
import model.storage.ClientCollection;
import model.storage.ClinicDB;
import model.storage.DoctorCollection;
import model.storage.EventCollection;

public class ClientMainControl extends AppointmentObserver {

	@FXML private AnchorPane login;
	@FXML private AnchorPane register;
	@FXML private AnchorPane start;
    @FXML private AnchorPane toolbar;
    @FXML private AnchorPane appointment;
    @FXML private AnchorPane week;
    @FXML private AnchorPane day;
    
	@FXML private ClientLoginControl loginController;
	@FXML private ClientRegisterControl registerController;
	@FXML private ClientStartupControl startController;
	@FXML private AppointmentControl appointmentController;
	@FXML private WeekControl weekController;
	@FXML private DayControl dayController;
	@FXML private ToolbarControl toolbarController;

	private EventCollection events;
	private DoctorCollection doctors;
	private Client mainClient;
	
	private ModelGregorianCalendar mgc;
	
	public void setClients(ClientCollection clients) {
		loginController.setEvents(this, clients);
		registerController.setEvents(this, clients);
	}
	
	public void setDoctors(DoctorCollection doctors) {
		this.doctors = doctors;
		ClinicDB.openConnection();
		toolbarController.setDoctors(this.doctors.getAll());
		ClinicDB.closeConnection();
	}

	@FXML
	public void initialize () {
		setAppointmentsPane();
		setToolbarPane();
		setStart();
		
		setStartVisible();
	}

	public void setMainClient (Client client) {
		this.mainClient = client;
		toolbar.setDisable(false);
		
		if (client == null) {
			toolbar.setDisable(true);
			setStartVisible();
		}
	}
	
	public void setStart () {
		startController.setEvents(this);
	}
	
	public void setToolbarPane () {
		toolbarController.setParent(this);
		toolbarController.setLogoutAction(logout -> {
			Alert alert = new Alert (AlertType.CONFIRMATION);
			alert.setTitle("Logout Confirmation!");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to logout?");
    		alert.showAndWait();
    		
    		if(alert.getResult() == ButtonType.OK) {
    			setStartVisible();
    			mainClient = null;
    			toolbar.setDisable(true);
    		} 
		});
		
		toolbarController.setWeekOrDay(day, week, appointment);
		
		toolbarController.setAgendaOrCalendar(day, week, appointment);
		
		toolbarController.setReservationAction(reserve -> {
			update();
		});
		
		setAppointmentsVisible();
		
		toolbarController.setDoctors(null);
		toolbar.setDisable(true);
	}
	
	public void setAppointmentsPane () {
		appointmentController.setPlaceholder("No appointments!");
		appointmentController.cancelButtonVisibility(true);
		
		appointmentController.setCancelButton(event -> {
			Appointment appointment = appointmentController.getListInput();
			
			if (appointment != null) {
				ClinicDB.openConnection();
				Alert alert = new Alert (AlertType.CONFIRMATION);
				alert.setTitle("Delete Confirmation");
				alert.setHeaderText(null);
				alert.setContentText("Are you sure you want to cancel this appointment?");
	    		
	    		Optional <ButtonType> result = alert.showAndWait();
	    	
	    		appointment.setClient(null);
	    		
	    		if (result.get() == ButtonType.OK){
	    			ClinicDB.openConnection();
	    			boolean removed = appointments.update(appointment);
	    			ClinicDB.closeConnection();
	    			Event ev = appointment.getEvent();
	        		ev.setTitle("FREE");
	        		
	        		ClinicDB.openConnection();
	    			boolean another = events.update(ev);
	    			ClinicDB.closeConnection();
					
	    			if(removed && another) {
	    				alert = new Alert (AlertType.INFORMATION);
	    				alert.setTitle("Delete Successful");
	    				alert.setHeaderText(null);
	    				alert.setContentText("Successfully freed: " + appointment.getTitle());
	    				alert.showAndWait();
	    			} else {
	    				alert = new Alert (AlertType.ERROR);
	    				alert.setTitle("Database Error");
	    				alert.setHeaderText(null);
	    				alert.setContentText("There was an error in the database connection!");
	    				alert.showAndWait();
	    			}
	    		} 

				ClinicDB.closeConnection();
			} 
		});
		
		appointmentController.setReserveButton(event -> {
			Appointment appointment = appointmentController.getListInput();
			appointment.setClient(mainClient);
			
			TextInputDialog dialog = new TextInputDialog("");
			dialog.setTitle("Reservation Confirmation");
			dialog.setHeaderText(null);
			dialog.setContentText("Reservation for what?");

			Optional<String> result = dialog.showAndWait();
			
			result.ifPresent(title -> {
				appointment.setTitle(title);
				ClinicDB.openConnection();
				boolean updated = appointments.update(appointment);
				ClinicDB.closeConnection();
				ClinicDB.openConnection();
				boolean updatedEvent = events.update(appointment.getEvent());
				ClinicDB.closeConnection();
				
				Alert alert;
				if(updated && updatedEvent) {
					alert = new Alert (AlertType.INFORMATION);
					alert.setTitle("Reservation Successful!");
					alert.setHeaderText(null);
					alert.setContentText("Successfully reserved: " + title);
					alert.showAndWait();
				} else {
					alert = new Alert (AlertType.ERROR);
					alert.setTitle("Database Error");
					alert.setHeaderText(null);
					alert.setContentText("There was an error in the database connection!");
					alert.showAndWait();
				}
			});
		});		
	}
	
	public void setLoginVisible () {
		login.setVisible(true);       
		register.setVisible(false); 
		start.setVisible(false);       
		appointment.setVisible(false);  
		week.setVisible(false);        
		day.setVisible(false);         
	}
	
	public void setRegisterVisible () {
		login.setVisible(false);       
		register.setVisible(true); 
		start.setVisible(false);       
		appointment.setVisible(false);  
		week.setVisible(false);        
		day.setVisible(false);         
	}
	
	public void setStartVisible () {
		login.setVisible(false);       
		register.setVisible(false); 
		start.setVisible(true);       
		appointment.setVisible(false);  
		week.setVisible(false);        
		day.setVisible(false);         
	}
	
	public void setAppointmentsVisible () {
		login.setVisible(false);       
		register.setVisible(false); 
		start.setVisible(false);        
		appointment.setVisible(true);  
		week.setVisible(false);        
		day.setVisible(false);
		
		update();
	}
	
	public void setWeekVisible () {
		login.setVisible(false);       
		register.setVisible(false); 
		start.setVisible(false);       
		appointment.setVisible(false);  
		week.setVisible(true);        
		day.setVisible(false);  
		
		update();
	}
	
	public void setDayVisible () {
		login.setVisible(false);       
		register.setVisible(false); 
		start.setVisible(false);       
		appointment.setVisible(false);  
		week.setVisible(false);        
		day.setVisible(true);   
		
		update();
	}
	
	public void setCalendar(ModelGregorianCalendar modelGregorianCalendar) {
		this.mgc = modelGregorianCalendar;
		toolbarController.setCalendar(modelGregorianCalendar);
	}

	@Override
	public void update() {
		if(mainClient != null) {
			ClinicDB.openConnection();
			if(!toolbarController.reservationState()) {
				dayController.setEvents(appointments.getAppointmentsOfClientThisDay(mainClient, mgc.selectedDate()));

				List <Appointment> weekEvents = new ArrayList <Appointment> ();
				
				for (int i = 0; i < mgc.getWeek().size(); i++) {
					Iterator <Appointment> tempEvents = appointments.getAppointmentsOfClientThisDay(mainClient, mgc.getWeek().get(i));
					while(tempEvents.hasNext()) {
						weekEvents.add(tempEvents.next());
					}
				}
				
				weekController.setEvents(weekEvents.iterator());
				
				if(toolbarController.isDay())
					appointmentController.setEvents(appointments.getAppointmentsOfClientThisDay(mainClient, mgc.selectedDate()));
				else
					appointmentController.setEvents(weekEvents.iterator());

				appointmentController.cancelButtonVisibility(true);
				appointmentController.removeButtonVisibility(false);
			} else {
				List <Doctor> doctors = toolbarController.getDoctorsInput();
				List <Appointment> dayEvents = new ArrayList <Appointment> ();
				List <Appointment> weekEvents = new ArrayList <Appointment> ();
				
				for(int i = 0; i < doctors.size(); i++) {
					Iterator <Appointment> events = appointments.getFreeAppointmentsOfDoctorThisDay(doctors.get(i), mgc.selectedDate());
					
					while (events.hasNext()) {
						dayEvents.add(events.next());
					}
					
					for (int j = 0; j < mgc.getWeek().size(); j++) {
						Iterator <Appointment> tempEvents = appointments.getFreeAppointmentsOfDoctorThisDay(doctors.get(i), mgc.getWeek().get(j));
						while(tempEvents.hasNext()) {
							weekEvents.add(tempEvents.next());
						}
					}	
				}
				
				if(toolbarController.isDay())
					appointmentController.setEvents(dayEvents.iterator());
				else
					appointmentController.setEvents(weekEvents.iterator());
				
				weekController.setEvents(weekEvents.iterator());
				dayController.setEvents(dayEvents.iterator());
				
				appointmentController.cancelButtonVisibility(false);
				appointmentController.removeButtonVisibility(true);
			}
			
			dayController.initializeButtons(appointments, events, mainClient);
			weekController.initializeButtons(appointments, events, mainClient);
			weekController.setDateLabel(mgc.getWeek());
			
			ClinicDB.closeConnection();
		}
	}

	public void setEvents(EventCollection events) {
		this.events = events;
		
		dayController.initializeButtons(appointments, events, mainClient);
		weekController.initializeButtons(appointments, events, mainClient);
	}
}
