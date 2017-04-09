package control.doctor;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Iterator;

import control.CalendarControl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.calendar.CalendarObserver;
import model.calendar.Doctor;
import model.storage.AppointmentCollection;
import model.storage.ClinicDB;
import model.storage.DoctorCollection;

public class DoctorToolbarControl extends CalendarObserver {
	
    @FXML private Button prevBtn;
    @FXML private Button logoutBtn;
    @FXML private RadioButton dayRBtn;
    @FXML private Label yearLabel;
    @FXML private Label monthLabel;
    @FXML private Button slotsBtn;
    @FXML private Button agendaBtn;
    @FXML private ComboBox <Doctor> doctorComboBox;
    @FXML private Button todayBtn;
    @FXML private CheckBox checkDoc2;
    @FXML private CheckBox checkDoc3;
    @FXML private RadioButton weekRBtn;
    @FXML private CheckBox checkDoc1;
    @FXML private Button nextBtn;
    
    @FXML private AnchorPane calendarView;
    @FXML private CalendarControl calendarViewController;
    @FXML 
    void initialize () {
    	checkDoc1.setSelected(true);
    	dayRBtn.setSelected(true);
    	doctorComboBox.setCellFactory( new Callback<ListView<Doctor>, ListCell<Doctor>>() {
    		
    		public ListCell<Doctor> call(ListView<Doctor> arg0) {
				return new ListCell<Doctor>() {
					 @Override 
					 protected void updateItem(Doctor item, boolean empty) {
						 super.updateItem(item, empty);
					    	if (item == null || empty) {
					            setText(null);
					            setGraphic(null);
					        } else {
					        	Text docName = new Text(item.getName().toString());
					            	
					            docName.setFont(Font.font(null, FontWeight.NORMAL, 12));
					            
					            HBox hbox = new HBox(docName);
					            setGraphic(hbox);
					        }
					 }
				};
			}
		});
    }
    
    public void setDay () {
 	   if(calendarViewController.getSelectedInput() != 0) {
 		   mgc.set(Calendar.DAY_OF_MONTH, calendarViewController.getSelectedInput());
 	   }
    }
    
    public LocalDate getDay () {
 	   return mgc.selectedDate();
    }
    
    @Override
	public void update() {
		yearLabel.setText(String.valueOf(mgc.selectedDate().getYear()));
		monthLabel.setText(Month.of(mgc.selectedDate().getMonthValue()).toString());
		
		int [][] items = new int [6][7];
    	
    	for(int i = 0; i < items.length; i++) {
    		for(int j = 0; j < items[i].length; j++) {
    			items[i][j] = 0;
    		}
    	}    	
    	
    	for(int i = 1; i <= mgc.dayOfMonthBound(); i++) {
    		items 	[(i + mgc.selectedDate().withDayOfMonth(1).getDayOfWeek().getValue() - 1)/7]
    				[(i + mgc.selectedDate().withDayOfMonth(1).getDayOfWeek().getValue() - 1)%7] 
    						= i;
    	}
    	
    	calendarViewController.setData(items);
    	calendarViewController.setSelectedInput(mgc.selectedDate().getDayOfMonth());
	}
    
    public void initializeComboBox (DoctorCollection dc)
    {
    	ClinicDB.openConnection();
    	Iterator <Doctor> doctors = dc.getAll();
    	ObservableList<Doctor> items = FXCollections.observableArrayList();
    	while(doctors.hasNext())
    	{
    		items.add(doctors.next());
    	}
    	doctorComboBox.setItems(items);
    	ClinicDB.closeConnection();
    }
    
    public void initializeButtons (DoctorMainControl dmc, DoctorCollection dc, AppointmentCollection ac) {
    	
    	logoutBtn.setOnAction(event -> {
    		dmc.setStartupVisible();
    	});
    	
    	nextBtn.setOnAction(event -> {
 		   mgc.set(Calendar.MONTH, mgc.get(Calendar.MONTH) + 1);
 		   mgc.set(Calendar.DAY_OF_MONTH,  1);
 	   });
    	
    	prevBtn.setOnAction(event -> {
 	       mgc.set(Calendar.MONTH, mgc.get(Calendar.MONTH) - 1);
 	       mgc.set(Calendar.DAY_OF_MONTH,  1);
 	   });
    	
    	todayBtn.setOnAction(event -> {
			mgc.jumpToCurrentDate();
//			mainViewController.update();
       });
    	
    	slotsBtn.setOnAction(event -> {
    		dmc.setDayVisible();
    		dmc.setDayAppointments(ac, doctorComboBox.getSelectionModel().getSelectedItem());
    	});
    	
    	agendaBtn.setOnAction(event -> {
    		dmc.setAgendaVisible();
    		ClinicDB.openConnection();
    		dmc.getAgendaControl().setEvents(ac.getAll());
    		ClinicDB.closeConnection();
    	});
    	
    	
    }

	public void disableLogout() {
		logoutBtn.setDisable(true);
	}

	public void enableLogout() {
		logoutBtn.setDisable(false);
	}
   
}
