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
    //@FXML private PasswordField passwordText;
    //@FXML private TextField usernameText;
    @FXML private TextField idText;
    
    public void initializeButtons(ClientMainControl clientMainController, ClientCollection clientCollections) {
    	loginButton.setOnAction(event -> {
    		if(idText.getText() != null) {
    			ClinicDB.openConnection();
    			if(ClinicDB.isOpen()) {
    				Iterator<Client> client = clientCollections.getAll();
    				
    				while(client.hasNext()) {
    					Client c = client.next();
    					if(c.login(Integer.parseInt(idText.getText()))) {
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
    			   			alert.setContentText("Invalid ID.");
    			   			alert.showAndWait();
    					}
    				}
    				ClinicDB.closeConnection();
    			}
    		}
    		
    		else if(idText.getText() == null) {
    			Alert alert = new Alert(AlertType.ERROR);
	   			alert.setTitle("FILL UP ALL FIELDS");
	   			alert.setHeaderText(null);
	   			alert.setContentText("Please enter ID.");
	   			alert.showAndWait();
    		}
    		/*
    		else {
    			Alert alert = new Alert(AlertType.ERROR);
	   			alert.setTitle("FILL UP ALL FIELDS");
	   			alert.setHeaderText(null);
	   			alert.setContentText("Please enter pasword.");
	   			alert.showAndWait();
    		}
    		*/
    	});
    	
    	backButton.setOnAction(event -> {
    		clientMainController.setStartupVisible();
    		clearFields();
    	});
    }
    
    public void clearFields () {
    	idText.clear();
    	//usernameText.clear();
    	//passwordText.clear();
    }
}
