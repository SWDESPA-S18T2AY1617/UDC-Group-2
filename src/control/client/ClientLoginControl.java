package control.client;

import java.util.Iterator;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.calendar.Client;
import model.storage.ClinicDB;
import model.storage.ClientCollection;

public class ClientLoginControl {

	@FXML private Button loginButton;
	@FXML private Button backButton;
    @FXML private PasswordField passwordText;
    @FXML private TextField usernameText;
    
    public void initializeButtons(ClientMainControl clientMainController, ClientCollection clientCollections) {
    	loginButton.setOnAction(event -> {
    		if(usernameText.getText() != null && passwordText.getText() != null) {
    			ClinicDB.openConnection();
    			if(ClinicDB.isOpen()) {
    				Iterator<Client> client = clientCollections.getAll();
    				
    				while(client.hasNext()) {
    					Client c = client.next();
    					if(c.login(usernameText.getText(), passwordText.getText())) {
    						Alert alert = new Alert(AlertType.INFORMATION);
    		       			alert.setTitle("SUCCESSFUL LOGIN");
    		       			alert.setHeaderText(null);
    		       			alert.setContentText("Welcome " + c.getName().toString() + "!");
    		       			alert.showAndWait();
    		       			
    		       			clientMainController.setDayVisible();
    		       			clientMainController.enableLogout();
    		       			clientMainController.initializeComboBox(clientCollections);
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
    		clientMainController.setStartupVisible();
    		clearFields();
    	});
    }
    
    public void clearFields () {
    	usernameText.clear();
    	passwordText.clear();
    }
}
