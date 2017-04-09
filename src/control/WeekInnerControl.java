package control;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import model.Appointment;
import model.Client;
import model.Details;
import model.storage.AppointmentCollection;
import model.storage.ClientCollection;
import model.storage.EventCollection;

public class WeekInnerControl {

	@FXML private AnchorPane monday;
	@FXML private AnchorPane tuesday;
	@FXML private AnchorPane wednesday;
	@FXML private AnchorPane thursday;
	@FXML private AnchorPane friday;
	private List <CellControl> cells;
	
	@FXML 
	public void initialize () {
		cells = new ArrayList <CellControl> ();
	}

	
	public void initializeButtons (AppointmentCollection appointments, EventCollection events, ClientCollection clients, Client client) {
		if (cells != null) {
			for (CellControl cell: cells) {
				cell.initializeButtons(appointments, events, clients, client);
			}
		}
	}
	
	
	
	public void setDataEvents (Iterator <Appointment> itr) {
		if (itr != null) {
			cells.clear();
			monday.getChildren().clear();
			tuesday.getChildren().clear();
			wednesday.getChildren().clear();
			thursday.getChildren().clear();
			friday.getChildren().clear();
				
			List <Appointment> events = new ArrayList<Appointment>();
			while(itr.hasNext()) {
				events.add(itr.next());
			}
			
			for (int i = 0; i < events.size(); i++) {
				cells.add(new CellControl(108, events.get(i)));
				
				LocalDate date = LocalDate.of
						(events.get(i).getDetails().getYear().getValue(),
						events.get(i).getDetails().getMonth(), 
						events.get(i).getDetails().getDayOfMonth());
				
				switch(date.getDayOfWeek()) {
					case MONDAY:
						monday.getChildren().add(cells.get(i));
						break;
					case TUESDAY:
						tuesday.getChildren().add(cells.get(i));
						break;
					case WEDNESDAY:
						wednesday.getChildren().add(cells.get(i));
						break;
					case THURSDAY:
						thursday.getChildren().add(cells.get(i));
						break;
					case FRIDAY:
						friday.getChildren().add(cells.get(i));
						break;
					default:
						
				}
				
				cells.get(i).setLayoutX(0.0);			
		
				Details details = ((Appointment) cells.get(i).getUserData()).getDetails();
				
				float start = 0;
				
				start = details.getTimeStart().getHour()*60 + details.getTimeStart().getMinute();
				
				cells.get(i).setLayoutY(start);
			}
		}
	}
}