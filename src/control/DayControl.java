package control;
import java.util.Iterator;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import model.Appointment;
import model.Client;
import model.storage.AppointmentCollection;
import model.storage.ClientCollection;
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
	 
	 public void initializeButtons (AppointmentCollection collection, EventCollection events, ClientCollection clients, Client client) {
		 dayInnerController.initializeButtons(collection, events, clients, client);
	 }
}