package control.secretary;

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
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import model.Appointment;
import model.AppointmentObserver;
import model.ModelGregorianCalendar;
import model.Secretary;
import model.Client;
import model.Doctor;
import model.storage.ClientCollection;
import model.storage.ClinicDB;
import model.storage.DoctorCollection;
import model.storage.EventCollection;
import model.storage.SecretaryCollection;

public class SecretaryMainControl extends AppointmentObserver {
	
	@FXML private AnchorPane appointment;
    @FXML private AnchorPane day;
    @FXML private AnchorPane week;
    @FXML private AnchorPane toolbar;
    @FXML private AnchorPane login;
    @FXML private AnchorPane register;
    @FXML private AnchorPane startup;
    
    @FXML private AppointmentControl appointmentController;
    @FXML private DayControl dayController;
    @FXML private WeekControl weekController;
    @FXML private SecretaryRegisterControl registerController;
    @FXML private SecretaryLoginControl loginController;
    @FXML private SecretaryStartupControl startupController;
    @FXML private ToolbarControl toolbarController; 

	private EventCollection events;
	private DoctorCollection doctors;
	private ClientCollection clients;
	private Secretary mainSecretary;
	
	private ModelGregorianCalendar mgc;
	
	public void setDoctors(DoctorCollection doctors) {
		this.doctors = doctors;
		ClinicDB.openConnection();
		toolbarController.setDoctors(this.doctors.getAll());
		ClinicDB.closeConnection();
	}
	
	public void setEvents (EventCollection events) {
		this.events = events;
	}
	
	public void setClients (ClientCollection clients) {
		this.clients = clients;
	}
	
    public void setCalendar (ModelGregorianCalendar mgc) {
    	this.mgc = mgc;
    	toolbarController.setCalendar(mgc);
    }
    
    @FXML
	public void initialize () {
		setToolbarPane();
		setStartupVisible();
		setAppointmentsPane();
		setStart();
		
		
		setStartupVisible();
	}

	private void setStart() {
		startupController.setEvents(this);
	}

	public void setMainSecretary (Secretary s) {
		this.mainSecretary = s;
		toolbar.setDisable(false);
		
		if (s == null) {
			toolbar.setDisable(true);
			setStartupVisible();
		}
	}
	
	public void setToolbarPane () {
		toolbarController.setSecretaryViews();
		toolbarController.setParent(this);
		toolbarController.setLogoutAction(logout -> {
			Alert alert = new Alert (AlertType.CONFIRMATION);
			alert.setTitle("Logout Confirmation!");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to logout?");
    		alert.showAndWait();
    		
    		if(alert.getResult() == ButtonType.OK) {
    			setStartupVisible();
    			mainSecretary = null;
    			toolbar.setDisable(true);
    		} 
		});
		
		toolbarController.setWeekOrDay(day, week, appointment);
		toolbarController.setAgendaOrCalendar(day, week, appointment);
		toolbarController.setReservationAction(reserve -> {
			update();
		});
		
		setAgendaVisible();
		
		toolbarController.setDoctors(null);
		toolbar.setDisable(true);
	}
    
    public void setStartupVisible () {
    	startup.setVisible(true);
    	appointment.setVisible(false);
    	week.setVisible(false);
    	day.setVisible(false);
    	login.setVisible(false);
    	register.setVisible(false);
    	
    	update();
    }
    
    public void setLoginVisible () {
    	startup.setVisible(false);
    	appointment.setVisible(false);
    	week.setVisible(false);
    	day.setVisible(false);
    	login.setVisible(true);
    	register.setVisible(false);
    	update();
    }
    
    public void setRegisterVisible () {
    	startup.setVisible(false);
    	appointment.setVisible(false);
    	week.setVisible(false);
    	day.setVisible(false);
    	login.setVisible(false);
    	register.setVisible(true);
    	update();
    }
    
