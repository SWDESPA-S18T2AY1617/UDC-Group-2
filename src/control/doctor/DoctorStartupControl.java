package control.doctor;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DoctorStartupControl {

    @FXML private Button registerButton;
    @FXML private Button signinButton;

    public void initializeButtons (DoctorMainControl dmc) {
    	 registerButton.setOnAction(event -> {
    		 dmc.setRegisterVisible();
    	 });
    	 
    	 signinButton.setOnAction(event -> { 
    		 dmc.setLoginVisible();
    	 });
    }
}
