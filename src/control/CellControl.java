package control;

import java.io.IOException;
import java.time.LocalTime;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Popup;
import model.calendar.Appointment;
import model.calendar.Client;
import model.calendar.Event;
import model.calendar.EventDetails;
import model.storage.AppointmentCollection;
import model.storage.EventCollection;

class CellControl extends AnchorPane {
	private Label text;
	private Event event;
	private Popup popup;
	private PopupControl popupControl;
	
	public double DEF_WIDTH;
	public final static double DEF_HEIGHT = 60;
	
	public CellControl (double width, Appointment event) {
		super();
		FXMLLoader loader = new FXMLLoader();
		popup = new Popup();
		text = new Label();
		
		text.setAlignment(Pos.CENTER);
		text.setWrapText(true);
		text.setFont(Font.font(null, FontWeight.NORMAL, 10));
		
		
		if(event.getClient() != null) {
			loader.setLocation(getClass().getResource("/view/PopupAppointmentPane.fxml"));
			try {
				popup.getContent().add(loader.load());
			} catch (IOException e) {
				e.printStackTrace();
			}
			PopupAppointmentControl control = loader.getController();
			control.setUserData(event);
			this.popupControl = control;
		} else {
			loader.setLocation(getClass().getResource("/view/PopupFreeSlotPane.fxml"));	
			try {
				popup.getContent().add(loader.load());
			} catch (IOException e) {
				e.printStackTrace();
			}
			PopupFreeSlotControl control = loader.getController();
			control.setUserData(event);
			this.popupControl = control;
		}
		
		
		DEF_WIDTH = width;
		
		
		
		getChildren().add(text);
		setMouseListener();
		setUserData(event);
	}
	
	private void setUserData (Appointment event){                                                                  
	
		if (event != null) {
			this.event = event;
			setStyle("-fx-background-color: #" + event.getDetails().getColor().toString().substring(2));
			Color color = event.getDetails().getColor();
			color.invert();
			text.setTextFill(color.invert());
			
			LocalTime timeStart = event.getDetails().getTimeStart();
			LocalTime timeEnd = null;
			
			timeEnd = ((EventDetails)event.getDetails()).getTimeEnd();
			
			String message = event.getTitle();
			
			double height = (timeEnd.toSecondOfDay() - timeStart.toSecondOfDay())/DEF_HEIGHT;
			double width = DEF_WIDTH;

			text.setText(message);	
			
			setSize(height, width);
			popup.setAutoHide(true);
		}
	}
	
	public void setSize (double height, double width) {
		setPrefSize(width, height);
		setMaxSize(width, height);
		setMinSize(width, height);

		text.setMaxSize(width, height);
		text.setMinSize(width, height);
		text.setPrefSize(width, height);
	}
	
	public Event getUserData () {
		return event;
	}
	
	public void setMouseListener () {
		setOnMouseClicked(handler -> {
			if (popup.isShowing())
				popup.hide();
			else
				popup.show(this, handler.getScreenX() - popup.getWidth()/2, handler.getScreenY());
		});
	}

	public void initializeButtons(AppointmentCollection appointments, EventCollection events, Client client) {
		popupControl.initializeButtons(appointments, events, client);
	}
} 
