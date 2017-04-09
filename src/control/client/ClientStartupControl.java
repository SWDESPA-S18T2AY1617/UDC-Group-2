package control.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ClientStartupControl {

	@FXML private Button registerButton;
	@FXML private Button loginButton; 
	
	public void initializeButtons(ClientMainControl clientMainController) {
		registerButton.setOnAction(event -> {
			clientMainController.setRegisterVisible();
			clientMainController.clearFields();
		});
		
		loginButton.setOnAction(event -> { 
			clientMainController.setLoginVisible();
			clientMainController.clearFields();
		});
	}
}
