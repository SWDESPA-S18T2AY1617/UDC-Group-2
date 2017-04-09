package control;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.Appointment;
import model.EventDetails;

public class AppointmentControl {

	@FXML private Button cancelButton;
	@FXML private Button reserveButton;
	@FXML private ListView <Appointment> appointList;

	@FXML 
	public void initialize () {
		appointList.setCellFactory(new Callback<ListView<Appointment>, ListCell<Appointment>>() {
			
			@Override
			public ListCell<Appointment> call(ListView<Appointment> arg0) {
				return new ListCell<Appointment>() {
					 @Override 
					 protected void updateItem(Appointment item, boolean empty) {
						 super.updateItem(item, empty);
					    	if (item == null || empty) {
					            setText(null);
					            setGraphic(null);
					        } else {
					        	
					        	String time = "" +  LocalDate.of(item.getDetails().getYear().getValue(), 
					        			item.getDetails().getMonth(),
					        			item.getDetails().getDayOfMonth()).getDayOfWeek().toString();
					        	
					        	time += " " + item.getDetails().getTimeStart() +"  -  " +((EventDetails)item.getDetails()).getTimeEnd() + " "; 
					    	    
					        	Text txtTime = new Text(time);
					        	String message = "";
					        	if(item.getClient() != null) {
					        		message = item.getDoctor().getName().getLast() + " Client: " + item.getClient().getName() + " ";
					        	} else {
					        		message = item.getDoctor().getName().toString() + " ";
					        	}
					        	
					        	Text txtDoctor = new Text (message);
					            Text txtTitle = new Text(item.getTitle() + " ");
					            Color color = item.getDetails().getColor();
					            
					            txtTime.setFill(color);
					            txtDoctor.setFill(color);
					            txtTitle.setFill(color);

					            txtTime.setFont(Font.font(null, FontWeight.BOLD, 16));
					            txtDoctor.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
					            txtTitle.setFont(Font.font(null, FontWeight.NORMAL, 16));
					            
					            HBox hbox = new HBox(txtTime, txtTitle, txtDoctor);
					            setGraphic(hbox);
					        }
					 }
				};
			}
		});
	}
	
	public void setPlaceholder (String text) {
		Label label = new Label(text);
		label.setFont(Font.font(null, FontWeight.BOLD, 24));
		appointList.setPlaceholder(label);
	}
	
    public void setEvents (Iterator <Appointment> events) {
		ObservableList <Appointment> items = FXCollections.observableArrayList();
		
		if (events != null) {		
			while(events.hasNext()){ 
				Appointment a = events.next();
					items.add(a);
			}
			
			items.sort(new Comparator <Appointment> () {
				@Override
				public int compare(Appointment o1, Appointment o2) {
					return o1.getDetails().getDayWeek().compareTo(o2.getDetails().getDayWeek()) + o1.getDetails().getTimeStart().compareTo(o2.getDetails().getTimeStart());
				}
			});
		}
		
		items = FXCollections.observableArrayList(items);
		appointList.setItems(FXCollections.observableArrayList());
		appointList.setItems(items);
    }
	
	public void cancelButtonVisibility (boolean value) {
		cancelButton.setVisible(value);
	}
	
	public void removeButtonVisibility (boolean value) {
		reserveButton.setVisible(value);
	}

	public void setReserveButton(EventHandler <ActionEvent> event) {
		reserveButton.setOnAction(event);
	}
	
	public void setCancelButton(EventHandler <ActionEvent> event) {
		cancelButton.setOnAction(event);
	}
	
	public Appointment getListInput () {
		return appointList.getSelectionModel().getSelectedItem();
	}
}