    public void setAgendaVisible () {
    	startup.setVisible(false);
    	appointment.setVisible(true);
    	week.setVisible(false);
    	day.setVisible(false);
    	login.setVisible(false);
    	register.setVisible(false);
    	update();
    }
    
    public void setDayVisible () {
    	startup.setVisible(false);
    	appointment.setVisible(false);
    	week.setVisible(false);
    	day.setVisible(true);
    	login.setVisible(false);
    	register.setVisible(false);
    	update();
    }
    
    public void setWeekVisible () {
    	startup.setVisible(false);
    	appointment.setVisible(false);
    	week.setVisible(true);
    	day.setVisible(false);
    	login.setVisible(false);
    	register.setVisible(false);
    	
    	update();
    }

	@Override
	public void update() {
		if(mainSecretary != null) {
			ClinicDB.openConnection();
			List <Doctor> doctors = toolbarController.getDoctorsInput();
			List <Appointment> dayEvents = new ArrayList <Appointment> ();
			List <Appointment> weekEvents = new ArrayList <Appointment> ();
			
			for(int i = 0; i < doctors.size(); i++) {
				
				if(toolbarController.isFree()) {
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
				
				if(toolbarController.isOccupied()) {
					Iterator <Appointment> events = appointments.getOccupiedAppointmentsOfDoctorThisDay(doctors.get(i), mgc.selectedDate());
					
					while (events.hasNext()) {
						dayEvents.add(events.next());
					}
					
					for (int j = 0; j < mgc.getWeek().size(); j++) {
						Iterator <Appointment> tempEvents = appointments.getOccupiedAppointmentsOfDoctorThisDay(doctors.get(i), mgc.getWeek().get(j));
						while(tempEvents.hasNext()) {
							weekEvents.add(tempEvents.next());
						}
					}
				}			
			}
			
			if(toolbarController.isDay())
				appointmentController.setEvents(dayEvents.iterator());
			else
				appointmentController.setEvents(weekEvents.iterator());
			
			weekController.setEvents(weekEvents.iterator());
			dayController.setEvents(dayEvents.iterator());
			
			dayController.initializeButtons(appointments, events, clients, null);
			weekController.initializeButtons(appointments, events, clients, null);
			weekController.setDateLabel(mgc.getWeek());
			
			appointmentController.cancelButtonVisibility(false);
			appointmentController.removeButtonVisibility(true);
			
			ClinicDB.closeConnection();
		}
	}
	
	public void setAppointmentsPane () {
		appointmentController.setPlaceholder("No appointments!");
		appointmentController.cancelButtonVisibility(false);
		appointmentController.removeButtonVisibility(true);
		
		appointmentController.setReserveButton(event -> {			
			List <Client> choices = new ArrayList<>();
			
			ClinicDB.openConnection();
			
			for (Iterator <Client> i = clients.getAll(); i.hasNext(); ) {
				choices.add(i.next());
			}
			
			ClinicDB.closeConnection();

			ChoiceDialog <Client> choiceDialog = new ChoiceDialog <Client> (choices.get(0), choices);
			choiceDialog.setTitle("Pick a client");
			choiceDialog.setHeaderText(null);
			choiceDialog.setContentText("Choose a client:");
			Optional<Client> result = choiceDialog.showAndWait();
			
			result.ifPresent(client -> {
				Appointment appointment = appointmentController.getListInput();
				
				appointment.setClient(client);
				
				TextInputDialog dialog = new TextInputDialog("");
				dialog.setTitle("Reservation Confirmation");
				dialog.setHeaderText(null);
				dialog.setContentText("Reservation for what?");

				Optional<String> titleResult = dialog.showAndWait();
				
				titleResult.ifPresent(title -> {
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
		});		
	}

	public void setSecretaries(SecretaryCollection secretaries) {
		loginController.setEvents(this, secretaries);
		registerController.setEvents(this, secretaries);
	}
}
