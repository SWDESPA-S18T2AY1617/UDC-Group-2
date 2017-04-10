package control.doctor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import model.Appointment;
import model.Doctor;
import model.EventDetails;
import model.storage.AppointmentCollection;
import model.storage.ClinicDB;
import model.storage.DoctorCollection;
import model.storage.EventCollection;

public class DoctorAddControl {

    @FXML private CheckBox wCheck;
    @FXML private CheckBox rptCheck;
    @FXML private DatePicker endDate;
    @FXML private CheckBox tCheck;
    @FXML private CheckBox fCheck;
    @FXML private Button addBtn;
    @FXML private ComboBox<LocalTime> timeStartCmbBox;
    @FXML private CheckBox hCheck;
    @FXML private CheckBox mCheck;
    @FXML private ComboBox<LocalTime> timeEndCmbBox;
    @FXML private DatePicker startDate;
	@FXML Button back;
    
	@FXML
	public void initialize () {
		rptCheck.setOnAction(event -> {
			wCheck.setDisable(!rptCheck.isSelected());
			tCheck.setDisable(!rptCheck.isSelected());
			fCheck.setDisable(!rptCheck.isSelected());
			hCheck.setDisable(!rptCheck.isSelected());
			mCheck.setDisable(!rptCheck.isSelected());
			endDate.setDisable(!rptCheck.isSelected());
		});
		
		timeStartCmbBox.getItems().clear();
        for (int i = 0; i < 24; i ++) {
        	timeStartCmbBox.getItems().add(LocalTime.of(i, 0));
        	timeStartCmbBox.getItems().add(LocalTime.of(i, 30));
        }
        
        timeStartCmbBox.getItems().remove(LocalTime.of(23, 30));
        timeStartCmbBox.setOnAction(handler -> {
        	timeEndCmbBox.getItems().clear();
        	for (int i = timeStartCmbBox.getSelectionModel().getSelectedIndex() + 1; i < timeStartCmbBox.getItems().size(); i++) {
        		timeEndCmbBox.getItems().add(timeStartCmbBox.getItems().get(i));
        	}
        	
        	timeEndCmbBox.getItems().add(LocalTime.of(23, 30));
        });
        
        for (int i = 1; i < timeStartCmbBox.getItems().size(); i++) {
    		timeEndCmbBox.getItems().add(timeStartCmbBox.getItems().get(i));
    	}
        
        timeEndCmbBox.getItems().add(LocalTime.of(23, 30));

	}
	
