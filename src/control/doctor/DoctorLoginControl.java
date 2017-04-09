package control.doctor;

import java.util.Iterator;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.Doctor;
import model.storage.ClinicDB;
import model.storage.DoctorCollection;

public class DoctorLoginControl {

    @FXML private Button loginBtn;
    @FXML private PasswordField passwordText;
    @FXML private TextField usernameText;
    @FXML private Button bckBtn;
    
    public void initializeButtons (DoctorMainControl dmc, DoctorCollection dc) {
    	loginBtn.setOnAction(event -> {
    		if(usernameText.getText() != null && passwordText.getText() != null)
    		{
    			ClinicDB.openConnection();
				Iterator <Doctor> doctors = dc.getAll();
				boolean found = false;	
				while(doctors.hasNext() && !found)
				{	
					Doctor currDoc = doctors.next();
					if(currDoc.getUsername().equals(usernameText.getText()) && currDoc.getPassword().equals(passwordText.getText())) {
						dmc.setMainDoctor(currDoc);
						Alert alert = new Alert(AlertType.INFORMATION);
		       			alert.setTitle("SUCCESSFUL LOGIN");
		       			alert.setHeaderText(null);
		       			alert.setContentText("Welcome " + currDoc.getName().toString() + "!");
		       			alert.showAndWait();
		       			
		       			dmc.setAgendaVisible();
		       			dmc.setMainDoctor(currDoc);
		       			
		       			found = true;
					}
    			}
				if (!found)
				{
					Alert alert = new Alert(AlertType.ERROR);
		   			alert.setTitle("TRY AGAIN");
		   			alert.setHeaderText(null);
		   			alert.setContentText("Invalid username and password.");
		   			alert.showAndWait();
				}
				ClinicDB.closeConnection();
    			
    		}
    		else if (usernameText.getText() == null)
    		{
    			Alert alert = new Alert(AlertType.ERROR);
	   			alert.setTitle("FILL UP ALL FIELDS");
	   			alert.setHeaderText(null);
	   			alert.setContentText("Please enter a username.");
	   			alert.showAndWait();
    		}
    		else
    		{
    			Alert alert = new Alert(AlertType.ERROR);
	   			alert.setTitle("FILL UP ALL FIELDS");
	   			alert.setHeaderText(null);
	   			alert.setContentText("Please enter pasword.");
	   			alert.showAndWait();
    		}
    		clearFields();
    	});
    	
    	bckBtn.setOnAction(event -> {
    		dmc.setStartupVisible();
    		clearFields();
    	});
    }
    
    public void clearFields () {
    	usernameText.clear();
    	passwordText.clear();
    }

}
