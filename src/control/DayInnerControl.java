package control;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Popup;
import model.calendar.Appointment;
import model.calendar.Details;
import model.calendar.Event;
import model.calendar.EventDetails;
import model.storage.EventCollection;

public class DayInnerControl extends AnchorPane {

	@FXML private AnchorPane schedule;
	private List <CellControl> cells;
	
	@FXML 
	public void initialize () {
		cells = new ArrayList <CellControl> ();
	}
	
	public void initializeButtons (EventCollection events) {
		if (cells != null) {
			for (CellControl cell: cells) {
				cell.initializeButtons(events);
			}
		}
	}
	
	public void setDataEvents (Iterator <Event> itr) {
		if (itr != null) {
			schedule.getChildren().clear();
				
			List <Event> events = new ArrayList<Event>();
			while(itr.hasNext()) {
				events.add(itr.next());
			}
			
			for (int i = 0; i < events.size(); i++) {
				cells.add(new CellControl());
				cells.get(i).setUserData(events.get(i));
				schedule.getChildren().add(cells.get(i));
		
				cells.get(i).setLayoutX(0.0);			
		
				Details details = cells.get(i).getUserData().getDetails();
				
				float start = 0;
				
				start = details.getTimeStart().getHour()*60 + details.getTimeStart().getMinute();
		
				cells.get(i).setLayoutY(start);
			}
		}
	}
}

class CellControl extends AnchorPane {
	private Label text;
	private Event event;
	private Popup popup;
	private PopupControl popupControl;
	
	public final static double DEF_WIDTH = 400;
	public final static double DEF_HEIGHT = 60;
	
	public CellControl () {
		super();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/PopupPane.fxml"));
		popup = new Popup();
		
		try {
			popup.getContent().add(loader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		popupControl = loader.getController();
		
		text = new Label();
		text.setAlignment(Pos.CENTER);
		text.setWrapText(true);
		text.setFont(Font.font(null, FontWeight.BOLD, 14));
		
		getChildren().add(text);
		setMouseListener();
	}
	
	public void setUserData (Event event){                                                                  
	
		if (event != null) {
			this.event = event;
			setStyle("-fx-background-color: #" + event.getDetails().getColor().toString().substring(2));
			Color color = event.getDetails().getColor();
			color.invert();
			text.setTextFill(color.invert());
			
			LocalTime timeStart = event.getDetails().getTimeStart();
			LocalTime timeEnd = null;
			
			String message = "";
			if (event.getDetails() instanceof EventDetails) {
				timeEnd = ((EventDetails)event.getDetails()).getTimeEnd();
				message = "EVENT: ";
			} else {
				timeEnd = timeStart.plusMinutes(30);
				message = "TASK: ";
			} 
			
			if(event instanceof Appointment) {
				message = "APPOINTMENT: ";
			}
			
			double height = (timeEnd.toSecondOfDay() - timeStart.toSecondOfDay())/DEF_HEIGHT;
			double width = DEF_WIDTH;

			text.setText(message + " " + event.getTitle());	
			
			
			
			setSize(height, width);
			popupControl.setUserData(event);
			popup.setAutoHide(true);
		}
	}
	
	public void initializeButtons (EventCollection events) {
		popupControl.setDeleteButton(events);
		popupControl.setDoneBox(events);
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
} 