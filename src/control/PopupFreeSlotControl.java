package control;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.calendar.Appointment;
import model.calendar.Client;
import model.calendar.EventDetails;
import model.storage.AppointmentCollection;
import model.storage.ClinicDB;
import model.storage.EventCollection;

public class PopupFreeSlotControl extends PopupControl {
	@FXML private Button reserveButton;
	@FXML private TextField appointmentTitle;
	@FXML private Text detailsText;
	@FXML private Rectangle rectangle;
	@FXML private Polygon triangle;
	
	private void setReserveButton (AppointmentCollection appointments, EventCollection events, Client client) {
		reserveButton.setOnAction(reserve -> {
			event.setClient(client);
			event.setTitle(appointmentTitle.getText());
			
			if(event.getClient() != null) {
				Alert alert = new Alert (AlertType.CONFIRMATION);
				alert.setTitle("Reservation Confirmation");
				alert.setHeaderText(null);
				alert.setContentText("Are you sure you want to reserve this appointment?");
				
				Optional <ButtonType> result = alert.showAndWait();
				
				ClinicDB.openConnection();
				
				if (result.get() == ButtonType.OK){
					ClinicDB.openConnection();
					boolean updated = appointments.update(event);
					ClinicDB.closeConnection();
					ClinicDB.openConnection();
					boolean updatedEvent = events.update(this.event.getEvent());
					ClinicDB.closeConnection();
					
					if(updated && updatedEvent) {
						alert = new Alert (AlertType.INFORMATION);
						alert.setTitle("Reservation Successful!");
						alert.setHeaderText(null);
						alert.setContentText("Successfully reserved: " + event.toString());
						alert.showAndWait();
					} else {
						alert = new Alert (AlertType.ERROR);
						alert.setTitle("Database Error");
						alert.setHeaderText(null);
						alert.setContentText("There was an error in the database connection!");
						alert.showAndWait();
					}
				} 
				
				ClinicDB.closeConnection();
			} else {
				Alert alert = new Alert (AlertType.INFORMATION);
				alert.setTitle("Reservation Error");
				alert.setHeaderText(null);
				alert.setContentText("Reservation Unsuccesful!");
				alert.showAndWait();
			}
		});
	}
	
	@Override
	public void setUserData (Appointment event) {
		this.event = event;
		
		detailsText.setText(event.getDoctor().toString() + " \n" +
							event.getDetails().getTimeStart().toString() + " - " +
							((EventDetails)event.getDetails()).getTimeEnd().toString());
						
		
		rectangle.setFill(event.getDetails().getColor());
		triangle.setFill(event.getDetails().getColor());
		detailsText.setFill(event.getDetails().getColor().invert());
	}

	@Override
	public void initializeButtons(AppointmentCollection appointments, EventCollection events, Client client) {
		setReserveButton(appointments, events, client);
	}
}