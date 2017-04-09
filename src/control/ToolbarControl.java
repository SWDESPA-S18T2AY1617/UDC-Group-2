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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
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

public class ToolbarControl {
	@FXML private Button todayButton;
	@FXML private Button nextButton;
	@FXML private Button prevButton;
	@FXML private Button logoutButton;
	
	@FXML private ListView <Doctor> doctorsCombobox;
	@FXML private ComboBox <Year> yearCombobox;

	@FXML private Label monthLabel;
	@FXML private Label viewLabel;
	@FXML private ToggleButton reservationButton;
	
	@FXML private AnchorPane calendar;
	@FXML private AnchorPane toolBar;	
	@FXML private CalendarControl calendarController;
	
	@FXML private RadioButton week;
	@FXML private RadioButton day;
	@FXML private RadioButton agenda;
	@FXML private RadioButton calendarX;
	@FXML private ToggleButton reservation;
	
	private ToggleGroup weekOrDay;
	private ToggleGroup agendaOrCalendar;
	
	private ObservableList <Doctor> doctors;
	private AppointmentObserver parent;

	public void setSecretaryViews () {
		
	}
	
	public void setClientViews () {
		
	}
	
	public void setWeekOrDay (AnchorPane day, AnchorPane week, AnchorPane agenda) {
		weekOrDay.selectedToggleProperty().addListener((observed, oldT, newT) -> {
			if(!agendaOrCalendar.getSelectedToggle().equals(this.agenda)) {
				if (weekOrDay.getSelectedToggle().equals(this.day)) {
					day.setVisible(true);
					week.setVisible(false);
					agenda.setVisible(false);
				} else {
					day.setVisible(false);
					week.setVisible(true);
					agenda.setVisible(false);
				}
			} else {
				day.setVisible(false);
				week.setVisible(false);
				agenda.setVisible(true);
			}
			parent.update();
		});
	}
	
	public boolean isDay () {
		return weekOrDay.getSelectedToggle().equals(day);
	}
	
	public void setAgendaOrCalendar (AnchorPane day, AnchorPane week, AnchorPane agenda) {
		agendaOrCalendar.selectedToggleProperty().addListener((observed, oldT, newT) -> {
			if(!agendaOrCalendar.getSelectedToggle().equals(this.agenda)) {
				if (weekOrDay.getSelectedToggle().equals(this.day)) {
					day.setVisible(true);
					week.setVisible(false);
					agenda.setVisible(false);
				} else {
					day.setVisible(false);
					week.setVisible(true);
					agenda.setVisible(false);
				}
			} else {
				day.setVisible(false);
				week.setVisible(false);
				agenda.setVisible(true);
			}
			parent.update();
		});
	}
	
	@FXML
	public void initialize () {
		
		weekOrDay = new ToggleGroup();
		weekOrDay.getToggles().addAll(day, week);
		
		agendaOrCalendar = new ToggleGroup();
		agendaOrCalendar.getToggles().addAll(agenda, calendarX);
		
		
		weekOrDay.selectToggle(day);
		agendaOrCalendar.selectToggle(agenda);
		
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
					            
					            box.setSelected(true);
					            
					            txtDoctor.setFill(color);

					            txtDoctor.setFont(Font.font(null, FontWeight.BOLD, 12));
					            
					            box.setOnAction(event -> {
					            	if(box.isSelected()) {
					            		doctors.add(item);
					            	} else {
					            		doctors.remove(item);
					            	}
					            
					            	parent.update();
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
	
	public void setReservationAction (EventHandler <ActionEvent> event) {
		reservation.setOnAction(event);
	}
	
	public boolean reservationState () {
		return reservation.isSelected();
	}
	
	public void setDoctors (Iterator <Doctor> doctors) {
		if(doctors == null) {
			doctorsCombobox.setDisable(true);
		} else {
			this.doctors.clear();
			doctorsCombobox.setItems(FXCollections.observableArrayList());
			while (doctors.hasNext()) {
				Doctor doc = doctors.next();
				doctorsCombobox.getItems().add(doc);
				this.doctors.add(doc);		
			}
			
			doctorsCombobox.setDisable(false);
		}
		
		parent.update();
	}

	public void update() {
		parent.update();
	}
}