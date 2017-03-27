package control;
import java.util.Iterator;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import model.Event;
import model.storage.EventCollection;

public class DayControl {
	@FXML private AnchorPane dayInner;
	@FXML private DayInnerControl dayInnerController;

	@FXML
	 public void initialize () {
		 dayInnerController.initialize();
	 }
		
	 public void setEvents (Iterator <Event> events) {
		 dayInnerController.setDataEvents(events);
	 }
	 
	 public void initializeButtons (EventCollection collection) {
		 dayInnerController.initializeButtons(collection);
	 }
}