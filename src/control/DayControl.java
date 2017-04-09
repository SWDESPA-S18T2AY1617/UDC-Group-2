package control;
import java.util.Iterator;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import model.calendar.Appointment;
import model.calendar.Event;
import model.storage.AppointmentCollection;

public class DayControl {
	@FXML private AnchorPane dayInner;
	@FXML private DayInnerControl dayInnerController;

	@FXML
	 public void initialize () {
		 dayInnerController.initialize();
	 }
		
	 public void setAppointments (Iterator <Appointment> appointments) {
		 dayInnerController.setDataEvents(appointments);
	 }
	 
	 public void initializeButtons (AppointmentCollection collection) {
		 dayInnerController.initializeButtons(collection);
	 }
	
}