	public void addAppointment(Doctor doc, EventCollection events, AppointmentCollection appointments, DoctorCollection doctors) {
    	
    	addBtn.setOnAction(event -> {
    		EventDetails details = new EventDetails();
    		LocalDate date = startDate.getValue();
    		
    		details.setTimeStart(timeStartCmbBox.getSelectionModel().getSelectedItem());
    		details.setTimeEnd(timeEndCmbBox.getSelectionModel().getSelectedItem());
    		details.setDayOfMonth(date.getDayOfMonth());
    		details.setMonth(date.getMonth());
    		details.setYear(Year.of(date.getYear()));
    		details.setColor(doc.getColor());
    		
    		Appointment app = new Appointment();
    		app.setClient(null);
    		app.setDoctor(doc);
    		app.setDetails(details);
    		
    		ClinicDB.openConnection();
    		app.setId(events.lastUpdatedID() + 1);
    		ClinicDB.closeConnection();
    		
    		app.setTitle("FREE");
    		ClinicDB.openConnection();
    		if(!events.isOverlapping(date, app.getDetails().getTimeStart(), ((EventDetails) app.getDetails()).getTimeEnd())) {
    			ClinicDB.closeConnection();
    			
    			ClinicDB.openConnection();
    			if(events.add(app.getEvent())) {
            		Alert alert = new Alert (AlertType.INFORMATION);
        			alert.setTitle("Free Slot/s Created!");
        			alert.setHeaderText(null);
        			alert.setContentText("Created Free Slot/s for " + date + " ");
        			alert.showAndWait();
            		ClinicDB.closeConnection();
            		
            		ClinicDB.openConnection();
		        		if(appointments.add(app)) {
		            		alert = new Alert (AlertType.INFORMATION);
		        			alert.setTitle("Free Slot/s Created!");
		        			alert.setHeaderText(null);
		        			alert.setContentText("Created Free Slot/s for " + date);
		        			alert.showAndWait();
		        		} else {
		            		alert = new Alert (AlertType.ERROR);
		        			alert.setTitle("DB Error!");
		        			alert.setHeaderText(null);
		        			alert.setContentText("Failed to created Free Slot/s for " + date);
		        			alert.showAndWait();
		        		}
            		ClinicDB.closeConnection();
            		
        		} else {
            		Alert alert = new Alert (AlertType.ERROR);
        			alert.setTitle("DB Error!");
        			alert.setHeaderText(null);
        			alert.setContentText("Failed to created Free Slot/s for " + date);
        			alert.showAndWait();
        		}

    		} else {
    			ClinicDB.closeConnection();
    			Alert alert = new Alert (AlertType.ERROR);
    			alert.setTitle("Overlapping Appointments!");
    			alert.setHeaderText(null);
    			alert.setContentText("Cannot create schedule due to overlapping appointments!");
    			alert.showAndWait();
    		}
    		
    		
    		if (rptCheck.isSelected()) {
    			LocalDate dateEnd = endDate.getValue();
    			
    			while (date.isBefore(dateEnd)) {
    				date = date.plusDays(1);
    				int dateDay = date.getDayOfWeek().getValue();
    				if (mCheck.isSelected() && dateDay == 1 ||
    					tCheck.isSelected() && dateDay == 2 ||
    					wCheck.isSelected() && dateDay == 3 ||
    					hCheck.isSelected() && dateDay == 4 ||
    					fCheck.isSelected() && dateDay == 5) {
    					
    					details = new EventDetails();
    		    		
    		    		details.setTimeStart(timeStartCmbBox.getSelectionModel().getSelectedItem());
    		    		details.setTimeEnd(timeEndCmbBox.getSelectionModel().getSelectedItem());
    		    		details.setDayOfMonth(date.getDayOfMonth());
    		    		details.setMonth(date.getMonth());
    		    		details.setYear(Year.of(date.getYear()));
    		    		details.setColor(doc.getColor());
    		    		
    		    		app = new Appointment();
    		    		app.setClient(null);
    		    		app.setDoctor(doc);
    		    		app.setDetails(details);
    		    		
    		    		ClinicDB.openConnection();
    		    		app.setId(events.lastUpdatedID() + 1);
    		    		ClinicDB.closeConnection();
    		    		
    		    		app.setTitle("FREE");
    		    		ClinicDB.openConnection();
    		    		if(!events.isOverlapping(date, app.getDetails().getTimeStart(), ((EventDetails) app.getDetails()).getTimeEnd())) {
    		    			ClinicDB.closeConnection();
    		    			
    		    			ClinicDB.openConnection();
    		    			if(events.add(app.getEvent())) {
    		            		Alert alert = new Alert (AlertType.INFORMATION);
    		        			alert.setTitle("Free Slot/s Created!");
    		        			alert.setHeaderText(null);
    		        			alert.setContentText("Created Free Slot/s for " + date + " ");
    		        			alert.showAndWait();
    		            		ClinicDB.closeConnection();
    		            		
    		            		ClinicDB.openConnection();
    				        		if(appointments.add(app)) {
    				            		alert = new Alert (AlertType.INFORMATION);
    				        			alert.setTitle("Free Slot/s Created!");
    				        			alert.setHeaderText(null);
    				        			alert.setContentText("Created Free Slot/s for " + date);
    				        			alert.showAndWait();
    				        		} else {
    				            		alert = new Alert (AlertType.ERROR);
    				        			alert.setTitle("DB Error!");
    				        			alert.setHeaderText(null);
    				        			alert.setContentText("Failed to created Free Slot/s for " + date);
    				        			alert.showAndWait();
    				        		}
    		            		ClinicDB.closeConnection();
    		            		
    		        		} else {
    		            		Alert alert = new Alert (AlertType.ERROR);
    		        			alert.setTitle("DB Error!");
    		        			alert.setHeaderText(null);
    		        			alert.setContentText("Failed to created Free Slot/s for " + date);
    		        			alert.showAndWait();
    		        		}

    		    		} else {
    		    			ClinicDB.closeConnection();
    		    			Alert alert = new Alert (AlertType.ERROR);
    		    			alert.setTitle("Overlapping Appointments!");
    		    			alert.setHeaderText(null);
    		    			alert.setContentText("Cannot create schedule due to overlapping appointments!");
    		    			alert.showAndWait();
    		    		}
    				}
    			}
    		}
    	});
    }
	public void setBackButton(EventHandler <ActionEvent> event) {
		back.setOnAction(event);
	}
}
