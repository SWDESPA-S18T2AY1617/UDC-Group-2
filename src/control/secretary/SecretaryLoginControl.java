package control.secretary;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Secretary;
import model.storage.ClinicDB;
import model.storage.SecretaryCollection;

public class SecretaryLoginControl {
	@FXML private Button loginButton;
	@FXML private Button backButton;
	@FXML private PasswordField passwordField;
	@FXML private TextField userName;

	public void setEvents (SecretaryMainControl parent, SecretaryCollection secretaries) {
		backButton.setOnAction(event -> {
			parent.setStartupVisible();
		});
		
		loginButton.setOnAction(event -> {
			if(!userName.getText().trim().isEmpty() && !passwordField.getText().trim().isEmpty()) {
				ClinicDB.openConnection();
				Secretary secretary = secretaries.get(userName.getText(), passwordField.getText());
				if (secretary != null) {
					parent.setMainSecretary(secretary);
					Alert alert = new Alert (AlertType.INFORMATION);
					alert.setTitle("Successful Login!");
					alert.setHeaderText(null);
					alert.setContentText("Welcome " + secretary.getName() + "!");
	        		alert.showAndWait();
	        		
					parent.setAgendaVisible();
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
