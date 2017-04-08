package control.secretary;

import control.AgendaControl;
import control.CalendarControl;
import control.DayControl;
import control.WeekControl;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import model.calendar.ModelGregorianCalendar;
import model.storage.SecretaryCollection;

public class SecretaryMainControl {
	
	@FXML private AnchorPane agendaView;
    @FXML private AnchorPane dayView;
    @FXML private AnchorPane weekView;
    @FXML private AnchorPane secToolbar;
    @FXML private AnchorPane loginView;
    @FXML private AnchorPane registerView;
    @FXML private AnchorPane startupView;
    
    @FXML private CalendarControl calendarViewController;
    @FXML private AgendaControl agendaViewController;
    @FXML private DayControl dayViewController;
    @FXML private WeekControl weekViewController;
    
    @FXML private SecretaryRegisterControl secRegisterController;
    @FXML private SecretaryLoginControl secLoginController;
    @FXML private SecretaryStartupControl secStartUpController;
    @FXML private SecretaryToolbarControl secToolbarController; 
    
    @FXML
    void initialize () {
    	setStartupVisible();
    	//secToolbarController.disableLogout();
    }
    
    public void enableLogout() {
    	//secToolbarController.enableLogout();
    }
    
    public void clearFields() {
    	secLoginController.clearFields();
    	secRegisterController.clearFields();
    }
    
    public void initializeUI (SecretaryCollection secCollections) {
    	secStartUpController.initializeButtons(this);
    	secLoginController.initializeButtons(this, secCollections);
    	secRegisterController.initializeButtons(this, secCollections);
    	//secToolbarController.initializeButtons(this, secCollections);
    }

    public void setCalendar (ModelGregorianCalendar mgc) {
    	//secToolbarController.setState(mgc);
    }
    
    public void setStartupVisible () {
    	startupView.setVisible(true);
    	agendaView.setVisible(false);
    	weekView.setVisible(false);
    	dayView.setVisible(false);
    	loginView.setVisible(false);
    	registerView.setVisible(false);
    }
    
    public void setLoginVisible () {
    	startupView.setVisible(false);
    	agendaView.setVisible(false);
    	weekView.setVisible(false);
    	dayView.setVisible(false);
    	loginView.setVisible(true);
    	registerView.setVisible(false);
    }
    
    public void setRegisterVisible () {
    	startupView.setVisible(false);
    	agendaView.setVisible(false);
    	weekView.setVisible(false);
    	dayView.setVisible(false);
    	loginView.setVisible(false);
    	registerView.setVisible(true);
    }
    
    public void setAgendaVisible () {
    	startupView.setVisible(false);
    	agendaView.setVisible(true);
    	weekView.setVisible(false);
    	dayView.setVisible(false);
    	loginView.setVisible(false);
    	registerView.setVisible(false);
    }
    
    public void setDayVisible () {
    	startupView.setVisible(false);
    	agendaView.setVisible(false);
    	weekView.setVisible(false);
    	dayView.setVisible(true);
    	loginView.setVisible(false);
    	registerView.setVisible(false);
    }
    
    public void setWeekVisible () {
    	startupView.setVisible(false);
    	agendaView.setVisible(false);
    	weekView.setVisible(true);
    	dayView.setVisible(false);
    	loginView.setVisible(false);
    	registerView.setVisible(false);
    }
    
    public void initializeComboBox(SecretaryCollection secCollections) {
		//secToolbarController.initializeComboBox(secCollections);
	}
}
