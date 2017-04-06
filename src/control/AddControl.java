package control;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import model.calendar.Details;
import model.calendar.Event;
import model.calendar.EventDetails;
import model.calendar.Status;
import model.calendar.TaskDetails;
import model.storage.ClinicDB;
import model.storage.EventCollection;

public class AddControl {
	@FXML private ComboBox <LocalTime> timeStartCmbBox;
	@FXML private ComboBox <LocalTime> timeEndCmbBox;
	@FXML private Button saveBtn;
	@FXML private TextField titleTxtField;
	@FXML private DatePicker datePick;
	@FXML private ColorPicker colorInput;
	@FXML private AnchorPane root;
	@FXML private Label toLabel;
	@FXML private Label addLabel;
	
    @FXML
    public void initialize() {
        assert colorInput != null : "fx:id=\"colorInput\" was not injected: check your FXML file 'AddPane.fxml'.";
        assert timeStartCmbBox != null : "fx:id=\"timeStartCmbBox\" was not injected: check your FXML file 'AddPane.fxml'.";
        assert titleTxtField != null : "fx:id=\"titleTxtField\" was not injected: check your FXML file 'AddPane.fxml'.";
        assert datePick != null : "fx:id=\"datePick\" was not injected: check your FXML file 'AddPane.fxml'.";
        assert toLabel != null : "fx:id=\"label\" was not injected: check your FXML file 'AddPane.fxml'.";
        assert timeEndCmbBox != null : "fx:id=\"timeEndCmbBox\" was not injected: check your FXML file 'AddPane.fxml'.";
        assert saveBtn != null : "fx:id=\"saveBtn\" was not injected: check your FXML file 'AddPane.fxml'.";
        assert addLabel != null : "fx:id=\"addLabel\" was not injected: check your FXML file 'AddPane.fxml'.";
        
        ObservableList<LocalTime> timeStart = FXCollections.observableArrayList();
        ObservableList<LocalTime> timeEnd = FXCollections.observableArrayList();
        
        for (int i = 0; i < 24; i ++) {
        	timeStart.add(LocalTime.of(i, 0));
        	timeStart.add(LocalTime.of(i, 30));
    	}
        
        timeStartCmbBox.setItems(timeStart);
        
        timeStartCmbBox.setOnAction(handler -> {
        	timeEnd.clear();
        	for (int i = timeStartCmbBox.getSelectionModel().getSelectedIndex() + 1; i < 48; i++) {
            	timeEnd.add(timeStart.get(i));
        	}
        });
        
    	for (int i = 1; i < 48; i++) {
        	timeEnd.add(timeStart.get(i));
    	}
    
        timeEndCmbBox.setItems(timeEnd);
        
        clearFields();
    }
   
    private void setAddBtnAction (EventCollection collections) {
    	saveBtn.setOnAction(ex -> {
    		ClinicDB.openConnection();
    		
        	String title = titleTxtField.getText();
        	LocalDate date = datePick.getValue();
        	Color color = colorInput.getValue();
        	LocalTime timeStart = timeStartCmbBox.getValue();
        	LocalTime timeEnd = timeEndCmbBox.getValue();
        	
        	String message = "";
        	
        	Event event = new Event ();
        	Details details;
        	
        	if (timeEndCmbBox.isVisible()) {
        		details = new EventDetails ();
        		((EventDetails)details).setTimeEnd(timeEnd);
        		message = "an event";
        	} else {
        		details = new TaskDetails ();
        		((TaskDetails)details).setStatus(Status.PENDING);
        		message = "a task";
        	}
        	
        	details.setColor(color);
        	details.setDayOfMonth(date.getDayOfMonth());
        	details.setYear(Year.of(date.getYear()));
        	details.setMonth(date.getMonth());
        	details.setTimeStart(timeStart);
        	
        	event.setDetails(details);
        	event.setTitle(title);
        	
        	event.setId(collections.lastUpdatedID() + 1);
        	ClinicDB.closeConnection();
        	
        	ClinicDB.openConnection();
        	boolean added = collections.add(event);
        	
        	if (added) {
        		Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Succesful Insert");
				alert.setHeaderText(null);
				alert.setContentText("You've succesfully added " + message + "!");
        		alert.showAndWait();
        	} else {
        		Alert alert = new Alert (AlertType.ERROR);
				alert.setTitle("Database Error");
				alert.setHeaderText(null);
				alert.setContentText("There was an error in the database connection!");
        		alert.showAndWait();
        	}
        	
    		clearFields();
        	ClinicDB.closeConnection();
        }); 
    }
    
    public void isStatus (boolean value) {
    	toLabel.setVisible(value);
		timeEndCmbBox.setVisible(value);
		if(value)
			addLabel.setText("ADD EVENT");
		else
			addLabel.setText("ADD TASK");
	}

	public void initializeButtons(EventCollection events) {
		setAddBtnAction(events);
	}
	
	public void clearFields () {
		if(timeStartCmbBox.getItems().size() != 0)
			timeStartCmbBox.getSelectionModel().select(0);
		if(timeEndCmbBox.getItems().size() != 0)
			timeEndCmbBox.getSelectionModel().select(0);
		datePick.setValue(LocalDate.now());
		titleTxtField.clear();
		colorInput.setValue(Color.WHITE);
	}
}
