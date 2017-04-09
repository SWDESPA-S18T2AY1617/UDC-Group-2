package control.secretary;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Name;
import model.Secretary;
import model.storage.ClinicDB;
import model.storage.SecretaryCollection;

public class SecretaryRegisterControl {
	@FXML private Button backButton;
	@FXML private PasswordField passwordText;
	@FXML private TextField lastNameText;
	@FXML private TextField usernameText;
	@FXML private TextField middleNameText;
	@FXML private TextField firstNameText;
	@FXML private Button registerButton;
	
	public void setEvents (SecretaryMainControl parent, SecretaryCollection Secretaries) {
		backButton.setOnAction(event -> {
			parent.setStartupVisible();
		});
		
		registerButton.setOnAction(event -> {
			if(	!lastNameText.getText().trim().isEmpty() &&
				!usernameText.getText().trim().isEmpty() && 
				!middleNameText.getText().trim().isEmpty() &&
				!firstNameText.getText().trim().isEmpty() &&
				!passwordText.getText().trim().isEmpty()) {
				
				Secretary secretary = new Secretary ();
				
				ClinicDB.openConnection();
				secretary.setId(Secretaries.lastUpdatedID() + 1);
				ClinicDB.closeConnection();
				
				secretary.setName(new Name ());
				secretary.getName().setFirst(firstNameText.getText());
				secretary.getName().setLast(lastNameText.getText());
				secretary.getName().setMiddle(middleNameText.getText());
				secretary.setUsername(usernameText.getText());
				secretary.setPassword(passwordText.getText());
				
				ClinicDB.openConnection();
				if (Secretaries.add(secretary)) {
					Alert alert = new Alert (AlertType.CONFIRMATION);
					alert.setTitle("Registration Successful!");
					alert.setHeaderText(null);
					alert.setContentText("Please login your details!");
	        		alert.showAndWait();
				} else {
					Alert alert = new Alert (AlertType.ERROR);
					alert.setTitle("Database error!");
					alert.setHeaderText(null);
					alert.setContentText("Failed to add Secretary details!");
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
