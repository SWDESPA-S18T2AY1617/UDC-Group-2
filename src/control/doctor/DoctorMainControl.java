package control.doctor;

import control.AppointmentControl;
import control.CalendarControl;
import control.DayControl;
import control.ToolbarControl;
import control.WeekControl;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import model.AppointmentObserver;
import model.Doctor;
import model.ModelGregorianCalendar;
import model.storage.AppointmentCollection;
import model.storage.DoctorCollection;
import model.storage.EventCollection;

public class DoctorMainControl extends AppointmentObserver {

    @FXML private AnchorPane calendarView;
    @FXML private AnchorPane agendaView;
    @FXML private AnchorPane dayView;
    @FXML private AnchorPane weekView;
    @FXML private AnchorPane toolbar;
    
    @FXML private ToolbarControl toolbarController;
    @FXML private CalendarControl calendarViewController;
    @FXML private AppointmentControl agendaViewController;
    @FXML private DayControl dayViewController;
    @FXML private WeekControl weekViewController;
    
	private AppointmentCollection appointments;
	private EventCollection events;
	private DoctorCollection doctors;
	private Doctor mainDoctor;
	
	private ModelGregorianCalendar mgc;
	
	@FXML 
	public void initialize () {
		toolbarController.setParent(this);
	}
	public void setCalendar(ModelGregorianCalendar modelGregorianCalendar) {
		toolbarController.setCalendar(modelGregorianCalendar);
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
