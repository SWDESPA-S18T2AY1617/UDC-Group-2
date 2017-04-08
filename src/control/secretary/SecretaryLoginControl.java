package control.secretary;

import java.util.Iterator;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.calendar.Secretary;
import model.storage.ClinicDB;
import model.storage.SecretaryCollection;

public class SecretaryLoginControl {
	
	@FXML private Button loginButton;
	@FXML private Button backButton;
    @FXML private PasswordField passwordText;
    @FXML private TextField usernameText;
    
    public void initializeButtons(SecretaryMainControl secMainController, SecretaryCollection secCollections) {
    	loginButton.setOnAction(event -> {
    		if(usernameText.getText() != null && passwordText.getText() != null) {
    			ClinicDB.openConnection();
    			if(ClinicDB.isOpen()) {
    				Iterator<Secretary> sec = secCollections.getAll();
    				
    				while(sec.hasNext()) {
    					Secretary s = sec.next();
    					if(s.login(usernameText.getText(), passwordText.getText())) {
    						Alert alert = new Alert(AlertType.INFORMATION);
    		       			alert.setTitle("SUCCESSFUL LOGIN");
    		       			alert.setHeaderText(null);
    		       			alert.setContentText("Welcome " + s.getName().toString() + "!");
    		       			alert.showAndWait();
    		       			
    		       			secMainController.setDayVisible();
    		       			secMainController.enableLogout();
    		       			secMainController.initializeComboBox(secCollections);
    					}
    					
    					else {
    						Alert alert = new Alert(AlertType.ERROR);
    			   			alert.setTitle("TRY AGAIN");
    			   			alert.setHeaderText(null);
    			   			alert.setContentText("Invalid username and password.");
    			   			alert.showAndWait();
    					}
    				}
    				ClinicDB.closeConnection();
    			}
    		}
    		
    		else if(usernameText.getText() == null) {
    			Alert alert = new Alert(AlertType.ERROR);
	   			alert.setTitle("FILL UP ALL FIELDS");
	   			alert.setHeaderText(null);
	   			alert.setContentText("Please enter a username.");
	   			alert.showAndWait();
    		}
    		
    		else {
    			Alert alert = new Alert(AlertType.ERROR);
	   			alert.setTitle("FILL UP ALL FIELDS");
	   			alert.setHeaderText(null);
	   			alert.setContentText("Please enter pasword.");
	   			alert.showAndWait();
    		}
    	});
    	
    	backButton.setOnAction(event -> {
    		secMainController.setStartupVisible();
    		clearFields();
    	});
    }
    
    public void clearFields () {
    	usernameText.clear();
    	passwordText.clear();
    }
}
