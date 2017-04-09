package control;

import java.time.LocalDate;
import java.time.Year;
import java.util.Calendar;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.AppointmentObserver;
import model.Doctor;
import model.ModelGregorianCalendar;
import javafx.scene.control.ToggleButton;

public class ToolbarControl {
	@FXML private Button todayButton;
	@FXML private Button nextButton;
	@FXML private Button prevButton;
	@FXML private Button dayButton;
	@FXML private Button weekButton;
	@FXML private Button logoutButton;
	@FXML private Button agendaDay;
	@FXML private Button agendaWeek;
	@FXML private ListView <Doctor> doctorsCombobox;
	@FXML private ComboBox <Year> yearCombobox;
	@FXML private Label monthLabel;
	@FXML private Label viewLabel;
	@FXML private ToggleButton reservationButton;
	
	@FXML private AnchorPane calendar;
	@FXML private AnchorPane toolBar;	
	@FXML private CalendarControl calendarController;
	
	private ObservableList <Doctor> doctors;
	private AppointmentObserver parent;
	

	@FXML
	public void initialize () {
		doctors = FXCollections.observableArrayList();
		
		doctorsCombobox.setCellFactory(new Callback<ListView<Doctor>, ListCell<Doctor>>() {
			
			@Override
			public ListCell<Doctor> call(ListView<Doctor> arg0) {
				return new ListCell<Doctor>() {
					 @Override 
					 protected void updateItem(Doctor item, boolean empty) {
						 super.updateItem(item, empty);
					    	if (item == null || empty) {
					            setText(null);
					            setGraphic(null);
					        } else {
					        	CheckBox box = new CheckBox ();
					        	Text txtDoctor = new Text (item.toString());
					            Color color = item.getColor();
					            
					            txtDoctor.setFill(color);

					            txtDoctor.setFont(Font.font(null, FontWeight.BOLD, 12));
					            
					            box.setOnAction(event -> {
					            	if(box.isSelected()) {
					            		doctors.add(item);
					            	} else {
					            		doctors.remove(item);
					            	}
					            });
					            
					            HBox hbox = new HBox(box, txtDoctor);
					            setGraphic(hbox);
					        }
					 }
				};
			}
		});
		
		calendarController.setParent(this);
	}
	
	public ObservableList <Doctor> getDoctorsInput () {
		return doctors;
	}
	public void setParent (AppointmentObserver parent) {
		this.parent = parent;
	}
	
	public void setCalendar (ModelGregorianCalendar mgc) {
		calendarController.setState(mgc);
		
		yearCombobox.setItems(FXCollections.observableArrayList());
		
		for (int i = 1930; i < LocalDate.now().getYear(); i++) 
			yearCombobox.getItems().add(Year.of(i));
		
		nextButton.setOnAction(event -> {
			mgc.set(Calendar.MONTH, mgc.get(Calendar.MONTH) + 1);
			mgc.set(Calendar.DAY_OF_MONTH,  1);
		});
   
		prevButton.setOnAction(event -> {
			mgc.set(Calendar.MONTH, mgc.get(Calendar.MONTH) - 1);
			mgc.set(Calendar.DAY_OF_MONTH,  1);
		});
   
		todayButton.setOnAction(event -> {
			mgc.jumpToCurrentDate();
		});
		
		yearCombobox.setOnAction(event -> {
			mgc.set(Calendar.YEAR, yearCombobox.getSelectionModel().getSelectedItem().getValue());
		});
	}
	
	public void selectYear (Year year) {
		yearCombobox.getSelectionModel().select(year);
	}
	
	public void setMonthLabel (String text) {
		monthLabel.setText(text);
	}

	public void setLogoutAction(EventHandler <ActionEvent> event) {
		logoutButton.setOnAction(event);
	}
	
	public void setDayAction (EventHandler<ActionEvent> event) {
		dayButton.setOnAction(event);
	}
	
	public void setWeekAction (EventHandler<ActionEvent> event) {
		weekButton.setOnAction(event);
	}
	
	public void setReservationAction (EventHandler <ActionEvent> event) {
		reservationButton.setOnAction(event);
	}
	
	public boolean reservationState () {
		return reservationButton.isSelected();
	}
	
	public void setDoctors (Iterator <Doctor> doctors) {
		if(doctors == null) {
			doctorsCombobox.setDisable(true);
		} else {
			doctorsCombobox.setItems(FXCollections.observableArrayList());
			
			while (doctors.hasNext()) {
				doctorsCombobox.getItems().add(doctors.next());
			}
			doctorsCombobox.setDisable(false);
		}
	}

	public void update() {
		parent.update();
	}
}