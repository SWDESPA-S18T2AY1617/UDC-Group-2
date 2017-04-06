package control;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class DoctorMainControl {

    @FXML private AnchorPane calendarView;
    @FXML private AnchorPane agendaView;
    @FXML private AnchorPane dayView;
    @FXML private AnchorPane weekView;
    
    @FXML private CalendarControl calendarViewController;
    @FXML private AgendaControl agendaViewController;
    @FXML private DayControl dayViewController;
    @FXML private WeekControl weekViewController;


}
