package control;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.CalendarObserver;

public class ToolbarControl extends CalendarObserver {

	@FXML private Button addTaskBtn;
	@FXML private Button todayBtn;
	@FXML private CheckBox eventCheckBox;
	@FXML private CheckBox taskCheckBox;
	@FXML private Button addEventBox;
	@FXML private Button nextBtn;
	@FXML private Button prevBtn;
	@FXML private Label yearLabel;
	@FXML private Label monthLabel;
	@FXML private Button seeAgenda;
	@FXML private Button seeDay;
	@FXML private AnchorPane calendarView;
	@FXML private CalendarControl calendarViewController;
	
	
    @FXML
    void initialize() {
        assert prevBtn != null : "fx:id=\"prevBtn\" was not injected: check your FXML file 'ToolbarPane.fxml'.";
        assert addEventBox != null : "fx:id=\"addEventBox\" was not injected: check your FXML file 'ToolbarPane.fxml'.";
        assert todayBtn != null : "fx:id=\"todayBtn\" was not injected: check your FXML file 'ToolbarPane.fxml'.";
        assert yearLabel != null : "fx:id=\"yearLabel\" was not injected: check your FXML file 'ToolbarPane.fxml'.";
        assert calendarView != null : "fx:id=\"calendarView\" was not injected: check your FXML file 'ToolbarPane.fxml'.";
        assert eventCheckBox != null : "fx:id=\"eventCheckBox\" was not injected: check your FXML file 'ToolbarPane.fxml'.";
        assert addTaskBtn != null : "fx:id=\"addTaskBtn\" was not injected: check your FXML file 'ToolbarPane.fxml'.";
        assert taskCheckBox != null : "fx:id=\"taskCheckBox\" was not injected: check your FXML file 'ToolbarPane.fxml'.";
        assert monthLabel != null : "fx:id=\"monthLabel\" was not injected: check your FXML file 'ToolbarPane.fxml'.";
        assert nextBtn != null : "fx:id=\"nextBtn\" was not injected: check your FXML file 'ToolbarPane.fxml'.";
             
        eventCheckBox.setSelected(true);
        taskCheckBox.setSelected(true);
   }
    
   public void initializeButtons (MainControl mainViewController) {
	   calendarViewController.selectedToggleProperty(this, mainViewController);
	   
       nextBtn.setOnAction(event -> {
		   mgc.set(Calendar.MONTH, mgc.get(Calendar.MONTH) + 1);
		   mgc.set(Calendar.DAY_OF_MONTH,  1);
		   mainViewController.update();
	   });
       
       prevBtn.setOnAction(event -> {
	       mgc.set(Calendar.MONTH, mgc.get(Calendar.MONTH) - 1);
	       mgc.set(Calendar.DAY_OF_MONTH,  1);
	       mainViewController.update();
	   });
       
	   seeDay.setOnAction(event -> {
			mainViewController.setDayVisible();
			mainViewController.update();
		});
       
       seeAgenda.setOnAction(event -> {
			mainViewController.setAgendaVisible();
			mainViewController.update();
		});
       
       todayBtn.setOnAction(event -> {
			mgc.jumpToCurrentDate();
			mainViewController.update();
       });
       
       addEventBox.setOnAction(event -> {
    	   mainViewController.setAddVisible();
			mainViewController.setAdd(true);
			mainViewController.update();
		});
       
       addTaskBtn.setOnAction(event -> {
			mainViewController.setAddVisible();
			mainViewController.setAdd(false);
			mainViewController.update();
		});
       
       eventCheckBox.setOnAction(event -> {
    	   mainViewController.update();
       });
       
       taskCheckBox.setOnAction(event -> {
    	   mainViewController.update();
       });
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
	
	public boolean showTasks () {
		return taskCheckBox.isSelected();
	}
	
	public boolean showEvents () {
		return eventCheckBox.isSelected();
	}
	
	
   public void setDay () {
	   if(calendarViewController.getSelectedInput() != 0) {
		   mgc.set(Calendar.DAY_OF_MONTH, calendarViewController.getSelectedInput());
	   }
   }
   
   public LocalDate getDay () {
	   return mgc.selectedDate();
   }
}
