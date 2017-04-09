package control.secretary;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SecretaryStartupControl {

	@FXML private Button signinButton;
	@FXML private Button registerButton;
	
	public void setEvents(SecretaryMainControl parent) {
		signinButton.setOnAction(event -> {
			parent.setLoginVisible();
		});
		
		registerButton.setOnAction(event -> {
			parent.setRegisterVisible();
		});
	}
}
