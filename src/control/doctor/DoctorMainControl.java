package control.doctor;

import control.AgendaControl;
import control.CalendarControl;
import control.DayControl;
import control.ToolbarControl;
import control.WeekControl;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import model.Client;
import model.Doctor;
import model.ModelGregorianCalendar;
import model.storage.AppointmentCollection;
import model.storage.ClientCollection;
import model.storage.ClinicDB;
import model.storage.DoctorCollection;
import model.storage.EventCollection;

public class DoctorMainControl {

    @FXML private AnchorPane calendarView;
    @FXML private AnchorPane agendaView;
    @FXML private AnchorPane dayView;
    @FXML private AnchorPane weekView;
    @FXML private AnchorPane toolbar;
    
    @FXML private ToolbarControl toolbarController;
    @FXML private CalendarControl calendarViewController;
    @FXML private AgendaControl agendaViewController;
    @FXML private DayControl dayViewController;
    @FXML private WeekControl weekViewController;
    
	private AppointmentCollection appointments;
	private EventCollection events;
	private DoctorCollection doctors;
	private Doctor mainDoctor;
	
	private ModelGregorianCalendar mgc;
	
	public void setCalendar(ModelGregorianCalendar modelGregorianCalendar) {
		toolbarController.setCalendar(modelGregorianCalendar);
	}
	
//	public void setAppointments(AppointmentCollection appointments) {
//		this.appointments = appointments;
//		appointments.register(this);
//	}
//	
//	public void setClients(DoctorCollection clients) {
//		loginController.setEvents(this, clients);
//		registerController.setEvents(this, clients);
//	}
//	
//	public void setDoctors(DoctorCollection doctors) {
//		this.doctors = doctors;
//		List <Doctor> doctors = new A
//		toolbarController.setDoctors(doctors);
//	}
	
	
}
