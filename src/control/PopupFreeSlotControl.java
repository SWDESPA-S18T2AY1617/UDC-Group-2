package control;

import java.util.Iterator;
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
import model.Appointment;
import model.Client;
import model.EventDetails;
import model.storage.AppointmentCollection;
import model.storage.ClientCollection;
import model.storage.ClinicDB;
import model.storage.EventCollection;
import javafx.scene.control.ChoiceBox;

public class PopupFreeSlotControl extends PopupControl {
	@FXML private Button reserveButton;
	@FXML private TextField appointmentTitle;
	@FXML private Text detailsText;
	@FXML private Rectangle rectangle;
	@FXML private Polygon triangle;
	@FXML private ChoiceBox <Client> clientList;
	
	private void setReserveButton (AppointmentCollection appointments, EventCollection events, ClientCollection clients, Client client) {
		reserveButton.setOnAction(reserve -> {
			
			if(client != null) {
				event.setClient(client);
			} else {
				event.setClient(clientList.getSelectionModel().getSelectedItem());
			}

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
	public void initializeButtons(AppointmentCollection appointments, EventCollection events, ClientCollection clients, Client client) {
		clientList.getItems().clear();
		ClinicDB.openConnection();
		for(Iterator<Client> itr = clients.getAll(); itr.hasNext(); ) {
			clientList.getItems().add(itr.next());
		}
		ClinicDB.closeConnection();
		
		if (clientList.getItems().size() != 0) {
			clientList.getSelectionModel().select(0);
			
			if(client != null) {

				for (int i = 0; i < clientList.getItems().size(); i++) {
					if(clientList.getItems().get(i).getId() == client.getId())
						clientList.getSelectionModel().select(i);
				}
				
				clientList.setDisable(true);
			}
		
		}
		
		setReserveButton(appointments, events, clients, client);
	}
}