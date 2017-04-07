package control.doctor;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert.AlertType;
import model.calendar.Doctor;
import model.calendar.Name;
import model.storage.ClinicDB;
import model.storage.DoctorCollection;

public class DoctorRegisterControl {

    @FXML private PasswordField passwordText;
    @FXML private TextField lastNameText;
    @FXML private TextField descriptionText;
    @FXML private TextField firstNameText;
//    @FXML private AnchorPane clientRegisterPane;
    @FXML private Button registerButton;
    @FXML private TextField usernameText;
    @FXML private ColorPicker colorPicker;
    @FXML private TextField middleNameText;
    @FXML private Button bckBtn;
    
    @FXML
    void initialize () {
    	clearFields();
    }
    
    public void initializeButtons (DoctorMainControl dmc, DoctorCollection dc)
    {
    	registerButton.setOnAction(event -> 
    	{
	    	if(firstNameText.getText() == null || lastNameText.getText() == null || middleNameText.getText() == null
	    			|| usernameText.getText() == null || passwordText.getText() == null)
	    	{
	    		Alert alert = new Alert(AlertType.ERROR);
	   			alert.setTitle("TRY AGAIN");
	   			alert.setHeaderText(null);
	   			alert.setContentText("Complete required fields.");
	   			alert.showAndWait();
	    	}
	    	else {
	    		Doctor d = new Doctor();
	    		Name n = new Name();
	    		n.setFirst(firstNameText.getText());
	    		n.setMiddle(middleNameText.getText());
	    		n.setLast(lastNameText.getText());
	    		d.setName(n);
	    		d.setUsername(usernameText.getText());
	    		d.setPassword(passwordText.getText());
	    		if(descriptionText.getText() != null)
	    			d.setDescription(descriptionText.getText());
	    		else d.setDescription("");
	    		d.setColor(colorPicker.getValue());
	    		
	    		ClinicDB.openConnection();
	    		if(dc.add(d))
	    			System.out.println("Added");
	    		ClinicDB.closeConnection();
	    	}
	    	dmc.setDayVisible();
    	});
    	
    	bckBtn.setOnAction(event -> {
    		dmc.setStartupVisible();
    		clearFields();
    	});
    }
    
    public void clearFields () {
    	passwordText.clear();
    	firstNameText.clear();
    	middleNameText.clear();
    	lastNameText.clear();
    	usernameText.clear();
    	colorPicker.setValue(Color.WHITE);
    	descriptionText.clear();
    	
    }

}
