package control;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.util.Iterator;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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

public class PopupFreeSlotControl extends PopupControl {
	@FXML private Button reserveButton;
	@FXML private TextField appointmentTitle;
	@FXML private Text detailsText;
	@FXML private Rectangle rectangle;
	@FXML private Polygon triangle;
	@FXML private ChoiceBox <Client> clientList;
	@FXML private ChoiceBox <LocalTime> timeStart;
	@FXML private ChoiceBox <LocalTime> timeEnd;
	@FXML private Text appointmentForWhat;
	@FXML private Text reservedFor;
	@FXML private AnchorPane doctor;
	@FXML private DatePicker date;
	@FXML private Button updateTime;
	@FXML private AnchorPane client;
	
	private void setReserveButton (AppointmentCollection appointments, EventCollection events, ClientCollection clients, Client client) {
		updateTime.setOnAction(update -> {
			EventDetails details = new EventDetails();
			details.setTimeEnd(timeEnd.getSelectionModel().getSelectedItem());
			details.setTimeStart(timeStart.getSelectionModel().getSelectedItem());
			details.setDayOfMonth(date.getValue().getDayOfMonth());
			details.setYear(Year.of(date.getValue().getYear()));
			details.setMonth(date.getValue().getMonth());
			details.setColor(event.getDetails().getColor());
			event.setDetails(details);
			
			ClinicDB.openConnection();
			Alert alert;
			
			if(!events.isOverlapping(date.getValue(), timeStart.getSelectionModel().getSelectedItem(), timeEnd.getSelectionModel().getSelectedItem())) {
				ClinicDB.closeConnection();
				ClinicDB.openConnection();
				if(events.update(event)) {
					alert = new Alert (AlertType.INFORMATION);
					alert.setTitle("Update Successful!");
					alert.setHeaderText(null);
					alert.setContentText("Successfully Updated!");
					alert.showAndWait();
				} else {
					alert = new Alert (AlertType.ERROR);
					alert.setTitle("DB Error!");
	    			alert.setHeaderText(null);
	    			alert.setContentText("There was an error in the SQL!");
	    			alert.showAndWait();
				}
				
				ClinicDB.closeConnection();
				ClinicDB.openConnection();
				appointments.update(event);
				ClinicDB.closeConnection();
			} else {
				alert = new Alert (AlertType.ERROR);
				alert.setTitle("Overlapping Appointments!");
    			alert.setHeaderText(null);
    			alert.setContentText("Cannot create schedule due to overlapping appointments!");
    			alert.showAndWait();
			}
			
			
			setUserData(event);
		});
		
		reserveButton.setOnAction(reserve -> {
			
			if(client != null) {
				event.setClient(client);
			} else {
				event.setClient(clientList.getSelectionModel().getSelectedItem());
			}
					
			if (clients == null) {
				ClinicDB.openConnection();
				Alert alert = new Alert (AlertType.CONFIRMATION);
				alert.setTitle("Free up Confirmation");
				alert.setHeaderText(null);
				alert.setContentText("Are you sure you want to remove this appointment?");
				
				Optional <ButtonType> result = alert.showAndWait();
				
				if (result.get() == ButtonType.OK){
					ClinicDB.openConnection();
					boolean updated = appointments.delete(event);
					ClinicDB.closeConnection();
					ClinicDB.openConnection();
					boolean updatedEvent = events.update(this.event.getEvent());
					ClinicDB.closeConnection();
					
					if(updated && updatedEvent) {
						alert = new Alert (AlertType.INFORMATION);
						alert.setTitle("Free up Successful!");
						alert.setHeaderText(null);
						alert.setContentText("Successfully freed: " + event.getDetails().getTimeStart().toString());
						alert.showAndWait();
					} else {
						alert = new Alert (AlertType.ERROR);
						alert.setTitle("Database Error");
						alert.setHeaderText(null);
						alert.setContentText("There was an error in the database connection!");
						alert.showAndWait();
					}
				} 
			} else {
				event.setTitle(appointmentTitle.getText());
				
				if(event.getClient() != null) {
					Alert alert = new Alert (AlertType.CONFIRMATION);
					alert.setTitle("Reservation Confirmation");
					alert.setHeaderText(null);
					alert.setContentText("Are you sure you want to reserve this appointment?");
					
					Optional <ButtonType> result = alert.showAndWait();
					
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
					
				} else {
					Alert alert = new Alert (AlertType.INFORMATION);
					alert.setTitle("Reservation Error");
					alert.setHeaderText(null);
					alert.setContentText("Reservation Unsuccesful!");
					alert.showAndWait();
				}
			}
			
			setUserData(event);
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
		appointmentForWhat.setFill(event.getDetails().getColor().invert());
		reservedFor.setFill(event.getDetails().getColor().invert());

        timeStart.getSelectionModel().select(event.getDetails().getTimeStart());
          
        timeEnd.getSelectionModel().select(((EventDetails) event.getDetails()).getTimeEnd());
        
        date.setValue(LocalDate.of(event.getDetails().getYear().getValue(), event.getDetails().getMonth(), event.getDetails().getDayOfMonth()));
	}

	@Override
	public void initializeButtons(AppointmentCollection appointments, EventCollection events, ClientCollection clients, Client client) {
		if(clients == null) {
			reserveButton.setText("Free up");
			clientList.setVisible(false);
			appointmentTitle.setVisible(false);
			appointmentForWhat.setVisible(false);
			reservedFor.setVisible(false);
			this.client.setVisible(false);
			this.doctor.setVisible(true);
			
			timeStart.getItems().clear();
	        for (int i = 0; i < 24; i ++) {
	        	timeStart.getItems().add(LocalTime.of(i, 0));
	        	timeStart.getItems().add(LocalTime.of(i, 30));
	    	}
	        
	        timeStart.setOnAction(handler -> {
	        	timeEnd.getItems().clear();
	        	for (int i = timeStart.getSelectionModel().getSelectedIndex(); i < timeStart.getItems().size(); i++) {
	        		timeEnd.getItems().add(timeStart.getItems().get(i));
	        	}
	        });
	        
	        timeStart.getSelectionModel().select(event.getDetails().getTimeStart());
	        
	        for (int i = timeStart.getSelectionModel().getSelectedIndex(); i < timeStart.getItems().size(); i++) {
	    		timeEnd.getItems().add(timeStart.getItems().get(i));
	    	}
	        
		} else {

			this.client.setVisible(true);
			this.doctor.setVisible(false);
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
		}
		setReserveButton(appointments, events, clients, client);
	}
}