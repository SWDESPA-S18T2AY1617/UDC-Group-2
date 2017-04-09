package control.doctor;

import control.AgendaControl;
import control.CalendarControl;
import control.DayControl;
import control.ToolbarControl;
import control.WeekControl;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import model.ModelGregorianCalendar;

public class DoctorMainControl {

    @FXML private AnchorPane calendarView;
    @FXML private AnchorPane agendaView;
    @FXML private AnchorPane dayView;
    @FXML private AnchorPane weekView;
    @FXML private AnchorPane toolbar;
    
    @FXML private ToolbarControl toolbarController;
    @FXML private CalendarControl calendarViewController;
    @FXML private AgendaControl agendaViewController;
    @FXML private DayControl dayViewController;
    @FXML private WeekControl weekViewController;
    
	public void setCalendar(ModelGregorianCalendar modelGregorianCalendar) {
		toolbarController.setCalendar(modelGregorianCalendar);
	}
}
