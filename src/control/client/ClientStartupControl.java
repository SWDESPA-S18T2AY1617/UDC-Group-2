package control.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ClientStartupControl {

	@FXML private Button signinButton;
	@FXML private Button registerButton;
	
	public void setEvents(ClientMainControl parent) {
		signinButton.setOnAction(event -> {
			parent.setLoginVisible();
		});
		
		registerButton.setOnAction(event -> {
			parent.setRegisterVisible();
		});
	}

}
