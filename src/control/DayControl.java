package control;
import java.util.Iterator;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import model.calendar.Appointment;
import model.calendar.Client;

import model.storage.AppointmentCollection;
import model.storage.EventCollection;

public class DayControl {
	@FXML private AnchorPane dayInner;
	@FXML private DayInnerControl dayInnerController;

	@FXML
	 public void initialize () {
		 dayInnerController.initialize();
	 }
		
	 public void setEvents (Iterator <Appointment> events) {
		 dayInnerController.setDataEvents(events);
	 }
	 
	 public void initializeButtons (AppointmentCollection collection, EventCollection events, Client client) {
		 dayInnerController.initializeButtons(collection, events, client);
	 }

	public void setDocAppointments(Iterator<Appointment> all) {
		// TODO Auto-generated method stub
		
	}
	
}