package control;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.CheckBox;
import javafx.scene.shape.Rectangle;
import model.calendar.Event;
import model.calendar.EventDetails;
import model.calendar.Status;
import model.calendar.TaskDetails;
import model.storage.ClinicDB;
import model.storage.EventCollection;
import javafx.scene.shape.Polygon;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class PopupControl extends AnchorPane {
	@FXML private Label nameLabel;
	@FXML private Label timeLabel;
	@FXML private CheckBox doneBox;
	@FXML private Rectangle rectangle;
	@FXML private Polygon triangle;
	@FXML private Button deleteBtn;
	
	private Event event;
	
	public void setDeleteButton (EventCollection collections) {
		deleteBtn.setOnAction(e -> {
			ClinicDB.openConnection();
			Alert alert = new Alert (AlertType.CONFIRMATION);
			alert.setTitle("Delete Confirmation");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to delete this?");
    		
    		Optional <ButtonType> result = alert.showAndWait();
    		
    		if (result.get() == ButtonType.OK){
    			boolean removed = collections.delete(event);
    			if(removed) {
    				alert = new Alert (AlertType.INFORMATION);
    				alert.setTitle("Delete Successful");
    				alert.setHeaderText(null);
    				alert.setContentText("Successfully deleted: " + event.getTitle());
    				alert.showAndWait();
    			} else {
    				alert = new Alert (AlertType.ERROR);
    				alert.setTitle("Database Error");
    				alert.setHeaderText(null);
    				alert.setContentText("There was an error in the database connection!");
    				alert.showAndWait();
    			}
    		} 

			ClinicDB.closeConnection();
		});
	}
	
	public void setDoneBox (EventCollection collections) {
		doneBox.setOnAction(e -> {
			ClinicDB.openConnection();
			Alert alert = new Alert (AlertType.CONFIRMATION);
			alert.setTitle("Mark as Done Confirmation");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to mark this as done?");

			Optional <ButtonType> result = alert.showAndWait();
			
    		if (result.get() == ButtonType.OK){
    			if(event.getDetails() instanceof TaskDetails) {
    				((TaskDetails) event.getDetails()).setStatus(Status.DONE);
					boolean removed = collections.update(event);
					if(removed) {
						alert = new Alert (AlertType.INFORMATION);
						alert.setTitle("Marked as Done");
						alert.setHeaderText(null);
						alert.setContentText("Successfully marked as done: " + event.getTitle());
						alert.showAndWait();
					} else {
						alert = new Alert (AlertType.ERROR);
						alert.setTitle("Database Error");
						alert.setHeaderText(null);
						alert.setContentText("There was an error in the database connection!");
						alert.showAndWait();
					}
    			} 
    		} 
    		
			
			
			ClinicDB.closeConnection();
		});
	}
	public void setUserData (Event event) {
		this.event = event;
		nameLabel.setText(event.getTitle());
		
		if (event.getDetails() instanceof EventDetails) {
			timeLabel.setText(event.getDetails().getTimeStart().toString() + " - " 
								+ ((EventDetails)event.getDetails()).getTimeEnd().toString());
			doneBox.setVisible(false);
		} else {
			timeLabel.setText(event.getDetails().getTimeStart().toString());
			doneBox.setVisible(true);
			
			if (((TaskDetails)event.getDetails()).getStatus() == Status.DONE) {
				doneBox.setSelected(true);
				doneBox.setDisable(true);
			}
		}	
		
		rectangle.setFill(event.getDetails().getColor());
		triangle.setFill(event.getDetails().getColor());
		timeLabel.setTextFill(event.getDetails().getColor().invert());
		nameLabel.setTextFill(event.getDetails().getColor().invert());
	}
}
