package control.client;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.calendar.Client;
import model.calendar.Name;
import model.storage.ClientCollection;
import model.storage.ClinicDB;

public class ClientRegisterControl {

	@FXML private Button backButton;
	@FXML private PasswordField passwordText;
	@FXML private TextField lastNameText;
	@FXML private TextField usernameText;
	@FXML private TextField middleNameText;
	@FXML private TextField firstNameText;
	@FXML private Button registerButton;
	
	public void setEvents (ClientMainControl parent, ClientCollection clients) {
		backButton.setOnAction(event -> {
			parent.setStartVisible();
		});
		
		registerButton.setOnAction(event -> {
			if(	!lastNameText.getText().trim().isEmpty() &&
				!usernameText.getText().trim().isEmpty() && 
				!middleNameText.getText().trim().isEmpty() &&
				!firstNameText.getText().trim().isEmpty() &&
				!passwordText.getText().trim().isEmpty()) {
				
				Client client = new Client ();
				
				ClinicDB.openConnection();
				client.setId(clients.lastUpdatedID() + 1);
				ClinicDB.closeConnection();
				
				client.setName(new Name ());
				client.getName().setFirst(firstNameText.getText());
				client.getName().setLast(lastNameText.getText());
				client.getName().setMiddle(middleNameText.getText());
				client.setUsername(usernameText.getText());
				client.setPassword(passwordText.getText());
				
				ClinicDB.openConnection();
				if (clients.add(client)) {
					Alert alert = new Alert (AlertType.CONFIRMATION);
					alert.setTitle("Registration Successful!");
					alert.setHeaderText(null);
					alert.setContentText("Please login your details!");
	        		alert.showAndWait();
				} else {
					Alert alert = new Alert (AlertType.ERROR);
					alert.setTitle("Database error!");
					alert.setHeaderText(null);
					alert.setContentText("Failed to add client details!");
	        		alert.showAndWait();
				}
				
				ClinicDB.closeConnection();
			} else {
				Alert alert = new Alert (AlertType.ERROR);
				alert.setTitle("Incomplete Fields");
				alert.setHeaderText(null);
				alert.setContentText("Please complete all fields!");
        		alert.showAndWait();
			}
		});
	}

}
