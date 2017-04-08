package control.secretary;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SecretaryStartupControl {
	
	@FXML private Button registerButton;
	@FXML private Button loginButton; 
	
	public void initializeButtons(SecretaryMainControl secMainController) {
		registerButton.setOnAction(event -> {
			secMainController.setRegisterVisible();
			secMainController.clearFields();
		});
		
		loginButton.setOnAction(event -> { 
			secMainController.setLoginVisible();
			secMainController.clearFields();
		});
	}
}
