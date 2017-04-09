package control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import model.calendar.Appointment;
import model.calendar.Client;
import model.calendar.Details;
import model.storage.AppointmentCollection;
import model.storage.EventCollection;

public class DayInnerControl {

	@FXML private AnchorPane schedule;
	private List <CellControl> cells;
	
	@FXML 
	public void initialize () {
		cells = new ArrayList <CellControl> ();
	}
	
	public void initializeButtons (AppointmentCollection appointments, EventCollection events, Client client) {
		if (cells != null) {
			for (CellControl cell: cells) {
				cell.initializeButtons(appointments, events, client);
			}
		}
	}
	
	public void setDataEvents (Iterator <Appointment> itr) {
		if (itr != null) {
			cells.clear();
			schedule.getChildren().clear();
				
			List <Appointment> events = new ArrayList<Appointment>();
			while(itr.hasNext()) {
				events.add(itr.next());
			}
			
			for (int i = 0; i < events.size(); i++) {
				cells.add(new CellControl(400, events.get(i)));
				schedule.getChildren().add(cells.get(i));
		
				cells.get(i).setLayoutX(0.0);			
		
				Details details = cells.get(i).getUserData().getDetails();
				
				float start = 0;
				
				start = details.getTimeStart().getHour()*60 + details.getTimeStart().getMinute();
		
				cells.get(i).setLayoutY(start);
			}
		}
	}
}