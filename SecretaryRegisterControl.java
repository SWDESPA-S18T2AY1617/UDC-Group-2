package control.secretary;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.calendar.Secretary;
import model.calendar.Name;
import model.storage.ClinicDB;
import model.storage.SecretaryCollection;

public class SecretaryRegisterControl {

	@FXML private TextField firstNameText;
	@FXML private TextField middleNameText;
    @FXML private TextField lastNameText;
    @FXML private TextField usernameText;
    @FXML private PasswordField passwordText;
    
    @FXML private Button registerButton;
    @FXML private Button backButton;
    
    @FXML
    public void initialize() {
    	clearFields();
    }
    
    public void initializeButtons(SecretaryMainControl secMainController, SecretaryCollection secCollections) {
    	registerButton.setOnAction(event -> {
    		if(firstNameText.getText() == null || lastNameText.getText() == null || middleNameText.getText() == null
	    	   || usernameText.getText() == null || passwordText.getText() == null) {
    			Alert alert = new Alert(AlertType.ERROR);
	   			alert.setTitle("TRY AGAIN");
	   			alert.setHeaderText(null);
	   			alert.setContentText("Complete required fields.");
	   			alert.showAndWait();
    		}
    		
    		else {
    			Secretary s = new Secretary(); 
    			Name n = new Name(); 
    			
    			n.setFirst(firstNameText.getText());
	    		n.setMiddle(middleNameText.getText());
	    		n.setLast(lastNameText.getText());
	    		
	    		s.setName(n);
	    		s.setUsername(usernameText.getText());
	    		s.setPassword(passwordText.getText());
	    		
	    		ClinicDB.openConnection();
	    		
	    		if(secCollections.add(s))
	    			System.out.println("Added");
	    		
	    		ClinicDB.closeConnection();
    		}
    		
    		secMainController.setDayVisible();
    	});
    	
    	backButton.setOnAction(event -> {
    		secMainController.setStartupVisible();
    		clearFields();
    	});
    }
    
    public void clearFields () {
    	firstNameText.clear();
    	middleNameText.clear();
    	lastNameText.clear();
    	usernameText.clear();
    	passwordText.clear();
    }
}