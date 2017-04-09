package control;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import model.Appointment;
import model.Client;
import model.storage.AppointmentCollection;
import model.storage.EventCollection;
import javafx.scene.control.Label;

public class WeekControl {
	
	@FXML private AnchorPane weekInner;
	@FXML private WeekInnerControl weekInnerController;
	@FXML private Label datesLabel;
	
	@FXML
	 public void initialize () {
		 weekInnerController.initialize();
	 }
		
	 public void setEvents (Iterator <Appointment> events) {
		 weekInnerController.setDataEvents(events);
	 }
	 
	 public void initializeButtons (AppointmentCollection collection, EventCollection events, Client client) {
		 weekInnerController.initializeButtons(collection, events, client);
	 }

	public void setDateLabel(List<LocalDate> week) {
		datesLabel.setText(	week.get(0).toString() + " - " +
							week.get(week.size()-1).toString());
		
	}
}
