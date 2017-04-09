package control;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.calendar.Appointment;
import model.calendar.Client;
import model.calendar.Event;
import model.calendar.EventDetails;
import model.storage.AppointmentCollection;
import model.storage.ClinicDB;
import model.storage.EventCollection;

public class PopupAppointmentControl extends PopupControl {
	@FXML private Label nameLabel;
	@FXML private Label timeLabel;
	@FXML private Rectangle rectangle;
	@FXML private Polygon triangle;
	@FXML private Button deleteBtn;
	@FXML private Text text;
	
	private void setDeleteButton (AppointmentCollection apps, EventCollection events) {
		ClinicDB.openConnection();
		deleteBtn.setOnAction(e -> {
			Alert alert = new Alert (AlertType.CONFIRMATION);
			alert.setTitle("Delete Confirmation");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to cancel this appointment?");
    		
    		Optional <ButtonType> result = alert.showAndWait();
  
    		if (result.get() == ButtonType.OK){
    			boolean removed = apps.delete(this.event);
    			Event ev = this.event.getEvent();
        		ev.setTitle("FREE");
    			boolean another = events.update(ev);
    			
    			if(removed && another) {
    				alert = new Alert (AlertType.INFORMATION);
    				alert.setTitle("Delete Successful");
    				alert.setHeaderText(null);
    				alert.setContentText("Successfully deleted: " + event.getTitle());
    				alert.showAndWait();
    			} else {
    				alert = new Alert (AlertType.ERROR);
    				alert.setTitle("Database Error");
    				alert.setHeaderText(null);
    				alert.setContentText("There was an error in the database connection!");
    				alert.showAndWait();
    			}
    		} 
		});

		ClinicDB.closeConnection();
	}
	
	@Override
	public void setUserData (Appointment event) {
		this.event = event;
		
		if (event.getDetails() instanceof EventDetails) {
			timeLabel.setText(event.getDetails().getTimeStart().toString() + " - " 
								+ ((EventDetails)event.getDetails()).getTimeEnd().toString());
		}
		
		text.setText(	event.getTitle() + " \n" +
						event.getDoctor().toString() + " \n" +
						event.getClient().toString() + " \n");
		
		rectangle.setFill(event.getDetails().getColor());
		triangle.setFill(event.getDetails().getColor());
		timeLabel.setTextFill(event.getDetails().getColor().invert());
	}

	@Override
	public void initializeButtons(AppointmentCollection appointments, EventCollection events, Client client) {
		setDeleteButton(appointments, events);
	}
}