package control.client;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.calendar.Client;
import model.storage.ClientCollection;
import model.storage.ClinicDB;

public class ClientLoginControl {

	@FXML private Button loginButton;
	@FXML private Button backButton;
	@FXML private PasswordField passwordField;
	@FXML private TextField userName;

	public void setEvents (ClientMainControl parent, ClientCollection clients) {
		backButton.setOnAction(event -> {
			parent.setStartVisible();
		});
		
		loginButton.setOnAction(event -> {
			if(!userName.getText().trim().isEmpty() && !passwordField.getText().trim().isEmpty()) {
				ClinicDB.openConnection();
				Client client = clients.get(userName.getText(), passwordField.getText());
				if (client != null) {
					parent.setMainClient(client);
					Alert alert = new Alert (AlertType.INFORMATION);
					alert.setTitle("Successful Login!");
					alert.setHeaderText(null);
					alert.setContentText("Welcome " + client.getName() + "!");
	        		alert.showAndWait();
	        		
					parent.setAppointmentsVisible();
				} else {
					Alert alert = new Alert (AlertType.ERROR);
					alert.setTitle("No client found!");
					alert.setHeaderText(null);
					alert.setContentText("Incorrect username or password!");
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
